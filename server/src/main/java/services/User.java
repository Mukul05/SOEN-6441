package services;

import java.sql.SQLException;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());

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
		Integer pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
	    Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
	    List<UserDetails> users = null;
	    LOGGER.log(Level.INFO, "here");
		try {
			users = userDao.readUser(pageNumber, pageSize);
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
	    out.print(users);
	    out.flush();  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
