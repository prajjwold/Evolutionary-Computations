/**
 * 
 */
package com.cs572.assignments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author prajjwol
 *
 */
public class App {
	public static void main(String[] args) {
		Function func = new RosenbrockFunction();
		execute(func);
		// execute();
		// Function func = new RosenbrockFunction();
		// SteadyStateGA GA = new SteadyStateGA(func);
		// GA.run();
	}

	public static void execute() {
		Function func1 = new SphericalFunction();
		Function func2 = new RosenbrockFunction();
		Function func3 = new RastriginFunction();
		Function func4 = new SchwefelFunction();
		Function func5 = new AckleyFunction();
		Function func6 = new GriewangkFunction();
		Function[] functions = { func1, func2, func3, func4, func5, func6 };
		for (Function function : functions) {
			execute(function);
		}
	}

	// Function to study the behaviour on GA in varying Tournament Size
	public static void execute(Function func) {
		SteadyStateGA GA = new SteadyStateGA(func);
		int[] tournamentSize = { 5, 10, 15, 20, 25 };
		// int[] tournamentSize= {30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80 };
		int numOfRuns = 10;
		String currentDir = System.getProperty("user.dir");
		String outputDir = currentDir + File.separator + "DATA";
		File output = new File(outputDir);
		if (!output.exists()) {
			output.mkdirs();
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(outputDir + File.separator + func.getClass().getSimpleName() + ".csv");
			writer.write("TournamentSize,NumIterations,AverageFitness,BestFitness\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int tsize : tournamentSize) {
			int numOfIterations = 0;
			float avgFitness = 0.0f;
			float bestFitness = 0.0f;
			PrintWriter writer1 = null;
			try {
				writer1 = new PrintWriter(
						outputDir + File.separator + func.getClass().getSimpleName() + "_" + tsize + ".csv");
				writer1.write("TournamentSize,NumIterations,AverageFitness,BestFitness\n");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			System.out.println("TournamentSize:" + tsize);
			for (int run = 0; run < numOfRuns; run++) {
				Constants.TOURNAMENT_SIZE = tsize;
				GA.execute(func);
				numOfIterations += GA.iterations;
				avgFitness = +GA.popn.getAvgFitness();
				bestFitness += GA.popn.getBestFitness();
				System.out.println("Run:" + run);
				writer1.write(tsize + "," + GA.iterations + "," + GA.popn.getAvgFitness() + ","
						+ GA.popn.getBestFitness() + "\n");
				GA.popn = null;
				System.gc();
			}
			numOfIterations /= numOfRuns;
			avgFitness /= numOfRuns;
			bestFitness /= numOfRuns;
			writer.write(tsize + "," + numOfIterations + "," + avgFitness + "," + bestFitness);
			writer1.close();
		}
		writer.close();

	}

}
