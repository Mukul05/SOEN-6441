package Database;



import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class Connections {
	
	public static String url = "jdbc:mysql://localhost:3306/DummyDb";
	public static String username = "root";
	public static String password = "admin";
	public static Connection conn = null;
	
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		
		conn = DriverManager.getConnection(url, username, password);
		System.out.println("Connection established!!");
		
		return conn;
	}

	public static void getDBCloseConnection() throws SQLException {
		conn.close();
	}

}