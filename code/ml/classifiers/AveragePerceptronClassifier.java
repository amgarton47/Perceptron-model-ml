package ml.classifiers;

import java.util.ArrayList;
import java.util.Collections;

import ml.DataSet;
import ml.Example;

public class AveragePerceptronClassifier extends PerceptronClassifier {
	private ArrayList<Double> u;
	private double b2 = 0;
	private int updated = 0, total = 0;

	public AveragePerceptronClassifier() {

	}

	public void train(DataSet data) {
		// initialize weight vectors to all zeroes
		this.data = data;
		int numFeatures = data.getAllFeatureIndices().size();
		w = new ArrayList<Double>(Collections.nCopies(numFeatures, 0.0));
		u = new ArrayList<Double>(Collections.nCopies(numFeatures, 0.0));

		for (int i = 0; i < numIterations; i++) {
			// randomly shuffle the data
			Collections.shuffle(data.getData());
			for (Example example : data.getData()) {
				double prediction = super.getPrediction(example);

				if (prediction * example.getLabel() <= 0) { // if we mis-classify example based on weights w
					for (int j = 0; j < u.size(); j++) {
						u.set(j, u.get(j) + updated * w.get(j));
					}

					b2 += b * updated;

					// update all the perceptron weights
					for (int k = 0; k < w.size(); k++) {
						w.set(k, w.get(k) + example.getLabel() * example.getFeature(k));
					}

					b += example.getLabel();
					updated = 0;
				}

				updated++;
				total++;
			}
		}

		// do one last weighted update here of the u and b2 weights based on the final
		// weights
		// divide all of the aggregate weights by the total number of examples

		for (int i = 0; i < u.size(); i++) {
			u.set(i, u.get(i) / total);
		}

		b2 /= total;
		b = b2;
		w = u;
	}

//	private double getPrediction(Example example) {
//		double prediction = b;
//		for (int i = 0; i < w.size(); i++) {
//			prediction += w.get(i) * example.getFeature(i);
//		}
//		return prediction;
//	}
}

//public class AveragePerceptronClassifier implements Classifier {
//	private int numIterations = 10;
//	private ArrayList<Double> w, u;
//	private double b = 0, b2 = 0;
//	private int updated = 0, total = 0;
//	private DataSet data;
//
//	public AveragePerceptronClassifier() {
//	}
//
//	@Override
//	public void train(DataSet data) {
//		this.data = data;
//		// initialize weight vectors to all zeroes
//		int numFeatures = data.getAllFeatureIndices().size();
//		w = new ArrayList<Double>(Collections.nCopies(numFeatures, 0.0));
//		u = new ArrayList<Double>(Collections.nCopies(numFeatures, 0.0));
//
//		for (int i = 0; i < numIterations; i++) {
//			// randomly shuffle the data
//			Collections.shuffle(data.getData());
//			for (Example example : data.getData()) {
//				double prediction = getPrediction(example);
//
//				if (prediction * example.getLabel() <= 0) { // if we mis-classify example based on weights w
//					for (int j = 0; j < u.size(); j++) {
//						u.set(j, u.get(j) + updated * w.get(j));
//					}
//
//					b2 += b * updated;
//
//					// update all the perceptron weights
//					for (int k = 0; k < w.size(); k++) {
//						w.set(k, w.get(k) + example.getLabel() * example.getFeature(k));
//					}
//
//					b += example.getLabel();
//					updated = 0;
//				}
//
//				updated++;
//				total++;
//			}
//		}
//
//		// do one last weighted update here of the u and b2 weights based on the final
//		// weights
//		// divide all of the aggregate weights by the total number of examples
//
//		for (int i = 0; i < u.size(); i++) {
//			u.set(i, u.get(i) / total);
//		}
//
//		b2 /= total;
//	}
//
//	private double getPrediction(Example example) {
//		double prediction = b;
//		for (int i = 0; i < w.size(); i++) {
//			prediction += w.get(i) * example.getFeature(i);
//		}
//		return prediction;
//	}
//
//	@Override
//	public double classify(Example example) {
//		double prediction = b2;
//
//		for (int i = 0; i < u.size(); i++) {
//			prediction += u.get(i) * example.getFeature(i);
//		}
//		return prediction >= 0 ? 1.0 : -1.0;
//	}
//
//	public void setIterations(int numIterations) {
//		this.numIterations = numIterations;
//	}
//
//	@Override
//	public String toString() {
//		String str = "";
//
//		for (int i = 0; i < data.getAllFeatureIndices().size(); i++) {
//			str += data.getAllFeatureIndices().toArray()[i] + ":" + w.get(i) + " ";
//		}
//		return str + b;
//	}
//}
