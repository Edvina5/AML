package servlets;

import java.io.IOException;

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
		
		
		
		String customer_id = request.getParameter("customer");  //cid from table_01
		String message = (String) session.getAttribute("welcome_msg");
		String username = message.substring(6).replace(" !", "");     //to get the username for the table query
		String table = "";
		
		//System.out.println(username);
		
		Authentification au = new Authentification(); 
		table = au.getTable(username);
		
		//System.out.println(table);
		
		if(customer_id.isEmpty()){
			response.sendRedirect("regularUser.jsp");
		}else{
			try{
				
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
		
		
		
	}

}
