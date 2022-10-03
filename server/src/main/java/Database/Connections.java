package Database;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connections {
	
	public static String url = null;
	public static String username = null;
	public static String password = null;
	public static Connection conn = null;
	private static final Logger LOGGER = Logger.getLogger(Connections.class.getName());
	private static Properties prop = null;
	
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		InputStream input = null;
		try {
			final String rootPath = "/db.properties";
			input=ClassLoader.class.getResourceAsStream("/dbproperties");
			prop.load(input);
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");			
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "database.properties not found");
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "IO error for database.properties");
		}
		conn =  DriverManager.getConnection(url, username, password);
		System.out.println("Connection established!!");
		return conn;
	}

	public static void getDBCloseConnection() throws SQLException {
		conn.close();
	}

}