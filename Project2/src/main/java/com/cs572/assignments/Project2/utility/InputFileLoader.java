/**
 * 
 */
package com.cs572.assignments.Project2.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

/**
 * @author prajjwol
 *
 */
public class InputFileLoader {
	private static int NumDataRows;
	private static int NumXVar;
	public static float inputdata[][];
	public static float outputdata[];
	private final String DELIMITER = ",";

	public void loadFile(String fname) {
		BufferedReader in;
		try {
			NumDataRows = 0;
			NumXVar = 0;
			in = new BufferedReader(new FileReader(fname));
			// Read the header line
			String line = in.readLine();
			StringTokenizer tokens = new StringTokenizer(line, DELIMITER);
			NumXVar = tokens.countTokens() - 1;
			// Read the actual data rows
			line = in.readLine();
			while (line != null) {
				NumDataRows++;
				line = in.readLine();
			}
			// System.out.println("No of X variables: " + NumXVar);
			// System.out.println("No of data rows: " + NumDataRows);
			inputdata = new float[NumDataRows][NumXVar];
			outputdata = new float[NumDataRows];
			in.close();

			in = new BufferedReader(new FileReader(fname));
			// Read the header line
			line = in.readLine();
			for (int i = 0; i < NumDataRows; i++) {
				line = in.readLine();
				tokens = new StringTokenizer(line, DELIMITER);
				for (int j = 0; j < NumXVar; j++) {
					inputdata[i][j] = Float.parseFloat(tokens.nextToken().trim());
					// System.out.print(inputdata[i][j] + " ");
				}
				outputdata[i] = Float.parseFloat(tokens.nextToken().trim());
				// System.out.println(outputdata[i]);
			}
			in.close();

		} catch (FileNotFoundException e) {
			System.out.println("ERROR: Please provide a data file");
			System.exit(0);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("ERROR: Incorrect data format");
			System.exit(0);
		}
	}

	public static int getNumDataRows() {
		return NumDataRows;
	}

	public static int getNumXVar() {
		return NumXVar;
	}

	public float[][] getInputData() {
		return inputdata;
	}

	public float[] getOutputData() {
		return outputdata;
	}
}
