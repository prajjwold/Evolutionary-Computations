package com.cs572.assignments;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class SimulatedAnnealing1 {
	public static final float TEMPERATURE = 100;
	public static final float ALPHA = 0.99f;
	public static final float DELTA = 0.1f;
	public NumberFormat formatter = new DecimalFormat("#0.00");

	public float calcProbability(float fitness1, float fitness2, float Temperature) {
		return (float) (1 / (Math.exp((fitness2 - fitness1) / Temperature)));
	}

	public float[][] genNeighbours(float[] prevSolution, Function func) {
		// generate 10 neighbours
		float[][] neighbours = new float[10][30];

		for (int i = 0; i < neighbours.length; i++) {
			Random rnd = new Random();
			// first, make a copy from prevSolution
			for (int j = 0; j < prevSolution.length; j++) {
				neighbours[i][j] = prevSolution[j];
			}
			// second, vary each coefficient of the solution by either addition
			// or subtraction by value DELTA (=0.01) based on some probability
			for (int j = 0; j < 30; j++) {
				float prob = rnd.nextFloat();
				if (prob > 0.5) {
					// apply the delta subtraction only if the result is in the
					// function range
					if (func.checkBoundary(neighbours[i][j] - DELTA)) {
						neighbours[i][j] = Float.valueOf(formatter.format(neighbours[i][j] - DELTA));
					}
				} else {
					// apply the delta subtraction only if the result is in the
					// function range
					if (func.checkBoundary(neighbours[i][j] + DELTA)) {
						neighbours[i][j] = Float.valueOf(formatter.format(neighbours[i][j] + DELTA));
					}
				}
			}
		}
		return neighbours;
	}

	public void run(Function func) {
		float[] initSolution = func.getInitSolution();
		float fitness1 = func.calcFitness(initSolution);

		float fitness2 = (float) 0.0;
		int steps = 1;

		PrintWriter writer = null;
		PrintWriter writer1 = null;
		try {
			writer = new PrintWriter("(SimulatedAnnealing1)solution" + System.currentTimeMillis() + ".txt");
			writer1 = new PrintWriter("(SimulatedAnnealing1)stepVSfitness" + System.currentTimeMillis() + ".csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writer.write("Initial Solution\n");
		for (int i = 0; i < initSolution.length; i++) {
			writer.write("x[" + i + "] = " + initSolution[i] + "\n");
		}
		writer.write("Initial Fitness :" + fitness1 + "\n");
		writer1.write("Step,fitness\n");

		while (steps < 1000 && fitness1 > 0.01) {
			for (float T = TEMPERATURE; T > 0; T *= ALPHA) {
				float[][] neighbours = genNeighbours(initSolution, func);
				for (float[] neighbour : neighbours) {
					fitness2 = func.calcFitness(neighbour);
					if (fitness2 < fitness1) {
						initSolution = neighbour;
						fitness1 = fitness2;
					} else {
						if (calcProbability(fitness1, fitness2, T) > 0.5) {
							initSolution = neighbour;
							fitness1 = fitness2;
						}
					}
				}
				System.out.println("Steps: " + steps);
				for (float x : initSolution) {
					System.out.print(x + ", ");
				}
				System.out.println();
				System.out.println("fitness: " + fitness1);
				System.out.println("T: " + T);
				writer1.write(steps + "," + fitness1 + "\n");
			}
			steps++;
		}
		writer.write("\nFinal Solution\n");
		for (int i = 0; i < initSolution.length; i++) {
			writer.write("x[" + i + "] = " + initSolution[i] + "\n");
		}
		writer.write("Final Fitness :" + fitness1 + "\n");
		writer.write("Number of Steps :" + steps + "\n");
		System.out.println("Number of Steps : " + steps);
		writer.close();
		writer1.close();
	}

}
