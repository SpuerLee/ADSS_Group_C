package Data_Layer;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    private static Connection connection = null;


    public static Connection getInstance(){
        if(connection == null){
            connection = new Connection();
        }
        return connection;
    }

    private Connection(){

        connection = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:DB/workers_transportation_db.db";
            // create a connection to the database
            connection = (Connection) DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    ((java.sql.Connection) connection).close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
