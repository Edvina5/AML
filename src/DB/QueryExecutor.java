package DB;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.SQLException;


public class QueryExecutor {
	public Connection con;
	
	/**
	 * Default constructor.
	 */
	public QueryExecutor() {
		//Connect to database and get Connection object
		this.con = DBConnector.connect();
	}
	

	public ResultSet executeQuery(String query) throws SQLException {
		Statement st = (Statement) con.createStatement();
		ResultSet rs = (ResultSet) st.executeQuery(query);
		
		return rs;
	}
}
