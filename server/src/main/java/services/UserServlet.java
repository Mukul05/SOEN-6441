package services;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.UserDetails;
import dao.UserDetailDAO;
import dao.UserDetailDAOImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MyUser")
public class UserServlet extends HttpServlet {
  
  private UserDetailDAOImpl userDao = UserDetailDAOImpl.getInstance();
  private static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());

  protected void doPost(HttpServletRequest req, HttpServletRequest resp) throws ServletException, IOException {

  }

  
  protected void doGet(HttpServletRequest req, HttpServletRequest response) throws ServletException, IOException {
    String action = req.getServletPath();
    try {
      switch (action) {
        case "/user":
          getUsers(req, response);
          break;
        default:
          ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
          RequestDispatcher dispatcher = req.getRequestDispatcher("/user");
          dispatcher.include(req,(ServletResponse) response);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "SQL Error", e);
    }
  }

  private void getUsers(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
    String pageNumber = req.getParameter("pageNumber");
    String pageSize = req.getParameter("pageSize");
    List<UserDetails> users = null;
	try {
		users = userDao.readUser();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    PrintWriter out = ((ServletResponse) response).getWriter();
    ((ServletResponse) response).setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(users);
    out.flush();  
  }

  private void createeUser(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
	
	int id = Integer.parseInt(req.getParameter("UserId"));
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String email = req.getParameter("email");
    UserDetails user = new UserDetails(id, firstName, lastName, email);
    boolean success = false;
	try {
		success = userDao.insertUser(user);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String responseMessage = success ? "Succesfully created user" : "Failed to create user";
    
    PrintWriter out = ((ServletResponse) response).getWriter();
    ((ServletResponse) response).setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(responseMessage);
    out.flush();  
  }

  private void updateUser(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
	int id = Integer.parseInt(req.getParameter("UserId"));
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String email = req.getParameter("email");
    UserDetails user = new UserDetails(id, firstName, lastName, email);
    boolean success = false;
	try {
		success = userDao.updateUser(user);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String responseMessage = success ? "Succesfully updated user details" : "Failed to update user details";
    
    PrintWriter out = ((ServletResponse) response).getWriter();
    ((ServletResponse) response).setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(responseMessage);
    out.flush();  
  }

  private void deleteUser(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
	int id = Integer.parseInt(req.getParameter("UserId"));;
    boolean success = false;
	try {
		success = userDao.deleteUser(id);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    String responseMessage = success ? "Succesfully deleted user" : "Failed to delete user";
    
    PrintWriter out = ((ServletResponse) response).getWriter();
    ((ServletResponse) response).setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(responseMessage);
    out.flush();  
  }
}
