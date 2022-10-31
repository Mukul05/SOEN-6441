package services;

import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bean.UserDetails;
import dao.UserDetailDAOImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/api")	
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDetailDAOImpl userDao = UserDetailDAOImpl.getInstance();
	private static final Logger LOGGER = Logger.getLogger(User.class.getName());
	private static int DEFAULT_PAGE_NUMBER = 1;
	private static int DEFAULT_PAGE_SIZE = 10;
	
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
		response.addHeader("Access-Control-Allow-Origin", "*");
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Max-Age", "86400");
		response.addHeader("Access-Control-Allow-Headers", "x-requested-with");

	    switch (action) {
	        case "/user":
	        try {
				createUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          break;
	        default:
	          ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
	          RequestDispatcher dispatcher = request.getRequestDispatcher("/user");
	          dispatcher.include(request,(ServletResponse) response);
	    }
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		response.addHeader("Access-Control-Allow-Origin", "*");
	    switch (action) {
	        case "/user":
	        try {
				deleteUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          break;
	        default:
	          ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
	          RequestDispatcher dispatcher = request.getRequestDispatcher("/user");
	          dispatcher.include(request,(ServletResponse) response);
	    }
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD");
	    switch (action) {
	        case "/user":
	        try {
				updateUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          break;
	        default:
	          ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NOT_FOUND);
	          RequestDispatcher dispatcher = request.getRequestDispatcher("/user");
	          dispatcher.include(request,(ServletResponse) response);
	    }
	}
	
	private void getUserById(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException {
    	try {
			UserDetails user = userDao.getUserById(userId);
			PrintWriter out = null;
			try {
				out = ((ServletResponse) response).getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
		    ((ServletResponse) response).setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		   
		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    String json = gson.toJson(user);
		    out.print(json);
		    out.flush(); 
		    response.getWriter().close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("userId");
		String pageNumber = request.getParameter("pageNumber");
	    String pageSize = request.getParameter("pageSize");
	    if (userId != null) {
	    	getUserById(request, response, userId);
	    }
	    if(!UserDetails.validateGet(pageNumber, pageSize)) {
	    	PrintWriter out = response.getWriter();
		    String responseMessage = "Invalid request parameters";
	    	out.print(responseMessage);
	    	out.flush();
	    	response.getWriter().close();
	    }
	    List<UserDetails> users = null;
	    LOGGER.log(Level.INFO, "here");
		try {
			int pNum = DEFAULT_PAGE_NUMBER;
			int pSize = DEFAULT_PAGE_SIZE;
			if (pageNumber != null) {
				pNum = Integer.parseInt(pageNumber);
			}
			if (pageSize != null) {
				pSize = Integer.parseInt(pageSize);
			}
			users = userDao.readUser(pNum, pSize);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	    PrintWriter out = null;
		try {
			out = ((ServletResponse) response).getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    ((ServletResponse) response).setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	   
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String json = gson.toJson(users);
	    out.print(json);
	    out.flush();  
	}


	private void createUser(HttpServletRequest req, HttpServletResponse response) throws SQLException, ServletException, IOException{
	    BufferedReader reader = req.getReader();
	    Gson gson = new Gson(); 	
	    UserDetails userData = gson.fromJson(reader, UserDetails.class);
	    boolean success = false;
		try {
			success = userDao.insertUser(userData);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    String responseMessage = success ? "Succesfully created user" : "Failed to create user";
	    
	    PrintWriter out = ((ServletResponse) response).getWriter();
	    ((ServletResponse) response).setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    out.print(responseMessage);
	    out.flush();  
	  }

	  private void updateUser(HttpServletRequest req, HttpServletResponse response) throws SQLException, ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("userId"));
		BufferedReader reader = req.getReader();
	    Gson gson = new Gson(); 	
	    UserDetails userData = gson.fromJson(reader, UserDetails.class);
	    boolean success = false;
		try {
			success = userDao.updateUser(id, userData);
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

	  private void deleteUser(HttpServletRequest req, HttpServletResponse response) throws SQLException, ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("userId"));;
	    boolean success = false;
		try {
			success = userDao.deleteUser(id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
