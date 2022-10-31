package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.Connections;
import bean.UserDetails;

public class UserDetailDAOImpl implements UserDetailDAO {

	private static Connection conn = null;

	public UserDetailDAOImpl() {}

	private static class SingletonHelper {
		private static final UserDetailDAOImpl INSTANCE = new UserDetailDAOImpl();
	}

	public static UserDetailDAOImpl getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public boolean insertUser(UserDetails bean) throws ClassNotFoundException, SQLException, Exception {

		try {
			int id = bean.getId();
			String firstName = bean.getFirstName();
			String lastName = bean.getLastName();
			String email = bean.getEmail();

			String insertstmt = "insert into UserDetail values(?,?,?,?)";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(insertstmt);
			pstatement.setInt(1, id);
			pstatement.setString(2, firstName);
			pstatement.setString(3, lastName);
			pstatement.setString(4, email);

			int result = pstatement.executeUpdate();

			return result > 0;
		} catch (Exception e) {
			throw e;
		} finally {

			Connections.getDBCloseConnection();
		}
	}

	@Override
	public List<UserDetails> readUser() throws ClassNotFoundException, SQLException {
		try {
			//return readUser(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
			List<UserDetails> users = new ArrayList<>();
			String query = "SELECT * FROM UserDetail";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			ResultSet resultSet = pstatement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				System.out.println(firstName);
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				UserDetails currentUser = new UserDetails( id, firstName, lastName, email);
				users.add(currentUser);
				
			}
			
			return users;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public UserDetails getUserById(String userId) throws ClassNotFoundException, SQLException {
		try {
			String query = "SELECT * FROM UserDetail WHERE id = ?";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			pstatement.setString(1, userId);
			ResultSet resultSet = pstatement.executeQuery();

			UserDetails user = null;
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				System.out.println(firstName);
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				user = new UserDetails( id, firstName, lastName, email);				
			}
			
			return user;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<UserDetails> readUser(int pageNumber, int pageSize) throws ClassNotFoundException, SQLException {
		try {
			List<UserDetails> users = new ArrayList<>();
			String query = "SELECT * FROM UserDetail ";
			int offset = (pageSize) * (pageNumber - 1);
			query = query 	+ "LIMIT " + offset + "," + pageSize;
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			ResultSet resultSet = pstatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				UserDetails currentUser = new UserDetails( id, firstName, lastName, email);
				users.add(currentUser);
			}
			return users;
		} catch (Exception e) {
			throw e;
		}
		finally {
			Connections.getDBCloseConnection();
			}
	}

	@Override
	public boolean updateUser(int userId, UserDetails user) throws SQLException, ClassNotFoundException {
		try {		
			
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String email = user.getEmail();

			String query = "UPDATE UserDetail SET firstName= ? ,lastName = ? , email=? WHERE id = ?";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			
			pstatement.setString(1, firstName);
			pstatement.setString(2, lastName);
			pstatement.setString(3, email);
			pstatement.setInt(4, userId);
			
			int result = pstatement.executeUpdate();

			return result > 0;
		} catch (Exception e) {
			throw e;
		} finally {
			Connections.getDBCloseConnection();
		}
	}

	@Override
	public boolean deleteUser(int userId) throws ClassNotFoundException, SQLException {
		try {
			String query = "DELETE from UserDetail WHERE id = ?";
			
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			pstatement.setInt(1, userId);
			
			int result = pstatement.executeUpdate();
			
			return result > 0;
		} catch (Exception e) {
			throw e;
		} finally {
			Connections.getDBCloseConnection();
		}

	}
}

