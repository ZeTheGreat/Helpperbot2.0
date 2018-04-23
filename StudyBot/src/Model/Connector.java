package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connector {

    public static Connection conn;

    public Connector() {
        getConnection();
    }


    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/studybot";
    private final String USER = "root";
    private final String PW = "tison123";

    public void getConnection() {
        try {
            Class.forName(DRIVER);
            conn =  DriverManager.getConnection(URL, USER, PW);
            System.out.println("Connected Successfully with the DB.\n");

        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Connection error: ", ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) throws SQLException {
        if (con != null) {
            con.close();
        }
    }


}
