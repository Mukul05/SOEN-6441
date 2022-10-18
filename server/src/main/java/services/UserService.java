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
	public int readUser() throws ClassNotFoundException, SQLException {
		int count = 0;
		try {
			count = userDAO.readUser().size();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}
		
		return count;
	}

	@Override
	public boolean updateUser(UserDetails bean) throws SQLException, ClassNotFoundException {
		boolean isUpdated = false;
		UserDetails cUser = userDAO.currentDetail(bean.getId());
		try {
			isUpdated = userDAO.updateUser(bean.getId(),bean, cUser);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}
		return isUpdated;
	}

	@Override
	public boolean deleteUser(int id) throws ClassNotFoundException, SQLException {
		 boolean isDeleted = false;
		try {
			isDeleted = userDAO.deleteUser(id);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			throw e;
		}
		return isDeleted;
	}

	

}
