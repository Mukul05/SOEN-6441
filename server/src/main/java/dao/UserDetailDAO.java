package dao;

import java.sql.SQLException;
import java.util.List;

import bean.UserDetails;

public interface UserDetailDAO {
	
	public boolean insertUser(UserDetails bean) throws ClassNotFoundException, SQLException,Exception;
	public List<UserDetails> readUser() throws ClassNotFoundException, SQLException;
	public List<UserDetails> readUser(int pageNumber, int pageSize) throws ClassNotFoundException, SQLException;
	public boolean updateUser(int userId, UserDetails bean, UserDetails currentUser) throws SQLException, ClassNotFoundException;
	public boolean deleteUser(int userId) throws ClassNotFoundException, SQLException;

}
