package reformyourcountry.dbupdate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import reformyourcountry.util.DateUtil;

/** Small utility to be run by a developer to identify the difference between
 * its entities and its DB schema. It produces an SQL to be copy/pasted and applied
 * on the DB manually. Each developers having its own DB, when a developer commits its
 * Java code with new entity attributes (needing new DB columns), he also commits
 * an updated SQL file with the SQL that other developers need to apply on their local DB.
 * Later, when deploying the next version of the application in production,
 * this SQL file with cumulated changes will be applied onto the production DB.  
 * 
 * Limitations: 
 * 1. the Hibernate schema update does not detect removed attributes. 
 * If you have to delete a column, you need to write the SQL manually;
 * 
 * 2. the Hibernate schema update does not detect changes on existing columns.
 * for example, if you add @Column(nullable=false), it will not generates an 
 * additional DB constraint.
 * 
 * @author Cédric Fieux & John Rizzo & Aymeric Levaux
 *
 */
public class SchemaUpdater  {
    
    private static final String PROP_FILE="/secret.properties";
    private static String url;
    private static String userName;
    private static String password;

    public static void readPropertiesFile(){
        try{
            InputStream is = SchemaUpdater.class.getResourceAsStream(PROP_FILE);
            Properties prop = new Properties();
            prop.load(is);
            url = prop.getProperty("DB.url");
            userName = prop.getProperty("DB.userName");
            password = prop.getProperty("DB.password");
            is.close();
            /* code to use values read from the file*/
        }catch(IOException e){
            System.out.println("Failed to read from " + PROP_FILE + " file.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public static void main(String[] arg) throws IOException {
        
        ////// 1. Prepare the configuration (connection parameters to the DB, ect.)
        readPropertiesFile();
        // Empty map. We add no additional property, everything is already in the persistence.xml
        Map<String,Object> map=new HashMap<String,Object>();   
        map.put("hibernate.connection.url", url);
        map.put("hibernate.connection.username", userName);
        map.put("hibernate.connection.password", password);
        // Get the config from the persistence.xml file, with the unit name as parameter.
        Ejb3Configuration conf =  new Ejb3Configuration().configure("Connection",map);
        SchemaUpdate schemaUpdate =new SchemaUpdate(conf.getHibernateConfiguration());

        /////// 2. Get the SQL
        // Before we run the update, we start capturing the console output (to add ";" later)
        PrintStream initOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PrintStream newOut = new PrintStream(outputStream);
        System.setOut(newOut);

        //The update is executed in script mode only
        schemaUpdate.execute(true, false);

        //We reset the original out
        System.setOut(initOut);
        
        ////// 3. Prints that SQL at the console with a good format (adding a ";" after each line).
        System.out.println("--*******************************************Begin of SQL********************************************");
        System.out.println("-- "+DateUtil.formatyyyyMMdd(new Date()));
        BufferedReader ouReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
        String str = ouReader.readLine();
        while(str != null){  // For each (sometimes multiline) SQL statement
            // now, str equals "".
            str = ouReader.readLine();  // 
            while (str != null && !str.trim().equals("")) { // for each line of the same statement
                System.out.println();  // previous line is finished.
                System.out.print(str.toLowerCase());
                str = ouReader.readLine();
            }
            // Statement is now finished
            System.out.println(";");
        }
        System.out.println("--*******************************************End of SQL********************************************");

        ////// 4. Print eventual exceptions.
        //If some exception occurred we display them
        if(!schemaUpdate.getExceptions().isEmpty()){
            System.out.println();
            System.out.println("SOME EXCEPTIONS OCCURED WHILE GENERATING THE UPDATE SCRIPT:");
            for (Exception e: (List<Exception>)schemaUpdate.getExceptions()) {
                System.out.println(e.getMessage());
            }
        }
    }

}
