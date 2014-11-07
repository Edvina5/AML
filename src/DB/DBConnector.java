package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
	private static Connection con = null;
	
	/**
	 * Connect to Database
	 * @return con - Connection object
	 */
	public static Connection connect() {
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "userlogindb";
		String driver = "com.mysql.jdbc.Driver";
		String dbUserName = "root";
		String dbPassword = "";
		try{
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url+dbName,dbUserName,dbPassword);
		}catch(Exception ex){
			System.out.println(ex);
		}
		return con;
	}
	
	/**
	 * Close the database connection.
	 */
	public static void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
       
}
