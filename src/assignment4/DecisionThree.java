package assignment4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class DecisionThree {
	
	
	Random random;
	
	
	public DecisionThree() {
		random = new Random();
	}
	
	
	
	public Node DecisionThreeLearning(ArrayList<ArrayList<Integer>> examples, ArrayList<Integer> attributes, ArrayList<ArrayList<Integer>> parent_examples) {
		if(examples.isEmpty())
			return new Node(PluralityValue(parent_examples));
		if(SameClassification(examples))
			return new Node(getSameClassification(examples));
		if(attributes.isEmpty())
			return new Node(PluralityValue(examples));
		return null;
	}

	/**
	 * Returns int value of the most frequently classification.
	 * Classification is interpreted as last entry in the inner list.
	 * or random value in case it is a tie.
	 * @param examples
	 * @return
	 */
	public int PluralityValue(ArrayList<ArrayList<Integer>> examples) {
		int a = 0, b = 0;
		int size = examples.size();
		int numberOfEntries = examples.get(0).size();
		for(int i = 0; i < size; i++) {
			if(examples.get(i).get(numberOfEntries - 1) == 1)
				a++;
			else
				b++;
		}
		// Returns randomly between 1 or 2 if the number of classification is same.
		if(a == b)
			return (random.nextInt(2) + 1); // Returns 1 or 2.
		return (a > b ? 1 : 2);
	}
	
	/**
	 * Returns true if all examples given are of same classification.
	 * Classification is interpreted as last entry in list.
	 * @param examples Examples
	 * @return
	 */
	public boolean SameClassification(ArrayList<ArrayList<Integer>> examples) {
		int size = examples.size();
		int numberOfEntries = examples.get(0).size();
		int numberOfClassification = 0;
		for(int i = 0; i < size; i++)
			if(examples.get(i).get(numberOfEntries - 1) == 1) ++numberOfClassification;
		return (numberOfClassification == 0 || numberOfClassification == size);
	}
	
	
	private int getSameClassification(ArrayList<ArrayList<Integer>> examples) {
		int numberOfEntries = examples.get(0).size();
		return examples.get(0).get(numberOfEntries - 1);
	}
	
	public int Importance(int a, ArrayList<ArrayList<Integer>> examples) {
		// Shall calculate information gain pr. attribute and return index of attribute with best gain.
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		try {
			DecisionThree decisionThree = new DecisionThree();
			ArrayList<ArrayList<Integer>> examples = TestData.ReadTestData("files/myFile.txt");
			System.out.println(decisionThree.PluralityValue(examples));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}