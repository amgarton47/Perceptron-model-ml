package ml.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ml.DataSet;
import ml.Example;

public class PerceptronClassifier implements Classifier {
	private int numIterations = 10;
	private ArrayList<Double> w;
	private double b = 0;

	public PerceptronClassifier() {
	}

	@Override
	public void train(DataSet data) {
		// initialize weight vectors to all zeroes
		int numFeatures = data.getAllFeatureIndices().size();
		w = new ArrayList<Double>(Collections.nCopies(numFeatures, 0.0));

		for (int i = 0; i < numIterations; i++) {
			// randomly shuffle the data
			shuffle(data);

			for (Example example : data.getData()) {
				double prediction = getPrediction(example);
				double y = example.getLabel();

				if (prediction * y <= 0) { // if we mis-classify example based on weights w
					for (int j = 0; j < w.size(); j++) {
						w.set(j, w.get(j) + y * example.getFeature(j));
					}

					b += y;
				}
			}
		}
	}

	private void shuffle(DataSet data) {
		// randomly shuffle the data
		for (int l = data.getData().size() - 1; l > 0; l--) {
			Random r = new Random();
			int randomIndex = r.nextInt(l + 1);
			Example temp = data.getData().get(l);
			data.getData().set(l, data.getData().get(randomIndex));
			data.getData().set(randomIndex, temp);
		}
	}

	private double getPrediction(Example example) {
		double prediction = b;
		for (int i = 0; i < w.size(); i++) {
			prediction += w.get(i) * example.getFeature(i);
		}
		return prediction;
	}

	@Override
	public double classify(Example example) {
		double prediction = b;

		for (int i = 0; i < w.size(); i++) {
			prediction += w.get(i) * example.getFeature(i);
		}
		return prediction >= 0 ? 1.0 : -1.0;
	}

	public void setIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	@Override
	public String toString() {
		return "";
	}
}
