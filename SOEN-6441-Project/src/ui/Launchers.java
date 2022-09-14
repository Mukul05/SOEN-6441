package ui;

import java.sql.SQLException;

import Database.Connections;
import services.UserService;
import services.UserServiceImpl;

public class Launchers {


	public static void main (String[] args) {
		readUser();
	}
	
	public static void readUser() {
		try {
			UserService userService = new UserServiceImpl();
			userService.readUser();
		}catch (ClassNotFoundException | SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
