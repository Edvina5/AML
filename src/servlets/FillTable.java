package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Util.Customer;
import Util.FinancialData;
import Authentification.Authentification;
import DB.QueryExecutor;

/**
 * Servlet implementation class FillTable
 */
@WebServlet("/filltable")

public class FillTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	private QueryExecutor queryExecutor;
	
	public void init(ServletConfig config) throws ServletException {
		queryExecutor = new QueryExecutor();
	}
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		FinancialData fd = new FinancialData();
		
		String CID = request.getParameter("customer");  //cid from table_01
		String message = (String) session.getAttribute("welcome_msg");
		String username = message.substring(6).replace(" !", "");     //to get the username for the table query
		String table_name = "";
		
		String emptyID = "You did not enter an user ID";
		String wrongFormat = "Please enter ID in digit format";				//all the error messages
		String customerExist = "Ther user with this ID does not exit";
		
		ArrayList<Customer> data = new ArrayList<Customer>();				//list where all customers will be stored
		
		table_name = fd.getTable(username);
			
		if(CID.isEmpty()){
			session.setAttribute("error", emptyID);
			response.sendRedirect("regularUser.jsp");
		}else if(!CID.matches("-?\\d+(\\.\\d+)?")){         //check if customer id is a digit 
			session.setAttribute("error", wrongFormat);
			response.sendRedirect("regularUser.jsp");
		}
		else if(!Authentification.customerExists(CID, table_name)){
			session.setAttribute("error", customerExist);
			response.sendRedirect("regularUser.jsp");				
		}else{
			data = FinancialData.getData(CID, table_name);
			request.setAttribute("data", data);
			request.getRequestDispatcher("regularUser.jsp").forward(request, response);
		}
		
		
	}

}
