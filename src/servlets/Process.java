package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Util.Sets;

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
		
		HttpSession session = request.getSession();
		
		String[] method = request.getParameterValues("analysis-methods");
		String[] transactions = request.getParameterValues("rowSelector");
		ArrayList<String> set = new ArrayList<String>();
		ArrayList<String> cont = new ArrayList<String>();
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> data = new ArrayList<String>();
		
		
		String noTrans = "You must select a transaction(s)";
		
		
		//print out the list of selected transactions
		
		
		if(transactions != null){
			for(String t : transactions){
				
				StringBuilder builder = new StringBuilder();
				
				String cid = "";
				String date = "";
				String content = "";
	
				builder.append(t.replace(" ", ","));
				int index = builder.indexOf(",");
				int index2 = builder.lastIndexOf(",");
				cid = builder.substring(0, index);
				date = builder.substring(index2+1,builder.length());
				
				content = builder.substring(index+1,index2);
				
				if(content.contains("0.00")){
					content = content.replace("0.00", "0");
				}
				cont.add(content);
				
				//System.out.println(cid);
				//System.out.println(date);
				//System.out.println(content);
			}
		}else{
			session.setAttribute("error", noTrans);
		}
		
		
		if(method[0].equals("SOM")){
			set = Sets.getSOMPredictions();
			
			for(String c : cont){
				//System.out.println(c);
				
				int index = c.indexOf(".");
				
				if(c.contains(".") && c.endsWith("0")){
					 c = c.substring(0, index+2);
				}
				//System.out.println(c);
				
				for(String c2 : set){
					//System.out.println(c2);
					if(c2.contains(c)){
						//System.out.println(c2.substring(c2.length()-1));
						results.add(c2.substring(c2.length()-1));
					}
					
					
				}
			}
			
			for(int i = 0 ; i < cont.size(); i++){
				String temp = "";
				temp = (cont.get(i).concat(" Suspicious? "+results.get(i)));
				//System.out.println(temp);
				data.add(temp);
			}
			
			
				
			//System.out.println("Self Organizing Map");
			request.setAttribute("data", data);
			request.getRequestDispatcher("results.jsp").forward(request, response);
		}
		
		
		
		else if(method[0].equals("Bayes")){
			set = Sets.getNaivePrediction();
			
			for(String c : cont){
				//System.out.println(c);
				
				int index = c.indexOf(".");
				
				if(c.contains(".") && c.endsWith("0")){
					 c = c.substring(0, index+2);
				}
				//System.out.println(c);
				
				for(String c2 : set){
					//System.out.println(c2);
					if(c2.contains(c)){
						//System.out.println(c2.substring(c2.length()-1));
						results.add(c2.substring(c2.length()-1));
					}
					
				}
			}
			
			for(int i = 0 ; i < cont.size(); i++){
				String temp = "";
				temp = (cont.get(i).concat(" Suspicious? "+results.get(i)));
				//System.out.println(temp);
				data.add(temp);
			}
			
			for(String s : data){
				System.out.println(s);
			}
			
			//System.out.println("Naive Bayes");
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("results.jsp").forward(request, response);
		}
		

	}

}
