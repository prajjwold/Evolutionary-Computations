/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs572.assignments.Project2.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.cs572.assignments.Project2.model.GPConstants;
import com.cs572.assignments.Project2.model.GeneticProgramming;
import com.cs572.assignments.Project2.model.Population;
import com.cs572.assignments.Project2.utility.InputFileLoader;

/**
 *
 * @author prajjwol
 */
public class Project2 {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		execute();
//
//		InputFileLoader input = new InputFileLoader();
//		input.loadFile("GPProjectData.csv");
//		int[] tournamentSize = { 5, 10, 15, 20, 25, 30, 35, 49, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100 };
//		for (int i = 0; i < tournamentSize.length; i++) {
//			GPConstants.TOURNAMENT_SIZE = tournamentSize[i];
//			GeneticProgramming gp = new GeneticProgramming();
//			gp.run();
//		}

	}

	public static void execute() throws FileNotFoundException {
		InputFileLoader input = new InputFileLoader();
		input.loadFile("GPProjectData.csv");
		GeneticProgramming gp = new GeneticProgramming();
		int[] tournamentSize = { 5, 10, 15, 20, 25, 30, 35, 49, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100 };
		int numOfRuns = 1;
		String currentDir = System.getProperty("user.dir");
		String outputDir = currentDir + File.separator + "DATA";
		File output = new File(outputDir);
		if (!output.exists()) {
			output.mkdirs();
		}
		PrintWriter writer = new PrintWriter(
				outputDir + File.separator + "TournamentSizeVsFitness" + System.currentTimeMillis() + ".csv");
		writer.write("TournamentSize,NumIterations,AverageFitness,BestFitness\n");
		Population popn = new Population();
		popn.generate();
		for (int tsize : tournamentSize) {
			int numOfIterations = 0;
			float avgFitness = 0.0f;
			float bestFitness = 0.0f;
			PrintWriter writer1 = new PrintWriter(
					outputDir + File.separator + "TournamentSizeVsFitness_" + tsize + ".csv");
			writer1.write("TournamentSize,NumIterations,AverageFitness,BestFitness\n");

			System.out.println("TournamentSize:" + tsize);
			for (int run = 0; run < numOfRuns; run++) {
				GPConstants.TOURNAMENT_SIZE = tsize;
				Population popn1 = new Population();
				popn1.copy(popn);
				gp.setPopulation(popn1);
				gp.execute();
				numOfIterations += gp.iterations;
				avgFitness += gp.popn.getAvgFitness();
				bestFitness += gp.popn.getBestFitness();
				System.out.println("Run:" + run);
				writer1.write(tsize + "," + gp.iterations + "," + gp.popn.getAvgFitness() + ","
						+ gp.popn.getBestFitness() + "\n");
				System.gc();
			}
			numOfIterations /= numOfRuns;
			avgFitness /= numOfRuns;
			bestFitness /= numOfRuns;
			writer.write(tsize + "," + numOfIterations + "," + avgFitness + "," + bestFitness + "\n");
			writer1.close();
		}
		writer.close();
	}

}
