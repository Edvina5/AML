package WekaWorkbench;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import DB.QueryExecutor;
import com.mysql.jdbc.ResultSet;
import weka.clusterers.SelfOrganizingMap;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class SOM {
	
	public static void main(String[] args) throws Exception{
		
		int count = 0;
		int num = 0;
	
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
		System.out.println(som.numberOfClusters());
		System.out.println(som.getHeight());
		System.out.println(som.getCalcStats());
		
		
		BufferedWriter out = new BufferedWriter(new FileWriter("som.txt"));
		BufferedWriter out2 = new BufferedWriter(new FileWriter("som2.txt"));
		for(int i = 0; i < train.numInstances(); i++){
			int label = som.clusterInstance(train.instance(i));
			//System.out.println(train.instance(i)+ " is in the cluster " + label );
			out.write(train.instance(i)+ " is in the cluster " + label + "\n");
			
			
			if(label == 3){
				isIt = "Y";
				predicted.add(isIt);
				out2.write(isIt+"\n");
			}else{
				isIt = "N";
				predicted.add(isIt);
				out2.write(isIt+"\n");
			}
			
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
			
		}
		
		System.out.println("Num: "+num);
		
		System.out.println("Count: "+count);
		int accuracy = (int) ((double)(count/num)*100);
		System.out.println("Accuracy: "+ accuracy);
		
	}

}
