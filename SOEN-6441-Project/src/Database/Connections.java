package Database;
import java.util.*;

import entities.UserDetails;
import managers.UsersManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connections {

	public static void sqlConnection() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DummyDb?useSSL=false","root","admin");
				Statement query = conn.createStatement();) {
			
			loadUserDetails(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadUserDetails(Statement query) throws SQLException {
		String qury = "Select id,name,designation from UserDetail";
		ResultSet re = query.executeQuery(qury);
		
		List<UserDetails> userDetailList = new ArrayList<>();
		while (re.next()) {
			long id = re.getLong("id");
			String name = re.getString("name");
			String designation = re.getString("designation");
			
			System.out.println("id: " + id + " , Name " + name + " , Designation " + designation);
			
			UserDetails userdetail = UsersManager.getInstance().createUser(id,name,designation);
			userDetailList.add(userdetail);
			
		}
		
	}
	
	
}
