package WekaWorkbench;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import DB.QueryExecutor;

import com.mysql.jdbc.ResultSet;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.clusterers.SelfOrganizingMap;
import weka.core.Instances;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class NaiveB {
	
	
public static void main(String[] args) throws Exception {
		
	
		String queries = "SELECT SF,RF,SA,RA,Suspicious FROM table_03;";
		QueryExecutor qe = new QueryExecutor();
		ResultSet rs = null;
		
		
		try{
			rs = qe.executeQuery(queries);
			
			InstanceQuery query = new InstanceQuery();
			query.setDatabaseURL("jdbc:mysql://localhost:3306/userlogindb");
		    query.setUsername("root");
		    query.setPassword("");
		    query.setQuery(queries);
			Instances data = query.retrieveInstances();
			
			
			//convert data to nominal
			NumericToNominal converter = new NumericToNominal();
			String[] options = new String[2];
			options[0]="-R";
	        options[1]="1-4";  //range of variables to make numeric
			
	        converter.setOptions(options);
	        converter.setInputFormat(data);
	        
			Instances newData = Filter.useFilter(data, converter);
			
			Instances train = new Instances(newData);
			train.setClassIndex(train.numAttributes() -1);  //set class label to be last collumn
			
			
			NaiveBayes nb = new NaiveBayes(); 
			nb.buildClassifier(train);
			Evaluation eval = new Evaluation(train);
			
			
			//need to create a test object
			//the test score is what we are interested in
			//eval.evaluateModel(nb, test);
			
			eval.crossValidateModel(nb, train, 10, new Random(1));
			
			System.out.println(eval.toSummaryString("\nResults\n=====\n", true));
			System.out.println("Error rate: "+eval.errorRate() + "\nfMeasure: " + eval.fMeasure(1)+ "\nPrecision: " + eval.precision(1)+ "\nRecall: " + eval.recall(1));
			
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		
	}

}
