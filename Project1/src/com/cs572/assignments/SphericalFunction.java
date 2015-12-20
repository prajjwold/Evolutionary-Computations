/**
 * 
 */
package com.cs572.assignments;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author prajjwol </br>
 * @description SphericalFunction Function </br>
 *              MINRANGE = -5.12 </br>
 *              MAXRANGE = 5.12 </br>
 *              At x=[0,0,0,.....0] f(x)=0 </br>
 */
public class SphericalFunction implements Function {
	public final double MINRANGE = -5.12;
	public final double MAXRANGE = 5.12;
	public final int DIMENSION = 30;

	public double getMINRANGE() {
		return MINRANGE;
	}

	public double getMAXRANGE() {
		return MAXRANGE;
	}

	/**
	 * This function calculate the fitness value for Spherical Function
	 * 
	 * @param param
	 *            Individual genome representation as float array
	 * @return the fitness value of the Individual
	 */
	@Override
	public float calcFitness(float[] param) {
		float result = 0.0f;

		for (int i = 0; i < param.length; i++) {
			result += param[i] * param[i];
		}
		return Math.abs(result);
		//return result;
	}

	@Override
	public boolean checkBoundary(float[] param) {
		for (float x : param) {
			// if out of range [-5.12,5.12]
			if (!(x >= MINRANGE && x <= MAXRANGE)) {
				return false;
			}
		}
		// if in the range [-5.12,5.12]
		return true;
	}

	@Override
	public boolean checkBoundary(float x) {
		// if out of range [-5.12,5.12]
		if (!(x >= MINRANGE && x <= MAXRANGE)) {
			return false;
		}
		// if in the range [-5.12,5.12]
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
