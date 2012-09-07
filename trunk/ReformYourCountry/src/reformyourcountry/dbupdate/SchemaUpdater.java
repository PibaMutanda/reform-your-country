package reformyourcountry.dbupdate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import reformyourcountry.util.DateUtil;

public class SchemaUpdater  {

    public static void main(String[] arg) throws IOException{
        run2();
    }

    @SuppressWarnings("deprecation")
    public static void run2() throws IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Ejb3Configuration conf =  new Ejb3Configuration().configure("ConnectionPostgres",map);
        SchemaUpdate schemaUpdate =new SchemaUpdate(conf.getHibernateConfiguration());
        schemaUpdate.setOutputFile("c:/test.sql");
        PrintStream initOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PrintStream newOut = new PrintStream(outputStream);
        System.setOut(newOut);

        //The update is executed in script mode only
        schemaUpdate.execute(true, false);

        //We reset the original out
        System.setOut(initOut);
        System.out.println("--*******************************************Begin of SQL********************************************");
        System.out.println("-- "+DateUtil.formatyyyyMMdd(new Date()));
        //We display the output content to the console in LOWER CASE ! (this is the reason of standart stream manip)
        BufferedReader ouReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
        String str = null;
        boolean firstPass = true;
        while(( str = ouReader.readLine()) != null){
            if (!firstPass ||!"".equals(str)){
                if ("".equals(str)) { // Previous statement finished
                    System.out.println(";");
                } else {
                    System.out.println();
                }
                System.out.print(str.toLowerCase());
            }
            firstPass=false;
        }
        // if ("".equals(str)) { // Previous statement finished
        System.out.println(";");
        // }

        //If some exception occurred we display them
        if(!schemaUpdate.getExceptions().isEmpty()){
            System.out.println();
            System.out.println("SOME EXCEPTIONS OCCURED WHILE GENERATING THE UPDATE SCRIPT:");
            for (Exception e: (List<Exception>)schemaUpdate.getExceptions()) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("--*******************************************End of SQL********************************************");
    }




}
