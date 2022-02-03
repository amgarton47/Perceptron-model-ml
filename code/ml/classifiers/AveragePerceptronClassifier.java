package ml.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ml.DataSet;
import ml.Example;

public class AveragePerceptronClassifier implements Classifier {
	private int numIterations = 10;
	private ArrayList<Double> w, u;
	private double b = 0, b2 = 0;
	private int updated = 0, total = 0;
	private DataSet data;

	public AveragePerceptronClassifier() {
	}

	@Override
	public void train(DataSet data) {
		this.data = data;
		// initialize weight vectors to all zeroes
		int numFeatures = data.getAllFeatureIndices().size();
		w = new ArrayList<Double>(Collections.nCopies(numFeatures, 0.0));
		u = new ArrayList<Double>(Collections.nCopies(numFeatures, 0.0));

		for (int i = 0; i < numIterations; i++) {
			// randomly shuffle the data
			shuffle(data);

			for (Example example : data.getData()) {
				double prediction = getPrediction(example);

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
		double prediction = b2;

		for (int i = 0; i < u.size(); i++) {
			prediction += u.get(i) * example.getFeature(i);
		}
		return prediction >= 0 ? 1.0 : -1.0;
	}

	public void setIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	@Override
	public String toString() {
		String str = "";

		for (int i = 0; i < data.getAllFeatureIndices().size(); i++) {
			str += data.getAllFeatureIndices().toArray()[i] + ":" + w.get(i) + " ";
		}
		return str + b;
	}
}
