package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DB.QueryExecutor;
import DB.DBConnector;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.ResultSetMetaData;

public class FinancialData {
	
	
	public String getTable(String username){       //get the name of the table that user stores his costumers data
		
		String table_name = "";
		QueryExecutor qe = new QueryExecutor();
		String query = "SELECT Table_name FROM client WHERE Username='" + username + "'";
		ResultSet rs = null;
		
		try{
			rs = qe.executeQuery(query);
			if(rs.next()){
				table_name = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return table_name;
	}
	
	
	
	public static ArrayList<Customer> getData(String CID, String table_name){
		
		QueryExecutor qe = new QueryExecutor();
		String query = "SELECT * FROM "+ table_name + " WHERE CID='" + CID + "'" + "ORDER BY Date DESC";
		
		ResultSet rs = null;
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		try{
			rs = qe.executeQuery(query);
			
			while(rs.next()){
				Customer customer = new Customer();
				customer.setCID(rs.getInt("CID"));
				customer.setSF(rs.getInt("SF"));
				customer.setRF(rs.getInt("RF"));
				customer.setSA(rs.getBigDecimal("SA"));
				customer.setRA(rs.getBigDecimal("RA"));
				customer.setDate(rs.getString("Date"));
				customers.add(customer);
			}
			
			
			/*
			for(int i = 0; i <= customers.size(); i++){
				System.out.println(customers.get(i).getCID());
				System.out.println(customers.get(i+1).getSF());
				System.out.println(customers.get(i+2).getRF());
				System.out.println(customers.get(i+3).getSA());
				System.out.println(customers.get(i+4).getRA());
				System.out.println(customers.get(i+5).getDate());
			}
			*/
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return customers;
		
	}
		

}
