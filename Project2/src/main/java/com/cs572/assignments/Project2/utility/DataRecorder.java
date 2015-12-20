/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs572.assignments.Project2.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author prajjwol
 */
public class DataRecorder {
	public static NumberFormat formatter = new DecimalFormat("#0.00");

	/**
	 * FitnessVsGeneration Generation BestFitness AvgFitness
	 */
	static PrintWriter writer1;
	/**
	 * AvgTreeSize Vs Generation Generation AvgTreeSize
	 */
	static PrintWriter writer2;
	/**
	 * FinalOutput Vs ExpectedOutput Datapoints ActualOutput ExpectedOutput
	 */
	static PrintWriter writer3;

	static PrintWriter writer4;

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
//			writer2 = new PrintWriter(
//					outputDir + File.separator + "TreeSizeVsGeneration" + System.currentTimeMillis() + ".csv");
//			writer3 = new PrintWriter(
//					outputDir + File.separator + "FinalOutputVsExpectedOutput" + System.currentTimeMillis() + ".csv");

			writer1.write("TournamentSize,Generations,AverageFitness,BestFitness\n");
			// writer2.write("Generations,AverageTreeSize\n");
			// writer3.write("DataPoints,ActualOutput,ExpectedOutput\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		writer1.close();
		//writer2.close();
		//writer3.close();
	}

	public static void write1(int tsize, int generation, double avgFitness, double bestFitness) {
		writer1.write(tsize + "," + generation + "," + formatter.format(avgFitness) + ","
				+ formatter.format(bestFitness) + "\n");
	}

	public static void write2(int generation, float avgTreeSize) {
		writer2.write(generation + "," + formatter.format(avgTreeSize) + "\n");
	}

	public static void write3(int dataRow, double actualOutput, double expectedOutput) {
		writer3.write(dataRow + "," + formatter.format(actualOutput) + "," + formatter.format(expectedOutput) + "\n");
	}
}
