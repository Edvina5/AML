package WekaWorkbench;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.clusterers.SelfOrganizingMap;
import weka.core.Instances;

public class SOM {
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader breader = null;
		breader = new BufferedReader(new FileReader("vote.arff"));
		
		Instances train = new Instances (breader);
		train.setClassIndex(train.numAttributes() -1);
		
		breader.close();
		
		SelfOrganizingMap som = new SelfOrganizingMap();
		som.buildClusterer(train);
		
		
	}

}
