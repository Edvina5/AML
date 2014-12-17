package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Process
 */
@WebServlet("/process")
public class Process extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String[] method = request.getParameterValues("analysis-methods");
		
		if(method[0].equals("SOM")){
			System.out.println("Self Organizing Map");
			response.sendRedirect("regularUser.jsp");
		}
		else if(method[0].equals("Bayes")){
			System.out.println("Naive Bayes");
			response.sendRedirect("regularUser.jsp");
		}
		else{
			System.out.println("Hi");
		}

	}

}
