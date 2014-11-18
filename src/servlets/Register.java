package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Authentification.Authentification;
import DB.QueryExecutor;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
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
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String password = request.getParameter("password");
		String tenant = request.getParameter("tenant").toUpperCase();
		String email = request.getParameter("email");
	
		
		if(username.isEmpty() || name.isEmpty() || surname.isEmpty() || password.isEmpty() || tenant.isEmpty() || email.isEmpty()){
			String message = "Please fill out all fields";
			session.setAttribute("message", message);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.sendRedirect("registration.jsp");
		}
		else{
		
		try{
			if(Authentification.userExists(username)){
				String message = "This user already exists! Pick different user name!";
				session.setAttribute("message", message);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.sendRedirect("registration.jsp");
			}else if(Authentification.register(username, name, surname, password, tenant, email)){
				String message = "You have registered successfully! You can log in now!";
				session.setAttribute("message", message);
				response.sendRedirect("index.jsp");
			}
		
			
		}catch(Exception ex){
			System.out.println(ex);
			}
		}
		
	}

}
