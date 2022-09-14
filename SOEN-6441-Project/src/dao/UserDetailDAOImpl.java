package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.Connections;
import bean.UserDetails;
public class UserDetailDAOImpl implements UserDetailDAO {

	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	
	@Override
	public int insertUser(UserDetails bean) throws ClassNotFoundException, SQLException, Exception {
		
		try {
			int rowsInsertCount = 0;
			String firstName = bean.getFirstName();
			String lastName = bean.getLastName();
			String email = bean.getEmail();
			
			String insertstmt = "insert into UserDetail values(?,?,?)";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(insertstmt);
			pstatement.setString(1, firstName);
			pstatement.setString(2, lastName);
			pstatement.setString(3, email);
			
			rowsInsertCount = pstatement.executeUpdate();
			
			return rowsInsertCount;
		} catch (Exception e) {
			throw e;
		} finally {
			
			Connections.getDBCloseConnection();
		}
	}
	
	@Override
	public void readUser() throws ClassNotFoundException, SQLException {
		try {
			String retrievedata = "select * from UserDetail";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(retrievedata);
			ResultSet resultSet = pstatement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Fist Name: " + resultSet.getString("firstName") + "\nLast Name: "
						+ resultSet.getString("lastName") + "\nEMail: " + resultSet.getString("email"));
						System.out.println("===========================================");
			}
		} catch (Exception e) {
			throw e;
		}
		finally {
					Connections.getDBCloseConnection();
					}
	}
	
	@Override
	public void updateUser(UserDetails bean) throws SQLException, ClassNotFoundException {
		try {
			String firstName = bean.getFirstName();
			String lastName = bean.getLastName();
			String email = bean.getEmail();
			
			String updatedata = "update UserDetail set email=? where firstName= ? and lastName = ?";
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(updatedata);
			pstatement.setString(1, firstName);
			pstatement.setString(2, lastName);
			pstatement.setString(3, email);
			boolean updateresult = pstatement.execute();
			if (updateresult != true) {
				System.out.println("User updated successfully!!");
			} else {
				System.out.println("OOps some thing went wrong!! ");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			
				Connections.getDBCloseConnection();
			}
	}
	
	@Override
	public void deleteUser(UserDetails bean) throws ClassNotFoundException, SQLException {
		try {
			String firstName = bean.getFirstName();
			String lastName = bean.getLastName();
			String email = bean.getEmail();
			boolean firstNameExist = false;
			boolean lastNameExist = false;
			boolean emailExist = false;
			if (firstName != null && firstName != "")
				firstNameExist = true;
			
			if (lastName != null && lastName != "")
				lastNameExist = true;
			
			if (email != null && email != "")
				emailExist = true;
			
			String deletedata="";
			
			if(firstNameExist)
			deletedata = "DELETE from UserDetail WHERE firstName = ? " ;
			conn = Connections.getDBConnection();
			PreparedStatement pstatement = conn.prepareStatement(deletedata);
			pstatement.setString(1, firstName);
			int deleted = pstatement.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
							Connections.getDBCloseConnection();
					}

	}
}

	
	

