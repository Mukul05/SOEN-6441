package services;

import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jasper.JspC;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
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
	private API usersapi = API.getInstance();
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
	    switch (action) {
	        case "/user":
	          try {
				getUsers(request, response);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
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
	        case "/user/reset":
	        	try {
					resetUsers(request, response);
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
	
	private void getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		String pageNumber = request.getParameter("pageNumber");
	    String pageSize = request.getParameter("pageSize");
	    String searchQuery = request.getParameter("searchQuery");
	    BufferedReader reader = request.getReader();
	    Gson gson = new Gson(); 	
	    UserDetails userData = gson.fromJson(reader, UserDetails.class);
	    boolean success = false;
	    if(!UserDetails.validateGet(pageNumber, pageSize)) {
	    	PrintWriter out = response.getWriter();
		    String responseMessage = "Invalid request parameters";
	    	out.print(responseMessage);
	    	out.flush();
	    	return;
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
			System.out.println(searchQuery);
			if(searchQuery == null)
				users = userDao.readUser(pNum, pSize);
			else
				users = userDao.readUser(searchQuery);
			
			   
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

	   
	    Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
	    String json = gson1.toJson(users);
	    
	    json = "{\n users: " + json + ",\ncount: "+ userDao.userCount() +"\n}";
	    JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
	    out.print(convertedObject);
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
	    if(success == true)
	    {
	    responseMessage = "{\"message\":\"" + responseMessage+"\",\"error\":\"false\"}";
	    JSONObject jsonObj = new JSONObject(responseMessage);
	    out.print(jsonObj);
	    }
	    else
	    {responseMessage = "{\"message\":\"" + responseMessage+"\",\"error\":\"true\"}";
	    JSONObject jsonObj = new JSONObject(responseMessage);
	    out.print(jsonObj);
	    }
	    out.flush();  
	  }

	  private void updateUser(HttpServletRequest req, HttpServletResponse response) throws SQLException, ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("userId"));
		BufferedReader reader = req.getReader();
	    Gson gson = new Gson(); 	
	    UserDetails userData = gson.fromJson(reader, UserDetails.class);
	    boolean success = false;
		try {
			success = userDao.updateUser(id, userData, userDao.currentDetail(id));
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
	    if(success == true)
	    {
	    responseMessage = "{\"message\":\"" + responseMessage+"\",\"error\":\"false\"}";
	    JSONObject jsonObj = new JSONObject(responseMessage);
	    out.print(jsonObj);
	    }
	    else
	    {responseMessage = "{\"message\":\"" + responseMessage+"\",\"error\":\"true\"}";
	    JSONObject jsonObj = new JSONObject(responseMessage);
	    out.print(jsonObj);
	    }
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
	    if(success == true)
	    {
	    responseMessage = "{\"message\":\"" + responseMessage+"\",\"error\":\"false\"}";
	    JSONObject jsonObj = new JSONObject(responseMessage);
	    out.print(jsonObj);
	    }
	    else
	    {responseMessage = "{\"message\":\"" + responseMessage+"\",\"error\":\"true\"}";
	    JSONObject jsonObj = new JSONObject(responseMessage);
	    out.print(jsonObj);
	    }
	    out.flush();  
	  }
	  
	  private void resetUsers(HttpServletRequest req, HttpServletResponse response) throws SQLException, ServletException, IOException{
		    boolean success = false;
			try {
				
				success = usersapi.resetData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		    String responseMessage = success ? "API users created (Data Resfreshed)" : "Failed to create user";
		    
		    PrintWriter out = ((ServletResponse) response).getWriter();
		    ((ServletResponse) response).setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    out.print(responseMessage);
		    out.flush(); 
		  }
}
