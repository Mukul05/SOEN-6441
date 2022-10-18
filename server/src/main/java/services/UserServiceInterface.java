package services;

import java.sql.SQLException;

import bean.UserDetails;

public interface UserServiceInterface {
	
	public boolean insertUser(UserDetails bean) throws ClassNotFoundException, SQLException,Exception;
	public int readUser() throws ClassNotFoundException, SQLException;
	public boolean updateUser(UserDetails bean) throws SQLException, ClassNotFoundException;
	public boolean deleteUser(int id) throws ClassNotFoundException, SQLException;


}
