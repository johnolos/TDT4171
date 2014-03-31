package assignment4;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class DecisionThree {

	Random rand;
	boolean random;

	public DecisionThree(boolean random) {
		rand = new Random();
		this.random = random;
	}

	public Node DecisionThreeLearning(ArrayList<ArrayList<Integer>> examples,
			ArrayList<Integer> attributes,
			ArrayList<ArrayList<Integer>> parent_examples) {
		if (examples.isEmpty())
			return new Node(PluralityValue(parent_examples));
		else if (SameClassification(examples))
			return new Node(getSameClassification(examples));
		else if (attributes.isEmpty())
			return new Node(PluralityValue(examples));
		int best;
		if (random)
			best = attributes.get(rand.nextInt(attributes.size()));
		else
			best = Importance(examples, attributes);
		ArrayList<Integer> copy = new ArrayList<Integer>();
		for (Integer i : attributes) {
			if (i != best)
				copy.add(new Integer(i));
		}
		Node tree = new Node(best);
		for (int vk = 1; vk <= 2; vk++) {
			ArrayList<ArrayList<Integer>> exs = new ArrayList<ArrayList<Integer>>();
			for (int i = 0; i < examples.size(); i++) {
				if (examples.get(i).get(best) == vk) {
					exs.add(examples.get(i));
				}
			}
			Node subtree = DecisionThreeLearning(exs, copy, examples);
			subtree.setBranch(vk);
			tree.addChild(subtree);
		}
		return tree;
	}

	/**
	 * Returns int value of the most frequently classification. Classification
	 * is interpreted as last entry in the inner list. or random value in case
	 * it is a tie.
	 * 
	 * @param examples
	 * @return
	 */
	public int PluralityValue(ArrayList<ArrayList<Integer>> examples) {
		int a = 0, b = 0;
		int size = examples.size();
		int numberOfEntries = examples.get(0).size();
		for (int i = 0; i < size; i++) {
			if (examples.get(i).get(numberOfEntries - 1) == 1)
				a++;
			else
				b++;
		}
		// Returns randomly between 1 or 2 if the number of classification is
		// same.
		if (a == b)
			return (rand.nextInt(2) + 1); // Returns 1 or 2.
		return (a > b ? 1 : 2);
	}

	/**
	 * Returns true if all examples given are of same classification.
	 * Classification is interpreted as last entry in list.
	 * 
	 * @param examples
	 *            Examples
	 * @return
	 */
	public boolean SameClassification(ArrayList<ArrayList<Integer>> examples) {
		int size = examples.size();
		int numberOfEntries = examples.get(0).size();
		int numberOfClassification = 0;
		for (int i = 0; i < size; i++)
			if (examples.get(i).get(numberOfEntries - 1) == 1)
				++numberOfClassification;
		return (numberOfClassification == 0 || numberOfClassification == size);
	}

	public int getSameClassification(ArrayList<ArrayList<Integer>> examples) {
		int numberOfEntries = examples.get(0).size();
		return examples.get(0).get(numberOfEntries - 1);
	}

	public int Importance(ArrayList<ArrayList<Integer>> examples,
			ArrayList<Integer> attributes) {
		int numberOfAttributes = examples.get(0).size() - 1;
		int attrWithBestGain = 0;
		double bestGain = Gain(examples, attrWithBestGain);
		double currentGain = 0.0;
		for (int i = 1; i < numberOfAttributes; i++) {
			currentGain = Gain(examples, i);
			if (currentGain > bestGain) {
				bestGain = currentGain;
				attrWithBestGain = i;
			}
		}
		return attrWithBestGain;
	}

	public double B(double q) {
		double value = -(q * (Math.log(q) / Math.log(2)) + (1 - q)
				* (Math.log(1 - q) / Math.log(2)));

		return (Double.isNaN(value) ? 0 : value);
	}

	private double remainderSum(ArrayList<ArrayList<Integer>> examples,
			int attr, int d) {
		double pk = 0, nk = 0, p = 0, n = 0;
		for (ArrayList<Integer> example : examples) {
			if (example.get(7) == 1) {
				p++;
				if (example.get(attr) == d)
					pk++;
			} else {
				n++;
				if (example.get(attr) == d)
					nk++;
			}
		}
		return ((pk + nk) / (p + n)) * B(pk / (pk + nk));
	}

	public double Remainder(ArrayList<ArrayList<Integer>> examples, int attr) {
		return remainderSum(examples, attr, 1)
				+ remainderSum(examples, attr, 2);
	}

	public double Gain(ArrayList<ArrayList<Integer>> examples, int attr) {
		double p = 0, n = 0;
		for (ArrayList<Integer> example : examples) {
			if (example.get(7) == 1)
				++p;
			else
				++n;
		}
		return B(p / (p + n)) - Remainder(examples, attr);
	}

	private static ArrayList<Integer> getAttributes(
			ArrayList<ArrayList<Integer>> examples) {
		ArrayList<Integer> attr = new ArrayList<Integer>();
		for (int i = 0; i < examples.get(0).size() - 1; i++) {
			attr.add(i);
		}
		return attr;
	}

	public static void main(String[] args) {
		try {
			int count = 0;
			do {
				count = 0;

				DecisionThree decisionThree = new DecisionThree(false);
				ArrayList<ArrayList<Integer>> examples = TestData
						.ReadTestData("files/training.txt");
				ArrayList<Integer> attributes = getAttributes(examples);
				Node node = decisionThree.DecisionThreeLearning(examples,
						attributes, null);
				node.printTree(0);

				ArrayList<ArrayList<Integer>> validation = TestData
						.ReadTestData("files/test.txt");
				for (ArrayList<Integer> transaction : validation) {
					if (node.classify(transaction)) {
						count++;
					}
				}

				DecimalFormat numberFormat = new DecimalFormat("#.00");
				System.out.println(count);
				System.out.println("Percentage: "
						+ numberFormat.format(((double) count / validation
								.size()) * 100) + "%");
			} while (count != 28);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}