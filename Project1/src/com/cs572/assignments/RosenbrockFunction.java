/**
 * 
 */
package com.cs572.assignments;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author prajjwol </br>
 * @description RosenbrockFunction Function </br>
 *              MINRANGE = -2.048 </br>
 *              MAXRANGE = 2.048 </br>
 *              At x=[1,1,1,.....1] f(x)=0 </br>
 */
public class RosenbrockFunction implements Function {
	public final double MINRANGE = -2.048;
	public final double MAXRANGE = 2.048;
	public final int DIMENSION = 30;

	public double getMINRANGE() {
		return MINRANGE;
	}

	public double getMAXRANGE() {
		return MAXRANGE;
	}

	/**
	 * This function calculate the fitness value for Rosenbrock Function
	 * 
	 * @param param
	 *            Individual genome representation as float array
	 * @return the fitness value of the Individual
	 */
	@Override
	public float calcFitness(float[] param) {
		float result = 0.0f;

		for (int i = 0; i < param.length - 1; i++) {
			result += 100 * (Math.pow((param[i + 1] - param[i]), 2) + Math.pow((param[i] - 1), 2));
		}
		return Math.abs(result);
		// return result;
	}

	@Override
	public boolean checkBoundary(float[] param) {
		for (float x : param) {
			// if out of range [-2.048,2.048]
			if (!(x >= MINRANGE && x <= MAXRANGE)) {
				return false;
			}
		}
		// if in the range [-2.048,2.048]
		return true;
	}

	@Override
	public boolean checkBoundary(float x) {
		// if out of range [-2.048,2.048]
		if (!(x >= MINRANGE && x <= MAXRANGE)) {
			return false;
		}
		// if in the range [-2.048,2.048]
		return true;
	}

	@Override
	public float[] getInitSolution() {
		float[] initSolution = new float[DIMENSION];
		// generate Initial Solution
		// generate Random values within the range
		Random rnd = new Random();
		for (int i = 0; i < DIMENSION; i++) {
			NumberFormat formatter = new DecimalFormat("#0.00");
			initSolution[i] = Float.valueOf(formatter.format((MINRANGE + rnd.nextFloat() * (MAXRANGE - MINRANGE))));
		}
		return initSolution;
	}

}
