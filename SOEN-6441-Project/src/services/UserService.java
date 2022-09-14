package services;

import java.sql.SQLException;

import bean.UserDetails;

public interface UserService {
	
	public int insertUser(UserDetails bean) throws ClassNotFoundException, SQLException,Exception;
	public void readUser() throws ClassNotFoundException, SQLException;
	public void updateUser(UserDetails bean) throws SQLException, ClassNotFoundException;
	public void deleteUser(UserDetails bean) throws ClassNotFoundException, SQLException;


}
