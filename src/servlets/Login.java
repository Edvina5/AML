package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import DB.QueryExecutor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Authentification.Authentification;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QueryExecutor queryExecutor;
	
	public void init(ServletConfig config) throws ServletException {
		queryExecutor = new QueryExecutor();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String group = request.getParameter("Group");
		
		//Check if login fields are not empty
		if(username.isEmpty() || password.isEmpty()) {
			String message = "Please enter username and password";
			session.setAttribute("message", message);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect("index.jsp");
		}
		else {
			
			try {
				if(Authentification.hasLogin(username, password, group)) {
					 String welcome_msg = "Hello " + username + " !" ;
					 session.setAttribute("welcome_msg", welcome_msg);
					 request.setAttribute("username", username);
					 response.sendRedirect("regularUser.jsp");
				}
				else {
					//Return HTTP error 401 - authentification failed.
					String credentials = username;
					session.setAttribute("credentials", credentials);
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.sendRedirect("index.jsp");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
	}

}
