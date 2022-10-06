package services;

import java.sql.SQLException;

import bean.UserDetails;
import dao.UserDetailDAO;
import dao.UserDetailDAOImpl;

public class UserService implements UserServiceInterface{

	// TODO: This all will get removed for servlet
	UserDetailDAO userDAO = new UserDetailDAOImpl();

	@Override
	public boolean insertUser(UserDetails bean) throws ClassNotFoundException, SQLException, Exception {
		 boolean isInserted = false;
		try {
			isInserted	=  userDAO.insertUser(bean);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e){
			throw e;
		}
		return isInserted;
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
			userDAO.updateUser(bean.getId(),bean, bean);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteUser(int id) throws ClassNotFoundException, SQLException {
		try {
			userDAO.deleteUser(id);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteUser(UserDetails bean) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
