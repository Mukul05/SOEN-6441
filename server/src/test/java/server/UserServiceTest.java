/**
 * 
 */
package server;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import bean.UserDetails;
import services.UserService;

/**
 * @author mukul
 *
 */
@TestMethodOrder(OrderAnnotation.class)
class UserServiceTest {

	
	static int count = 0;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
	 * Test method for {@link services.UserService#readUser()}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(1)
	void testReadUser() throws ClassNotFoundException, SQLException {
		//fail("Not yet implemented"); // TODO
		System.out.println("Inside Testing Read User");
		UserService read = new UserService();
		count = read.readUser();
		boolean isgreater = false;
		if (count>0)
			isgreater=true;
		assertEquals(true,isgreater);
	}


	/**
	 * Test method for {@link services.UserService#insertUser(bean.UserDetails)}.
	 * @throws Exception 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(2)
	void testInsertUser() throws ClassNotFoundException, SQLException, Exception {
		//fail("Not yet implemented"); // TODO
		count = count + 1;
		System.out.println("Inside Testing Insert User");
		UserDetails user = new UserDetails(count,"TestName","TestLast","TestEmail22");
		UserService insert = new UserService();
		assertEquals(true,insert.insertUser(user));
		
	}

	
	/**
	 * Test method for {@link services.UserService#updateUser(bean.UserDetails)}.
	 * @throws Exception 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(3)
	void testUpdateUser() throws ClassNotFoundException, SQLException, Exception {
		//fail("Not yet implemented"); // TODO
		System.out.println("Inside Testing Update User");
		UserDetails user = new UserDetails(count,"TestNameUpdated","TestLastUpdate","TestEmail222");
		UserService update = new UserService();
		assertEquals(false,update.updateUser(user));
		UserDetails user2 = new UserDetails(count,"TestNameUpdated","TestLastUpdate","TestEmail22");
		assertEquals(true,update.updateUser(user2));
	}

	/**
	 * Test method for {@link services.UserService#deleteUser(int)}.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Test
	@Order(4)
	void testDeleteUserInt() throws ClassNotFoundException, SQLException {
		//fail("Not yet implemented"); // TODO
		System.out.println("Inside Testing Delete User");
		UserService delete = new UserService();
		assertEquals(true,delete.deleteUser(count));
	}

	

}
