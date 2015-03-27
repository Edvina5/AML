package Util;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.clusterers.SelfOrganizingMap;
import weka.core.Instance;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import DB.DBConnector;
import DB.QueryExecutor;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class Sets {
	
	
	private String table_name = "table_03";
	
	public void createTraining(){
		
		int cid = 0;
		BigDecimal sf = null;
		BigDecimal rf = null;
		BigDecimal sa = null;
		BigDecimal ra = null;
		String date = "";
		String sus = "";
		
		
		//get 58 (10%) of both suspicious and not suspicious transactions for training set
		String query = "SELECT * FROM "+table_name+" WHERE Suspicious = 'Y' LIMIT 150 ";
		String query2 = "SELECT * FROM "+table_name+" WHERE Suspicious = 'N' LIMIT 150";
	
		QueryExecutor qe = new QueryExecutor();
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement prs = null;
		
	
		try{
			rs = qe.executeQuery(query);
			rs2 = qe.executeQuery(query2);
			
			while(rs.next()){
				cid = rs.getInt("CID");
				sf = rs.getBigDecimal("SF");
				rf = rs.getBigDecimal("RF");
				sa = rs.getBigDecimal("SA");
				ra = rs.getBigDecimal("RA");
				date = rs.getString("Date");
				sus = rs.getString("Suspicious");
				
				
				String query3 = "INSERT INTO training VALUES('"+cid+"','"+sf+"', '"+rf+"', '"+sa+"', '"+ra+"', '"+date+"', '"+sus+"')";
				prs = (PreparedStatement) DBConnector.connect().prepareStatement(query3);
				prs.execute();
				DBConnector.closeConnection();
			}
			
			while(rs2.next()){
				cid = rs2.getInt("CID");
				sf = rs2.getBigDecimal("SF");
				rf = rs2.getBigDecimal("RF");
				sa = rs2.getBigDecimal("SA");
				ra = rs2.getBigDecimal("RA");
				date = rs2.getString("Date");
				sus = rs2.getString("Suspicious");
				
				
				String query3 = "INSERT INTO training VALUES('"+cid+"','"+sf+"', '"+rf+"', '"+sa+"', '"+ra+"', '"+date+"', '"+sus+"')";
				prs = (PreparedStatement) DBConnector.connect().prepareStatement(query3);
				prs.execute();
				DBConnector.closeConnection();
			}
	
			System.out.println("Done");
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		
	}
	
	
	public void createTesting(){
		
		int cid = 0;
		BigDecimal sf = null;
		BigDecimal rf = null;
		BigDecimal sa = null;
		BigDecimal ra = null;
		String date = "";
		String sus = "";
		
		String query = "SELECT * FROM "+ table_name + "";
		QueryExecutor qe = new QueryExecutor();
		ResultSet rs = null;
		PreparedStatement prs = null;
		
		try{
			
			rs = qe.executeQuery(query);
			
			while(rs.next()){
				cid = rs.getInt("CID");
				sf = rs.getBigDecimal("SF");
				rf = rs.getBigDecimal("RF");
				sa = rs.getBigDecimal("SA");
				ra = rs.getBigDecimal("RA");
				date = rs.getString("Date");
				sus = rs.getString("Suspicious");
				
				String query3 = "INSERT INTO testing VALUES('"+cid+"','"+sf+"', '"+rf+"', '"+sa+"', '"+ra+"', '"+date+"', '"+sus+"')";
				prs = (PreparedStatement) DBConnector.connect().prepareStatement(query3);
				prs.execute();
				DBConnector.closeConnection();
				
				System.out.println("Importing...");
			
			}
			System.out.println("Done");
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		
	}
	
	
	public static ArrayList<String> getNaivePrediction() throws IOException{
		
		ArrayList<String> naivePreds = new ArrayList<String>();
		
		String query1 = "SELECT SF,RF,SA,RA,Suspicious FROM training;";
		String query2 = "SELECT SF,RF,SA,RA,Suspicious FROM testing";
		
		String actual = "";
		String predicted = "";
		int count = 0;
		int j = 0;
		int fp = 0;
		int fn = 0;
		
		BufferedWriter out = new BufferedWriter(new FileWriter("class.txt"));
		
		try{
			InstanceQuery query = new InstanceQuery();
			query.setDatabaseURL("jdbc:mysql://localhost:3306/userlogindb");
		    query.setUsername("root");
		    query.setPassword("");
		    query.setQuery(query1);
		    
		    //training instance
			Instances training = query.retrieveInstances();
			
			training.setClassIndex(training.numAttributes() -1);
			
			InstanceQuery query3 = new InstanceQuery();
			query3.setDatabaseURL("jdbc:mysql://localhost:3306/userlogindb");
		    query3.setUsername("root");
		    query3.setPassword("");
			query3.setQuery(query2);
			//testing instance
			Instances testing = query3.retrieveInstances();
			
			
			
			testing.setClassIndex(testing.numAttributes() -1);
		    //create Naive Bayes Classifier
		    NaiveBayes nb = new NaiveBayes(); 
		    //create model
		   
		    nb.buildClassifier(training);
		    Instance current;
		   
		    //train classifier one by one
		    for( j = 0; j < training.size(); j++){
		    	current = training.instance(j);
		    	nb.updateClassifier(current);
		    }
		   
		    Evaluation eval = new Evaluation(training);
		    
		    //cross validate training set
		    eval.crossValidateModel(nb, training, 10, new Random(1));
		    
		    for( j = 0; j < testing.numInstances() ; j ++){
			  
				double label = nb.classifyInstance(testing.instance(j));
			    
				
				//System.out.print("Actual: "+testing.instance(j).toString(testing.classIndex()) + " - ");
				//System.out.print("Predicted: "+testing.classAttribute().value((int) label) +" "+ j + "\n");
				
				actual = testing.instance(j).toString(testing.classIndex());
				predicted = testing.classAttribute().value((int) label);
				
				//remove actuall label
				String part = testing.instance(j).toString().substring(0, testing.instance(j).toString().length()-2);
				part = part.concat(","+testing.classAttribute().value((int) label));
				
				//add transactions and predicted labels to arraylist
				naivePreds.add(part);
				
				//System.out.println(part);
				
				if(actual.equals(predicted)){
					count++;
				}
				
				//calculate false positives
				if(actual.equals("N") && predicted.equals("Y")){
					fp++;
				}
				
				//calculate false negatives
				if(actual.equals("Y") && predicted.equals("N")){
					fn++;
				}
	        	
		  }
	        
	      out.close();
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		BigDecimal cou = new java.math.BigDecimal(String.valueOf(count));
		BigDecimal jj = new java.math.BigDecimal(String.valueOf(j));
		BigDecimal fp2 = new java.math.BigDecimal(String.valueOf(fp));
		BigDecimal fn2 = new java.math.BigDecimal(String.valueOf(fn));
		BigDecimal fpRate = new BigDecimal(0);
		BigDecimal fnRate = new BigDecimal(0);
		BigDecimal accuracy = new BigDecimal(0);
		BigDecimal hundred = new BigDecimal(100);
		
		//calculate the accuracy
		accuracy = (cou.divide(jj, 6, RoundingMode.HALF_UP).multiply(hundred));
		
		//calculate false positive rate
		fpRate = (fp2.divide(jj, 6, RoundingMode.HALF_UP).multiply(hundred));
		
		//calculate false negative rate 
		fnRate = (fn2.divide(jj, 6, RoundingMode.HALF_UP).multiply(hundred));
		
		//System.out.println(cou);
		//System.out.println(jj);
		
		//System.out.println("Naive Bayes accuracy: "+accuracy+"%");
		//System.out.println("False positive rate: " + fpRate+"%");
		//System.out.println("False negatives: " + fnRate+"%");
		//System.out.println(accuracy);
		
		
		if(naivePreds.isEmpty()){
			System.out.println("empty");
		}
		
		
		
		
		
		return naivePreds;
		
	}
	
	
	public static ArrayList<String> getSOMPredictions(){
		
		int count = 0;
		int num = 0;
		int fp = 0;
		int fn = 0;
	
		ArrayList<String> somPreds = new ArrayList<String>();
		
		ArrayList<String> predicted = new ArrayList<String>();
		ArrayList<String> actual = new ArrayList<String>();
		
		String queries = "SELECT SF,RF,SA,RA FROM table_01;";
		
		String query2 = "SELECT Suspicious from table_03";
		
		QueryExecutor qe = new QueryExecutor();
		ResultSet rs = null;
		try{	
			
			String sus = "";
			
			rs = qe.executeQuery(query2);
			
			
			InstanceQuery query = new InstanceQuery();
			query.setDatabaseURL("jdbc:mysql://localhost:3306/userlogindb");
		    query.setUsername("root");
		    query.setPassword("");
		    query.setQuery(queries);
			Instances data = query.retrieveInstances();
			
		
		double learningrate = 0.5;
		
		
		String isIt = "";
		
		Instances train = new Instances (data);
		
			
		SelfOrganizingMap som = new SelfOrganizingMap();
		som.setHeight(10);
		som.setConvergenceEpochs(1000);
		som.setOrderingEpochs(2000);
		som.setLearningRate(learningrate);
		som.buildClusterer(train);	
		
		BufferedWriter out = new BufferedWriter(new FileWriter("som.txt"));
		BufferedWriter out2 = new BufferedWriter(new FileWriter("som2.txt"));
		for(int i = 0; i < train.numInstances(); i++){
			int label = som.clusterInstance(train.instance(i));
			
			
			//System.out.println(train.instance(i)+ " is in the cluster " + label );
			//out.write(train.instance(i)+ " is in the cluster " + label + "\n");
			
			String part = train.instance(i).toString().substring(0, train.instance(i).toString().length() );
			
			
			if(label == 3){
				isIt = "Y";
				predicted.add(isIt);
				part = part.concat(" "+isIt);
				//out2.write(isIt+"\n");
			}else{
				isIt = "N";
				predicted.add(isIt);
				part = part.concat(" "+isIt);
				//out2.write(isIt+"\n");
			}
			
			somPreds.add(part);
			
			while(rs.next()){
				sus = rs.getString("Suspicious");
				actual.add(sus);
			}
			
			 num = actual.size();
		
		}
		out.close();
		out2.close();
		
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		for(int i = 0; i < actual.size(); i++){
			if(predicted.get(i).equals(actual.get(i))){
				count++;
			}
			
			//calculate false positives
			if(actual.get(i).equals("N") && predicted.get(i).equals("Y")){
				fp++;
			}
			
			//calculate false negatives
			if(actual.get(i).equals("Y") && predicted.get(i).equals("N")){
				fn++;
			}
			
		}
		
		BigDecimal cou = new java.math.BigDecimal(String.valueOf(count));
		BigDecimal jj = new java.math.BigDecimal(String.valueOf(num));
		BigDecimal accuracy = new BigDecimal(0);
		BigDecimal hundred = new BigDecimal(100);
		BigDecimal fp2 = new java.math.BigDecimal(String.valueOf(fp));
		BigDecimal fn2 = new java.math.BigDecimal(String.valueOf(fn));
		BigDecimal fpRate = new BigDecimal(0);
		BigDecimal fnRate = new BigDecimal(0);
		
		//calculate the accuracy
		accuracy = (cou.divide(jj, 6,  BigDecimal.ROUND_HALF_UP).multiply(hundred));
		
		//calculate false positive rate
		fpRate = (fp2.divide(jj, 6, RoundingMode.HALF_UP).multiply(hundred));
				
		//calculate false negative rate 
		fnRate = (fn2.divide(jj, 6, RoundingMode.HALF_UP).multiply(hundred));
		
		//System.out.println(cou);
		//System.out.println(jj);
		
		//System.out.println("Accuracy for SOM: "+ accuracy+"%");
		//System.out.println("False positive rate: " + fpRate+"%");
		//System.out.println("False negatives: " + fnRate+"%");
		
		
	
		return somPreds;
	}
	
	
	public static void main(String[] args) throws IOException{
		Sets set = new Sets();
		
		//set.createTraining();
		//set.createTesting();
		set.getNaivePrediction();
		//set.getSOMPredictions();
	}
	
	
}
