package services;

import java.sql.SQLException;

import bean.UserDetails;
import dao.UserDetailDAO;
import dao.UserDetailDAOImpl;

public class UserServiceImpl implements UserService{

	UserDetailDAO userDAO = new UserDetailDAOImpl();

	@Override
	public int insertUser(UserDetails bean) throws ClassNotFoundException, SQLException, Exception {
		 int UpdateCount = 0;
		try {
			UpdateCount	=  userDAO.insertUser(bean);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e){
			throw e;
		}
		return UpdateCount;
	}

	@Override
	public void readUser() throws ClassNotFoundException, SQLException {
		try {
			userDAO.readUser();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}

	}

	@Override
	public void updateUser(UserDetails bean) throws SQLException, ClassNotFoundException {
		try {
			userDAO.updateUser(bean);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteUser(UserDetails bean) throws ClassNotFoundException, SQLException {
		try {
			userDAO.deleteUser(bean);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}
	}

}
