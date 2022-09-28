package services;

import java.sql.SQLException;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jasper.JspC;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysql.cj.xdevapi.JsonArray;

import bean.UserDetails;
import dao.UserDetailDAOImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/api")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDetailDAOImpl userDao = UserDetailDAOImpl.getInstance();
	private static final Logger LOGGER = Logger.getLogger(User.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
	    switch (action) {
	        case "/user":
	          getUsers(request, response);
	          break;
	        default:
	          ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
	          RequestDispatcher dispatcher = request.getRequestDispatcher("/user");
	          dispatcher.include(request,(ServletResponse) response);
	    }
	}
	
	
	
	

	
	
	private void getUsers(HttpServletRequest request, HttpServletResponse response) {
		//Integer pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
	   // Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
	    List<UserDetails> users = null;
	    LOGGER.log(Level.INFO, "here");
		try {
			users = userDao.readUser();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	    PrintWriter out = null;
		try {
			out = ((ServletResponse) response).getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ((ServletResponse) response).setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	   
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String json = gson.toJson(users);
	     out.print(json);
	    out.flush();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void createUser(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
		
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
