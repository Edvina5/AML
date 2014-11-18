package Authentification;
import java.sql.SQLException;

import DB.QueryExecutor;
import DB.DBConnector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;;

public class Authentification {
	public static boolean hasLogin(String username, String password, String group) throws SQLException {
		QueryExecutor qe = new QueryExecutor();
		String query = "SELECT * FROM client WHERE Username='" + username + "'" + "AND Password='" + password + "'" + "AND Tenant ='" + group + "'";
		ResultSet rs = null;
		try {
			rs = qe.executeQuery(query);
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Not sure about this
		DBConnector.closeConnection();
		
		
		return false;
	}
	/**
	 * parameters = [
	 * @param parameters
	 * @return
	 */

	public static boolean register(String username, String name, String surname, String password, String tenant, String email) {
	
		String query = "INSERT INTO client (Username, Name, Surname, Password, Tenant, Email) Values (?, ?, ?, ?, ?, ?)";
	
		try{
			
			PreparedStatement prp = (PreparedStatement) DBConnector.connect().prepareStatement(query);
			prp.setString(1, username);
			prp.setString(2, name);
			prp.setString(3, surname);
			prp.setString(4, password);
			prp.setString(5, tenant);
			prp.setString(6, email);
			
			prp.execute();
			
			DBConnector.closeConnection();

		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return true;
	}
	
	public static boolean userExists (String username){
		
		QueryExecutor qe = new QueryExecutor();
		
		String query = "SELECT * FROM client WHERE Username='" + username + "'";
		ResultSet rs = null;
		
		try{
			rs = qe.executeQuery(query);
			if(rs.next()){
				return true;
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
	
		return false;
	}
	
	public String getTable(String username){
		
		String table = "";
		
		QueryExecutor qe = new QueryExecutor();
		
		String query = "SELECT Table_name FROM client WHERE Username='" + username + "'";
		ResultSet rs = null;
		
		try{
			rs = qe.executeQuery(query);
			if(rs.next()){
				table = rs.getString(1);
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return table;
	}
	
	
	
	
}
