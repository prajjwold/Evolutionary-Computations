package com.cs572.assignments;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HillClimbing {
	public static final float DELTA = 0.5f;
	public NumberFormat formatter = new DecimalFormat("#0.00");

	public float[][] genNeighbours(float[] prevSolution, Function func) {
		float[][] neighbours = new float[10][30];
		for (int i = 0; i < neighbours.length; i++) {
			Random rnd = new Random();
			for (int j = 0; j < prevSolution.length; j++) {
				neighbours[i][j] = prevSolution[j];
			}
			for (int j = 0; j < 30; j++) {
				float prob = rnd.nextFloat();
				if (prob > 0.5) {
					// apply the delta subtraction only if the result is in the
					// function range
					if (func.checkBoundary(neighbours[i][j] - DELTA)) {
						neighbours[i][j] = Float.valueOf(formatter.format(neighbours[i][j] - DELTA));
					}
				} else {
					// apply the delta addition only if the result is in the
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
		float fitness2 = 0.0f;
		int steps = 1;

		PrintWriter writer = null;
		PrintWriter writer1 = null;
		try {
			writer = new PrintWriter("(HillClimbing)solution" + System.currentTimeMillis() + ".txt");
			writer1 = new PrintWriter("(HillClimbing)stepVSfitness" + System.currentTimeMillis() + ".csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writer.write("Initial Solution\n");
		for (int i = 0; i < initSolution.length; i++) {
			writer.write("x[" + i + "] = " + initSolution[i] + "\n");
		}
		writer.write("Initial Fitness :" + fitness1 + "\n");
		writer1.write("Step,fitness\n");
		while (steps <= 100000 && fitness1 > 0.01) {
			float[][] neighbours = genNeighbours(initSolution, func);
			for (float[] neighbour : neighbours) {
				fitness2 = func.calcFitness(neighbour);
				if (fitness2 < fitness1) {
					initSolution = neighbour;
					fitness1 = fitness2;
				}
			}
			System.out.println("Steps: " + steps);
			for (float x : initSolution) {
				System.out.print(x + ", ");
			}
			System.out.println();
			System.out.println("fitness: " + fitness1);
			writer1.write(steps + "," + fitness1 + "\n");
			steps++;
		}
		writer.write("\nFinal Solution\n");
		for (int i = 0; i < initSolution.length; i++) {
			writer.write("x[" + i + "] = " + initSolution[i] + "\n");
		}
		writer.write("Final Fitness :" + fitness1 + "\n");
		writer.write("Number of Steps :" + steps + "\n");
		writer.close();
		writer1.close();
	}
	
	public Map<String,Float> iterativeRun(Function func,float[] initSolution) {
		Map<String, Float> results = new HashMap<String,Float>();
		float fitness1 = func.calcFitness(initSolution);
		float fitness2 = 0.0f;
		int steps = 1;
		results.put("Initial Fitness", fitness1);
		while (steps <= 100000 && fitness1 > 0.01) {
			float[][] neighbours = genNeighbours(initSolution, func);
			for (float[] neighbour : neighbours) {
				fitness2 = func.calcFitness(neighbour);
				if (fitness2 < fitness1) {
					initSolution = neighbour;
					fitness1 = fitness2;
				}
			}
			steps++;
		}
		
		results.put("Final Fitness", fitness1);
		results.put("Iterations", (float)steps);
		return results;
	}
}
