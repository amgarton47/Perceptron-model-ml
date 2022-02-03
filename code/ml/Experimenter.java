package ml;

import java.util.ArrayList;

import javax.swing.JFrame;

import ml.classifiers.AveragePerceptronClassifier;
import ml.classifiers.Classifier;
import ml.classifiers.ClassifierTimer;
import ml.classifiers.PerceptronClassifier;
import ml.classifiers.RandomClassifier;

/**
 * CS158-PO - Machine Learning Assignment: 02
 * 
 * A class to experiment on the accuracy of a classification model as well as
 * its hyper-parameters.
 * 
 * @author Aidan Garton
 *
 */
public class Experimenter {

	// runs some passed-in number of trials given a classification model and a
	// fraction
	// for which to split the training and testing data
	public static double[] runXTrials(DataSet dataset, Classifier classifier, int numTrials, double splitFraction) {
		double trainCorrect = 0.0, testCorrect = 0.0, total1 = 0.0, total2 = 0.0;
		for (int i = 0; i < numTrials; i++) {
			DataSet[] splitData = dataset.split(splitFraction);
			classifier.train(splitData[0]);

			for (Example example : splitData[1].getData()) {
				double prediction = classifier.classify(example);

				if (prediction == example.getLabel()) {
					testCorrect++;
				}
				total1++;
			}

			for (Example example : splitData[0].getData()) {
				double prediction = classifier.classify(example);

				if (prediction == example.getLabel()) {
					trainCorrect++;
				}
				total2++;
			}
		}

		double trainAccuracy = (trainCorrect / total2) * 100;
		double testAccuracy = (testCorrect / total1) * 100;

		return new double[] { Math.floor(trainAccuracy * 10000) / 10000, Math.floor(testAccuracy * 10000) / 10000 };
	}

	// starter code for testing out our model and comparing it to a random
	// classifier
	public static void main(String[] args) {
		final int NUM_TRIALS = 100;
		String datasetName = "titanic-train";
		DataSet dataset = new DataSet("../assign3-starter/data/" + datasetName + ".csv");

		System.out.print("Random classification accuracy: ");
		RandomClassifier random = new RandomClassifier();
		System.out.println(Math.floor(runXTrials(dataset, random, NUM_TRIALS, 0.8)[1] * 10000) / 10000 + "%");

		PerceptronClassifier p1 = new PerceptronClassifier();
		AveragePerceptronClassifier p2 = new AveragePerceptronClassifier();
		
//		ClassifierTimer.timeClassifier(p1, dataset, 100);
//		ClassifierTimer.timeClassifier(p2, dataset, 100);
		
		double[] results1 = runXTrials(dataset, p1, NUM_TRIALS, 0.8);
		double[] results2 = runXTrials(dataset, p2, NUM_TRIALS, 0.8);
//
		System.out.println("AveragePerceptron: " + results2[1]);
		System.out.println("Perceptron: " + results1[1]);
		
		System.out.println(p1);
		System.out.println(p2);

//		ArrayList<Point> points = new ArrayList<Point>();
//
//		System.out.println("\nPERCEPTRON ACCURACIES (varying num train iterations)\n");
//		for (int iters = 0; iters <= 100; iters += 10) {
//			PerceptronClassifier p = new PerceptronClassifier();
//			p.setIterations(iters);
//
//			double[] results = runXTrials(dataset, p, NUM_TRIALS, 0.8);
//			System.out.println(
//					"PC: iterations: " + iters + " train accuracy: " + results[0] + " test accuracy: " + results[1]);
//
//			AveragePerceptronClassifier ap = new AveragePerceptronClassifier();
//			ap.setIterations(iters);
//
//			double[] resultss = runXTrials(dataset, ap, NUM_TRIALS, 0.8);
//			System.out.println(
//					"APC: iterations: " + iters + " train accuracy: " + resultss[0] + " test accuracy: " + results[1]);
//
////			points.add(new Point(iters, resultss[1]));
//			points.add(new Point(iters, results[1]));
//		}
//
//		// accuracy graph for varying # of train iterations
//		G g = new G(100, 100, points);
//		g.setXLabel("# train iterations");
//		g.setYLabel("accuracy");
//		g.graph(500, 500);
		
		
	}
}
