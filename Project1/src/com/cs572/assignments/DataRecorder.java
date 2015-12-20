package com.cs572.assignments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DataRecorder {
	public static NumberFormat formatter = new DecimalFormat("#0.00");

	/**
	 * FitnessVsGeneration Generation BestFitness AvgFitness
	 */
	static PrintWriter writer1;
	static PrintWriter writer2;

	public static void open() {
		try {
			String currentDir = System.getProperty("user.dir");
			String outputDir = currentDir + File.separator + "output";
			File output = new File(outputDir);
			if (!output.exists()) {
				output.mkdirs();
			}
			writer1 = new PrintWriter(
					outputDir + File.separator + "FitnessVsGeneration" + System.currentTimeMillis() + ".csv");
			writer2 = new PrintWriter(
					outputDir + File.separator + "TimeTakenVsDimension" + System.currentTimeMillis() + ".csv");

			writer1.write("Generations,AverageFitness,BestFitness\n");
			writer2.write("Dimension,TimeTaken(NanoSeconds)\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		writer1.close();
		writer2.close();

	}

	public static void write1(int generation, double avgFitness, double bestFitness) {
		writer1.write(generation + "," + formatter.format(avgFitness) + "," + formatter.format(bestFitness) + "\n");
	}

	public static void write2(int dimension, double timeTaken) {
		writer2.write(dimension + "," + formatter.format(timeTaken) + "\n");
	}

}
