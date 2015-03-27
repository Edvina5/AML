package WekaWorkbench;

import java.io.BufferedWriter;
import java.io.FileWriter;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.experiment.InstanceQuery;

public class Kmeans {
	
	public static void main(String[] args) throws Exception{
		
		
		String queries = "SELECT SF,RF,SA,RA FROM table_01;";
		
		try{	
			
			
			InstanceQuery query = new InstanceQuery();
			query.setDatabaseURL("jdbc:mysql://localhost:3306/userlogindb");
		    query.setUsername("root");
		    query.setPassword("");
		    query.setQuery(queries);
			Instances data = query.retrieveInstances();
			
			SimpleKMeans kmeans = new SimpleKMeans();
			kmeans.setNumClusters(5);
			kmeans.buildClusterer(data);
			
		
			BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));
			BufferedWriter out2 = new BufferedWriter(new FileWriter("file2.txt"));
			//get cluster membership for each instance
			for(int j = 0; j < data.numInstances(); j++){
				
				String isIt = "";
				
				int label = kmeans.clusterInstance(data.instance(j));
				System.out.println(data.instance(j)+ " is in the cluster " + label );
				
				if(label == 4){
					isIt = "Y";
				}else{
					isIt = "N";
				}
				
				//out.write(data.instance(j)+ " " + label +"\n");
				out2.write(data.instance(j) + "\n");
				out.write( isIt +"\n");
				
			}
			out.close();
			out2.close();
			
			
		}catch(Exception ex){
			System.out.println(ex);
		}
		
		
		
		
		
	}

}
