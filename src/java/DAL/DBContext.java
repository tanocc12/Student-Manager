package DAL;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {

    protected Connection connection;

    private final String url =
            "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;"
            + "databaseName=StudentManagementDB;"
            + "encrypt=false;"
            + "trustServerCertificate=true;";

    private final String user = "sa";          
    private final String password = "123"; 

    public DBContext() {

        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected!");

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}