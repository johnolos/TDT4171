package assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestData {
	
	public static ArrayList<ArrayList<Integer>> ReadTestData(String fileLocation) throws FileNotFoundException, IOException {
		ArrayList<ArrayList<Integer>> testData = new ArrayList<ArrayList<Integer>>();
		try(BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
			String line;
			while((line = br.readLine()) != null) {
				ArrayList<Integer> row = new ArrayList<Integer>();
				String[] values = line.split("\\t");
				for(String i : values)
					row.add(Integer.valueOf(i));
				testData.add(row);
			}
		}
		return testData;
	}
}
