package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.mysql.jdbc.ResultSet;

import DB.QueryExecutor;

public class Scenario {
	
	String table_name = "table_01";
	
	public List<Integer> getCIDS() throws SQLException{
		
		String query = "SELECT DISTINCT(CID) FROM "+ table_name + ""; ;
		QueryExecutor qe = new QueryExecutor();
		List<Integer> CIDs = new ArrayList<Integer>();
		
		try{
			ResultSet rs = qe.executeQuery(query);
		
			//put all distinct CIDs into arraylist
			while(rs.next()){
				CIDs.add(rs.getInt(1));
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		qe.con.close();
		//print out results in the arraylist
		/*
		for(int i=0; i <CIDs.size(); i++){
			System.out.println("hi " + CIDs.get(i));
		}
		*/
		return CIDs;
		
	}
	
	public List<BigDecimal> getRAs(int CID) throws SQLException{
		
		String query = "SELECT RA FROM " + table_name + " WHERE CID=" + CID +" AND RA != 0.00";
		QueryExecutor qe = new QueryExecutor();
		
		List<BigDecimal> RAs = new ArrayList<BigDecimal>();
		List<BigDecimal> PositiveRAs = new ArrayList<BigDecimal>();
		
		try{
			ResultSet rs = qe.executeQuery(query);
			
			while(rs.next()){
				RAs.add(rs.getBigDecimal(1));
			}
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		//check if value is negative, convert it to positive and add it to new arraylist
		for(int i=0; i <RAs.size(); i++){
			BigDecimal value = new BigDecimal("-1.0");
			if(RAs.get(i).compareTo(BigDecimal.ZERO)<0){
				PositiveRAs.add(RAs.get(i).multiply(value));
			}else{
				PositiveRAs.add(RAs.get(i));
			}
		}
		qe.con.close();
		/*
		for(int i=0; i <PositiveRAs.size(); i++){
			System.out.println("hi " + PositiveRAs.get(i));
		}
		*/		
		return PositiveRAs;	
	}
	
	
	public Map<Integer,List<BigDecimal>> getCalcs() throws SQLException{
		
		Scenario sc = new Scenario();
		
		Map<Integer,List<BigDecimal>>  data = new HashMap<Integer,List<BigDecimal>>() ;
		List<Integer> CIDs = sc.getCIDS();
		Map<Integer,BigDecimal> means = new HashMap<Integer,BigDecimal>();
		Map<Integer,List<BigDecimal>> calculations = new HashMap<Integer,List<BigDecimal>>();
		List<BigDecimal> val2 = new ArrayList<BigDecimal>();
		BigDecimal one = new BigDecimal(1);
		BigDecimal avg = new BigDecimal(0);
		BigDecimal sum = new BigDecimal(0);
		BigDecimal count = new BigDecimal(0);
		BigDecimal temp = new BigDecimal(0);
		int key = 0;
		
		//System.out.println("Calculating...");
		//adding all non empty row to the data Map
		//map that contains <CID, RA....>
		for(int i = 0; i < CIDs.size(); i++){
				if(sc.getRAs(CIDs.get(i)).isEmpty()){
					
				}else{
					data.put(CIDs.get(i),sc.getRAs(CIDs.get(i)));
				}
				
				
			//System.out.println(i);
		}
		
		
		//get averages of each CID and put them in averages Map
		//<CID,Mean>
		for(Entry<Integer,List<BigDecimal>> e : data.entrySet()) {
			
			List<BigDecimal> value = e.getValue();
			 key = e.getKey();
			
			for(BigDecimal ent: value){
				sum = sum.add(ent);
				count = count.add(new BigDecimal(1));
				//System.out.println("Count: " + count);
				//System.out.println("Sum: " +sum);
			}
			
			
			avg = avg.add((sum.divide(count,2, BigDecimal.ROUND_HALF_UP)));
			//System.out.println("Avg: " +avg);
			means.put(key, avg);
			avg = new BigDecimal(0);
			count = new BigDecimal(0);
		    sum = new BigDecimal(0);
			
			//System.out.println("Key: " + e.getKey() + ", Value: " + e.getValue());
		}
		
		/* 
		for(Entry<Integer,BigDecimal> e :average.entrySet()){
			System.out.println("Key: " + e.getKey() + ", Value: " + e.getValue());
		}
		*/
		BigDecimal mean = new BigDecimal(0);
		//do stuff here 
		for(Entry<Integer,BigDecimal> entry : means.entrySet()){
			key = entry.getKey();
			
			mean = entry.getValue();
			val2 = data.get(entry.getKey());
			
			//System.out.println("This: " +val2);

			//don't delete works! storing (xi - mean)^2 properly
			for(int i = 0; i< val2.size(); i++){
				temp = val2.get(i).subtract(mean);
				temp = temp.multiply(temp);

				val2.set(i, temp);					
			}
			
						
			int size = val2.size();
			BigDecimal size2 = new BigDecimal(String.valueOf(size));
			BigDecimal minus = new BigDecimal("1");
			size2 = size2.subtract(minus);
			//System.out.println(size2);
			double sqroot = 0.0;
			
			for(int j = 0; j < size -1; j++){
				List<BigDecimal> calcs = new ArrayList<BigDecimal>();
				temp = temp.add(val2.get(j));
				temp = temp.divide(size2, 6, BigDecimal.ROUND_HALF_UP);
			    sqroot= temp.doubleValue();
				
				sqroot = Math.sqrt(sqroot);
				temp = BigDecimal.valueOf(sqroot);
				calcs.add(size2.add(one));
				calcs.add(mean);
				calcs.add(temp);
				//store num of transactions, mean and standard deviation into calculations map
				calculations.put(key, calcs);			
			}
			
			//System.out.println(temp);		
			//System.out.println(size2);
			//System.out.println(val2);
			//System.out.println("\n");
			//System.out.println(sum2);
			//System.out.println( "Key: "+ key+ " Values: " + val2 + ", Mean: " + mean);
			
		}
		
		/*
		System.out.println(calculations.size());
		
		for(Entry<Integer,List<BigDecimal>> e : calculations.entrySet()){
			System.out.println("Key: "+ e.getKey() + ", Values: "+ e.getValue());
		}
		*/
		
		return calculations;
		
	}
	
	
	public Map<Integer,List<BigDecimal>> getScenario(Map<Integer,List<BigDecimal>> calculations) throws IOException{
		
		
		//not storing anything to sc yet... just want to look at transactions first
		Map<Integer,List<BigDecimal>> sc = new HashMap<Integer,List<BigDecimal>>();
		
		BigDecimal twoStds = new BigDecimal(0);
		BigDecimal tenStds = new BigDecimal(0);
		BigDecimal twoAvg = new BigDecimal(0);
		BigDecimal temp1 = new BigDecimal(0);
		BigDecimal temp2 = new BigDecimal(0);
		BigDecimal result = new BigDecimal(0);
		BigDecimal ten = new BigDecimal(10);
		int key = 0;
		int res = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		BigDecimal mean = new BigDecimal(0);
		List<BigDecimal> values = new ArrayList<BigDecimal>();
		
		
		
		//System.out.println(result); 
		
		BufferedWriter out = new BufferedWriter(new FileWriter("file2.txt"));
		BufferedWriter out2 = new BufferedWriter(new FileWriter("file3.txt"));
		
		for(Entry<Integer,List<BigDecimal>> e : calculations.entrySet()){
			key = e.getKey();
			values = e.getValue();
			
			List<BigDecimal> numTrans = values.subList(0, 1);
			List<BigDecimal> avg = values.subList(1, 2);
			List<BigDecimal> avg2 = new ArrayList<BigDecimal>();
			List<BigDecimal> stds = values.subList(2, 3);
			List<BigDecimal> stds2 = new ArrayList<BigDecimal>();
			List<BigDecimal> stds10 = new ArrayList<BigDecimal>();
			
			//200% average value, 2 stds, 10 stds
			for(i = 0; i < avg.size(); i++){
				twoAvg = avg.get(i).add(avg.get(i));
				avg2.add(twoAvg);
				twoStds = stds.get(i).add(stds.get(i));
				stds2.add(twoStds);
				tenStds = stds.get(i).multiply(ten);
				stds10.add(tenStds);
				temp1 = avg.get(i).add(twoStds);
				temp2 = tenStds.subtract(avg.get(i));
				BigDecimal range = temp2.subtract(temp1); 
				result = temp1.add(range.multiply(new BigDecimal(Math.random())));
				res = result.subtract(avg.get(i)).compareTo(twoAvg);
			}
			
			if(res == 1){
				out.write(result.setScale(2, RoundingMode.CEILING)+"\n");
				out2.write(numTrans.toString()+"\n");
			}else{
				System.out.println("Don't include");
			}
			
			
			System.out.println("AVG+2stds: "+ temp1.setScale(2, RoundingMode.CEILING) + " 10stds-avg: " +temp2.setScale(2, RoundingMode.CEILING));
			System.out.println("Suggested amount: " + result.setScale(2, RoundingMode.CEILING));
			System.out.println("\n");
			//System.out.println(twoAvg);
			//System.out.println("Number of trans: "+ numTrans +" AVG: "+ avg + " AGV2: "+avg2 + "STDS2: "+ stds2 + "STDS10: " + stds10) ;
		}
		out.close();
		out2.close();
		
		return sc;
		
	}
	
	public static void main(String[] args) throws SQLException, IOException{
		Scenario sc = new Scenario();
		
		//sc.getCalcs();
		sc.getScenario(sc.getCalcs());
		
	}
	
}