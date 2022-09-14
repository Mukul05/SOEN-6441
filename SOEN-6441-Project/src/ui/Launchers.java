package Launcher;

import Database.Connections;

public class Launchers {

	public static void loadData() {
		System.out.println("Loading data...");
		Connections.sqlConnection();
	}
	
	public static void main (String[] args) {
		loadData();
	}
}
