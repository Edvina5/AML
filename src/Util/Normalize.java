package Util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import DB.DBConnector;
import DB.QueryExecutor;

public class Normalize {
	
	
	public ResultSet getAllData(String table){
		
		QueryExecutor qe = new QueryExecutor();
		String query = "SELECT * FROM "+ table + ""; 
		ResultSet rs = null;
		
		try{
			rs = qe.executeQuery(query);
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		return rs;
	}
	
	public ArrayList<Integer> getCID() throws SQLException{
		
		Normalize norm = new Normalize();
		
		ArrayList<Integer> cid = new ArrayList<Integer>();
		ResultSet rs = norm.getAllData("table_03");
		
		while(rs.next()){
			cid.add(rs.getInt("CID"));
		}
		
		return cid;
		
	}
	
	public ArrayList<Integer> getLabel()throws SQLException{
		
		Normalize norm = new Normalize();
		
		ArrayList<Integer> label = new ArrayList<Integer>();
		ResultSet rs = norm.getAllData("table_03");
		
		while(rs.next()){
			label.add(rs.getInt("Label"));
		}
		
		return label;
	}
	
	public ArrayList<String> getDate() throws SQLException{
		
		Normalize norm = new Normalize();
		
		ArrayList<String> date = new ArrayList<String>();
		ResultSet rs = norm.getAllData("table_03");
		
		while(rs.next()){
			date.add(rs.getString("Date"));
		}
		
		return date;
		
	}
	
	public ArrayList<BigDecimal> getSF() throws SQLException{
		
		Normalize norm = new Normalize();
	
		ArrayList<BigDecimal> sf = new ArrayList<BigDecimal>();
		
		ResultSet rs = norm.getAllData("table_03");
		
		while(rs.next()){
			sf.add(rs.getBigDecimal("SF"));
		}
	
		return sf;
	}
	
	
	public ArrayList<BigDecimal> getRF() throws SQLException{
		
		Normalize norm = new Normalize();
	
		ArrayList<BigDecimal> rf = new ArrayList<BigDecimal>();
		
		ResultSet rs = norm.getAllData("table_03");
		
		while(rs.next()){
			rf.add(rs.getBigDecimal("RF"));
		}
		
		return rf;
	}
	

	public ArrayList<BigDecimal> getRA() throws SQLException{
	
		Normalize norm = new Normalize();
	
		ArrayList<BigDecimal> ra = new ArrayList<BigDecimal>();
		
		ResultSet rs = norm.getAllData("table_03");
		
		while(rs.next()){
			ra.add(rs.getBigDecimal("RA"));
		}
		
		return ra;
	}
	
	public ArrayList<BigDecimal> getSA() throws SQLException{
		
		Normalize norm = new Normalize();

		ArrayList<BigDecimal> sa = new ArrayList<BigDecimal>();
		
		ResultSet rs = norm.getAllData("table_03");
		
		while(rs.next()){
			sa.add(rs.getBigDecimal("SA"));
		}
		
		return sa;
	}
	
	public ArrayList<BigDecimal> normSA() throws SQLException{
		
		Normalize norm = new Normalize();
		BigDecimal min = new BigDecimal("0");
		BigDecimal max = new BigDecimal("0");
		
		int i;
		
		ArrayList<BigDecimal> sa = new ArrayList<BigDecimal>();
		sa = norm.getSA();

		ArrayList<BigDecimal> normSA = new ArrayList<BigDecimal>();
		
		int index_min = sa.indexOf(Collections.min(sa));
		int index_max = sa.indexOf(Collections.max(sa));
		
		
		min = sa.get(index_min);  //min value of SA column
		max = sa.get(index_max);  //max value of SA column
			
		
		for(i = 0; i< sa.size(); i++){
			if(sa.get(i).equals(min)){
				normSA.add(sa.get(i));
			}else{
				normSA.add(sa.get(i).subtract(min).divide(max.subtract(min), 6, BigDecimal.ROUND_HALF_UP));
			}
		}	
		
		return normSA;
	}
	
	
	public ArrayList<BigDecimal> normRA() throws SQLException{
		
		Normalize norm = new Normalize();
		BigDecimal min = new BigDecimal("0");
		BigDecimal max = new BigDecimal("0");
		
		int i;
		
		ArrayList<BigDecimal> ra = new ArrayList<BigDecimal>();
		ra = norm.getRA();
		
		ArrayList<BigDecimal> positive = new ArrayList<BigDecimal>();
		ArrayList<BigDecimal> normRA = new ArrayList<BigDecimal>();
		
		//multiply by -1 to change to positive
		BigDecimal minus = new BigDecimal(-1);
		
		//change negative values to positive
		for(int j = 0; j < ra.size(); j++){
			if(ra.get(j).signum() < 0){
				positive.add(ra.get(j).multiply(minus));
			}else{
				positive.add(ra.get(j));
			}
		}
		
	
		int index_min = positive.indexOf(Collections.min(positive));
		int index_max = positive.indexOf(Collections.max(positive));
		
		
		min = positive.get(index_min);  //min value of RA column
		max = positive.get(index_max);  //max value of RA column
		
		for(i = 0; i< positive.size(); i++){
			if(positive.get(i).equals(min)){
				normRA.add(ra.get(i));
			}else{
				normRA.add(positive.get(i).subtract(min).divide(max.subtract(min), 6, BigDecimal.ROUND_HALF_UP));
			}
		}
				
		return normRA;
	}
	
	
	public ArrayList<BigDecimal> normSF() throws SQLException{
		
		Normalize norm = new Normalize();
		BigDecimal min;
		BigDecimal max;
		
		int i;
		
		ArrayList<BigDecimal> sf = new ArrayList<BigDecimal>();
		sf = norm.getSF();

		ArrayList<BigDecimal> normSF = new ArrayList<BigDecimal>();
		
		int index_min = sf.indexOf(Collections.min(sf));
		int index_max = sf.indexOf(Collections.max(sf));
		
		
		min = sf.get(index_min);  //min value of SF column
		max = sf.get(index_max);  //max value of SF column
			
		//System.out.println(min);
		//System.out.println(max);
		
		
		for(i = 0; i< sf.size(); i++){
			if(sf.get(i).equals(min)){
				normSF.add(sf.get(i));
			}else{
				normSF.add(sf.get(i).subtract(min).divide(max.subtract(min), 6, BigDecimal.ROUND_HALF_UP));
			}
		}	
		
		return normSF;
	}
	
	
	public ArrayList<BigDecimal> normRF() throws SQLException{
		
		Normalize norm = new Normalize();
		BigDecimal min;
		BigDecimal max;
		
		int i;
		
		ArrayList<BigDecimal> rf = new ArrayList<BigDecimal>();
		rf = norm.getRF();

		ArrayList<BigDecimal> normRF = new ArrayList<BigDecimal>();
		
		int index_min = rf.indexOf(Collections.min(rf));
		int index_max = rf.indexOf(Collections.max(rf));
		
		min = rf.get(index_min);  //min value of RF column
		max = rf.get(index_max);  //max value of RF column
			
		//System.out.println(min);
		//System.out.println(max);
		
		for(i = 0; i< rf.size(); i++){
			if(rf.get(i).equals(min)){
				normRF.add(rf.get(i));
			}else{
				normRF.add(rf.get(i).subtract(min).divide(max.subtract(min), 6, BigDecimal.ROUND_HALF_UP));
			}
		}	
		
		return normRF;
	}
	
	
	public void storeNormData(String table_name) throws SQLException{
		
		Normalize norm = new Normalize();
		
		int i;
		int cid;
		int label;
		BigDecimal sf, rf, sa, ra;
		String date;
		
		PreparedStatement prs = null;
		
		ArrayList<Integer> CID = norm.getCID();
		ArrayList<BigDecimal> SF = norm.normSF();
		ArrayList<BigDecimal> RF = norm.normRF();
		ArrayList<BigDecimal> SA = norm.normSA();
		ArrayList<BigDecimal> RA = norm.normRA();
		ArrayList<String> DATE = norm.getDate();
		ArrayList<Integer> LABEL = norm.getLabel();
		
		for(i = 0; i < CID.size(); i++){
			cid = CID.get(i);
			sf = SF.get(i);
			rf = RF.get(i);
			sa = SA.get(i);
			ra = RA.get(i);
			date = DATE.get(i);
			label = LABEL.get(i);
			
			String query = "INSERT INTO "+table_name+" VALUES('"+cid+"','"+sf+"', '"+rf+"', '"+sa+"', '"+ra+"', '"+date+"', '"+label+"')";
			prs = (PreparedStatement) DBConnector.connect().prepareStatement(query);
			prs.execute();
			System.out.println("Import rows " + i);
			DBConnector.closeConnection();
		}
		prs.close();
		System.out.println("Success!");
		
	}
	
	

	public static void main(String[] args) throws SQLException{
		
		Normalize norm = new Normalize();
		norm.storeNormData("table_02");
		//norm.normRF();
	}

}
