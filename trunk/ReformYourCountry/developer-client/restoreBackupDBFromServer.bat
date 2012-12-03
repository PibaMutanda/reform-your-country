:: This script restores the DB backup in the local DB. But the backup first needs to be uncompressed (7ZIP, manually, not in this script)

SET EXE_LOCATION=C:\Progra~1\PostgreSQL\9.2\bin
SET USER=rycdev
SET DATABASE=RYC
SET BACKUP_FILE=last-db
SET PORT=2012

%EXE_LOCATION%\dropdb.exe -U %USER% -p %PORT% %DATABASE%

:: using the postgres user to create the DB, owned by the rycdev user. We could have used the rycdev user to create the dB, it was just in case of the rycdev user has no right to create DBs.
%EXE_LOCATION%\createdb.exe -O %USER% -U postgres -p %PORT% %DATABASE%

%EXE_LOCATION%\psql.exe -U %USER% -p %PORT% -f %BACKUP_FILE% %DATABASE% 

PAUSE 