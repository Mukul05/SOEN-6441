package Database;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import services.User;

public class Connections {
	
	public static String url = null;
	public static String username = null;
	public static String password = null;
	public static Connection conn = null;
	private static final Logger LOGGER = Logger.getLogger(Connections.class.getName());
	private static Properties prop = null;
	
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		
		//String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("db.properties")).getPath();
		//InputStream input = null;
		
		//try {
			//input = new FileInputStream(rootPath);
			//Properties prop = new Properties();
			//prop = new Properties();
			//prop.load(new FileInputStream("src/main/java/Database/db.properties"));
			//prop.load(getServletContext().getResourceAsStream("/WEB-INF/lib/db.properties"));
			//url = prop.getProperty("url");
			//username = prop.getProperty("username");
			//password = prop.getProperty("password");
			String url = "jdbc:mysql://localhost:3306/DummyDb";
			String username = "root";
			String password = "admin";
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Connection conn =  DriverManager.getConnection(url,username,password);
			
	//	} catch (FileNotFoundException e) {
	//		LOGGER.log(Level.SEVERE, "database.properties not found");
	//	} catch (IOException e) {
	//		LOGGER.log(Level.SEVERE, "IO error for database.properties");
	//	}
		conn = DriverManager.getConnection(url, username, password);
		System.out.println("Connection established!!");
		
		return conn;
	}

	public static void getDBCloseConnection() throws SQLException {
		conn.close();
	}

}