package servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		String customer_id = request.getParameter("customer");  //cid from table_01
		String message = (String) session.getAttribute("welcome_msg");
		String username = message.substring(6).replace(" !", "");     //to get the username for the table query
		String table_name = "";
		
		//System.out.println(username);
		
		
		table_name = fd.getTable(username);
		
		
		
		if(customer_id.isEmpty()){
			response.sendRedirect("regularUser.jsp");
			System.out.println("You haven't entered customer ID");  //Print this message later
		}else if(!customer_id.matches("-?\\d+(\\.\\d+)?")){         //check if customer id is a digit 
			System.out.println("You must enter a digit!");			//print this message later 
			response.sendRedirect("regularUser.jsp");
		}
		else{
			fd.getData(customer_id, table_name);
			response.sendRedirect("regularUser.jsp");
		}
		
		
		
	}

}
