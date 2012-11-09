#/bin/bash
#tips : exit error code are free between 2 and 64 ( so can use 3 to 63)

# Make sure only root can run our script
if [ "$(id -u)" != "0" ]; then
        echo "$0 This script must be run as root" 1>&2
        exit 1
fi

BASE_FOLDER=/opt/RYC_Maintenance
#in case of executing the script in a différent folder, we are we access ressource as relative path
cd $BASE_FOLDER

. bin/config
. bin/common_function

#Smart file remove.
function rotateBackup(){

        assertFolderExists $BACKUP_FOLDER/$1

        cd $BACKUP_FOLDER/$1
        #because the expression is eval when the variable is declared
        BACKUPS_TO_KEEP=$(ls | wc -l) #Amount of files already in that dir.

        # if there are too many old backups, we delete some files
        if [ $BACKUPS_TO_KEEP -gt $BACKUP_COUNT ]; then
                fileToRemoveNumber=$(($BACKUPS_TO_KEEP-$BACKUP_COUNT))
                rm -f $(ls -tr | head -n $fileToRemoveNumber) #list file by mod time oldest first and remove X file
                exit $?
        else
                echo "no $1 backup to remove"
		exit 5
        fi
}

case $1 in
        "backupDB")
                echo " backup DB..."

                ensureFolderExists $BACKUP_FOLDER
                ensureFolderExists $BACKUP_FOLDER/DB
                
                cd $BACKUP_FOLDER/DB
                su -c 'pg_dump '$DB_NAME' | xz -e9c > last.xz' postgres 
                cp -a last.xz $NOW.xz

                rotateBackup DB
                exit 0
                ;;
        "backupGen")
                echo " backup gen dir..."

                ensureFolderExists $BACKUP_FOLDER
                ensureFolderExists $BACKUP_FOLDER/gen

                assertFolderExists $GEN_DIR

                # Compresses the gen folder into an XZ file.
                if [ "$(ls -A "$GEN_DIR")" ]; then

                        #must be in the gen and execute tar in it otherwise tar archive all the dir structure
                        cd $GEN_DIR
                        tar -Jcvf $BACKUP_FOLDER/gen/last.tar.xz * 
                        #must now copy the generated archive and compress it with the right to the right location
                        #mv last.tar $BACKUP_FOLDER/gen/last.tar 
                        cd $BACKUP_FOLDER/gen 
                        #use "last" copy name instead of directly named with the name to easely get the last backup from dev computer 
                        #xz -fe9 last.tar 
                        cp -a last.tar.xz $NOW.tar.xz

                        exit $?
                else
                        echo "nothing to do, gen directory is empty "
                        exit 0
                fi
                rotateBackup gen
                exit 0
                ;;
        "rotateBackup")
                case $2 in
                        "DB")
                                rotateBackup DB
                        ;;
                        "gen")
                                rotateBackup gen
                        ;;
                        *)
                        echo "bad second argument : $2 . Use DB or gen ."
                         exit 3
                        ;;
                esac        
                ;;
        "restoreGen")
                if [ ! -n $2 ]
                then
                        echo "specify backup not implemented yet"
                        exit 5
                else
                        echo "restoring last gen backup..."
                        assertFolderExists $BACKUP_FOLDER/gen

                        ensureFolderExists $WEBAPPS_FOLDER/ROOT/gen
                        chown tomcat:tomcat $WEBAPPS_FOLDER/ROOT/gen
                        
                        tar -Jxvf $BACKUP_FOLDER/gen/last.tar.xz -C $WEBAPPS_FOLDER/ROOT/gen
                fi
                ;;
        "deploy")
                echo "deploying application ..."
                #TODO make special command like force checkout , dev , verbose , debug , etc... 
                #echo "write a special command or leave blank for a normal deploy"
                #TEMP=
                #read TEMP
                case TEMP in
                        *)
                                BIN_DIR=$BASE_FOLDER/bin

                                #we need to execute scripts from their folder otherweise config file can't be found
                                cd $BIN_DIR
                                ./switch_httpd dep

                                #the precedent script change his working and we do not want to hardcode the emplacement of the config file
                                cd $BIN_DIR
                                ./checkout-build

                                #the precedent script change his working and we do not want to hardcode the emplacement of the config file
                                cd $BASE_FOLDER
                                #backup gen folder in case of...
                                ./maintenance.sh backupGen
                                cd $BIN_DIR
                                ./do_deployment

                                #the precedent script change his working and we do not want to hardcode the emplacement of the config file
                                cd $BIN_DIR
                                ./start_server

                                #the precedent script change his working and we do not want to hardcode the emplacement of the config file
                                cd $BIN_DIR
                                ./switch_httpd prod
                        ;;
                esac
                ;;
                
        *)
                echo "bad argument : $1"
                exit 3
                ;;
esac
