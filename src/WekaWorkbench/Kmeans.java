package WekaWorkbench;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

import DB.DBConnector;
import DB.QueryExecutor;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class Kmeans {
	
	public static void main(String[] args) throws Exception{
		
		
		String queries = "SELECT CID,SF,RF,SA,RA,Date FROM table_02;";
		
		
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
			
			SimpleKMeans kmeans = new SimpleKMeans();
			kmeans.setNumClusters(2);
			kmeans.buildClusterer(data);
			
		
			BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));
			
			//get cluster membership for each instance
			for(int j = 0; j < data.numInstances(); j++){
				
				int label = kmeans.clusterInstance(data.instance(j));
				System.out.println(data.instance(j)+ " is in the cluster " + label );
				out.write(data.instance(j) + "," + label + "\n");
				
			}
			out.close();
			
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		
		
	}

}
