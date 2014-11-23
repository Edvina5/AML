package Util;

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
	

	public String getData(String CID, String table_name){	//get financial data
		
		String content = "";
		
		QueryExecutor qe = new QueryExecutor();
		String query = "SELECT * FROM "+ table_name + " WHERE CID='" + CID + "'" + "ORDER BY Date DESC";
		
		ResultSet rs = null;
		
		try{
			rs = qe.executeQuery(query);
			
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			while(rs.next()){
				 for (int i = 1; i <= columnsNumber; i++) {
			            if (i > 1){
			            System.out.print(", ");
			            String columnValue = rs.getString(i);
			            System.out.println(columnValue + " " + rsmd.getColumnName(i));
			            }
			        }
			        System.out.println("");
				
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return content;
		
	}
	

}
