package services;

import java.io.PrintWriter;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.UserDetails;
import dao.UserDetailDAOImpl;
import jakarta.servlet.http;

public class UserServlet extends HttpServlet {
  
  private UserDetailDAOImpl userDao = UserDetailDAOImpl.getInstance();
  private static final Logger LOGGER = Logger.getLogger(UserServlet.class.getName());

  @Override
  protected void doPost(HttpServletRequest req, HttpServletRequest resp) throws ServletException, IOException {

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletRequest response) throws ServletException, IOException {
    String action = req.getServletPath();
    try {
      switch (action) {
        case "/user":
          getUsers(req, response);
          break;
        default:
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          RequestDispatcher dispatcher = req.getRequestDispatcher("/user");
          dispatcher.include(req,response);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "SQL Error", e);
    }
  }

  private void getUsers(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
    int pageNumber = req.getParameter("pageNumber");
    int pageSize = req.getParameter("pageSize");
    List<UserDetails> users = userDao.readUser(pageNumber, pageSize);

    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(users);
    out.flush();  
  }

  private void createeUser(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
    int id = req.getParameter("userId");
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String email = req.getParameter("email");
    UserDetails user = new UserDetails(id, firstName, lastName, email);
    boolean success = userDao.insertUser(user);
    String responseMessage = success ? "Succesfully created user" : "Failed to create user";
    
    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(responseMessage);
    out.flush();  
  }

  private void updateUser(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
    int id = req.getParameter("userId");
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String email = req.getParameter("email");
    UserDetails user = new UserDetails(id, firstName, lastName, email);
    boolean success = userDao.updateUser(user);
    String responseMessage = success ? "Succesfully updated user details" : "Failed to update user details";
    
    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(responseMessage);
    out.flush();  
  }

  private void deleteUser(HttpServletRequest req, HttpServletRequest response) throws SQLException, ServletException, IOException{
    int id = req.getParameter("userId");
    boolean success = userDao.deleteUser(id);
    String responseMessage = success ? "Succesfully deleted user" : "Failed to delete user";
    
    PrintWriter out = response.getWriter();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    out.print(responseMessage);
    out.flush();  
  }
}
