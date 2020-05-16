package Data_Layer;

import Interface_Layer.Workers.SystemInterfaceWorkers;

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

        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:DB/workers_transportation_db.db";
            // create a connection to the database
            conn = (Connection) DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    ((java.sql.Connection) conn).close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
