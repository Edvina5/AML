package WekaWorkbench;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.clusterers.SelfOrganizingMap;
import weka.core.Instances;

public class NaiveB {
	
	
public static void main(String[] args) throws Exception {
		
		BufferedReader breader = null;
		breader = new BufferedReader(new FileReader("vote.arff"));
		
		Instances train = new Instances (breader);
		train.setClassIndex(train.numAttributes() -1);
		
		breader.close();
		
		
		
		NaiveBayes nb = new NaiveBayes(); 
		nb.buildClassifier(train);
		Evaluation eval = new Evaluation(train);
		
		eval.crossValidateModel(nb, train, 10, new Random(1));
		
		System.out.println(eval.toSummaryString("\nResults\n=====\n", true));
		System.out.println(eval.fMeasure(1)+ " " + eval.precision(1)+ " " + eval.recall(1));
		
	
	}

}
