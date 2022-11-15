package Database;





import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;




public class Connections {
	
	public static String url = null;
	public static String username = null;
	public static String password = null;
	public static Connection conn = null;
	

	
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		
		
			String url = "jdbc:mysql://localhost:3306/Dummydb";
			String username = "root";
			String password = "admin";
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		conn = DriverManager.getConnection(url, username, password);
		System.out.println("Connection established!!");
		
		return conn;
	}

	public static void getDBCloseConnection() throws SQLException {
		conn.close();
	}

}