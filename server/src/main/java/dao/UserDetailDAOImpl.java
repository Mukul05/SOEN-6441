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
	private static int DEFAULT_PAGE_NUMBER = 1;
	private static int DEFAULT_PAGE_SIZE = 10;

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
			int result = 0;
			UserDetails cUser = currentDetail(id);
			if (cUser == null)
			{
						
			String insertstmt = "insert into UserDetail values(?,?,?,?)";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(insertstmt);
			pstatement.setInt(1, id);
			pstatement.setString(2, firstName);
			pstatement.setString(3, lastName);
			pstatement.setString(4, email);

			result = pstatement.executeUpdate();

			
			}
			else
			{
				System.out.println("inside insert update");
				
				return updateUser(id,bean, cUser);
				
			}
				
			
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
	public List<UserDetails> readUser(String name) throws ClassNotFoundException, SQLException {
		try {
			List<UserDetails> users = new ArrayList<>();
			
			String query="";
		
			query = "SELECT * FROM UserDetail where LOWER(firstName) like Lower('%" + name + "%') or LOWER(lastName) like Lower('%" + name + "%')";
			
			
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			ResultSet resultSet = pstatement.executeQuery();
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String fn = resultSet.getString("firstName");
				String ln = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				UserDetails currentUser = new UserDetails( id, fn, ln, email);
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
	public int userCount() throws ClassNotFoundException, SQLException {
		try {
			
				String query = "SELECT count(*) as count from userDetail";
			
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			ResultSet resultSet = pstatement.executeQuery();
			int count =0;
			if(resultSet.next()){
				   count = resultSet.getInt(1);
				}
			
			return count;
		} catch (Exception e) {
			throw e;
		}
		finally {
			Connections.getDBCloseConnection();
			}
	}

	@Override
	public boolean updateUser(int userId, UserDetails user, UserDetails cUser) throws SQLException, ClassNotFoundException {
		try {		
			
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String email = user.getEmail();
			if(cUser.getEmail().equals(email))
			{
			String query = "UPDATE UserDetail SET firstName= ? ,lastName = ? , email=? WHERE id = ?";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			
			pstatement.setString(1, firstName);
			pstatement.setString(2, lastName);
			pstatement.setString(3, email);
			pstatement.setInt(4, userId);
			
			int result = pstatement.executeUpdate();

			return result > 0;
			}
			else
			{	
				System.out.println("Emailid not same");
				return false;
			}
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
	
	public UserDetails currentDetail(int id) throws ClassNotFoundException, SQLException {
		try {
			//return readUser(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE);
			List<UserDetails> currentusers = new ArrayList<>();
			String query = "SELECT * FROM UserDetail where id = ?";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			pstatement.setInt(1, id);
			ResultSet resultSet = pstatement.executeQuery();
			
				if(resultSet.next() == false)
					return null;
				else
				{
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");
				UserDetails currentUser = new UserDetails( id, firstName, lastName, email);
				return currentUser;
				}
			
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	public boolean truncateTable() throws ClassNotFoundException, SQLException {
		try {
			String query = "TRUNCATE TABLE UserDetail";
			
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(query);
			
			
			int result = pstatement.executeUpdate();
			
			return result > 0;
		} catch (Exception e) {
			throw e;
		} finally {
			Connections.getDBCloseConnection();
		}

	}
	
}

