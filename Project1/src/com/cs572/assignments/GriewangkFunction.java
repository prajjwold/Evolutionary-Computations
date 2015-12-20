/**
 * 
 */
package com.cs572.assignments;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author prajjwol
 * @description Griewangk Function </br>
 *              MINRANGE = -600 </br>
 *              MAXRANGE = 600 </br>
 *              At x=[0,0,0,.....0] f(x)=0 </br>
 */
public class GriewangkFunction implements Function {

	public final double MINRANGE = -600;
	public final double MAXRANGE = 600;
	public final int DIMENSION = 30;

	public double getMINRANGE() {
		return MINRANGE;
	}

	public double getMAXRANGE() {
		return MAXRANGE;
	}

	/**
	 * This function calculate the fitness value for Griewangk Function
	 * 
	 * @param param
	 *            Individual genome representation as float array
	 * @return the fitness value of the Individual
	 */
	@Override
	public float calcFitness(float[] param) {
		float result = 1.0f;
		float sumpart = 0.0f;
		float productpart = 0.0f;

		for (int i = 0; i < param.length; i++) {
			sumpart += Math.pow(param[i], 2) / 4000.0f;
			productpart *= Math.cos(param[i] / Math.sqrt(i + 1));
		}
		return Math.abs(result + sumpart - productpart);
		// return (result + sumpart - productpart);
	}

	@Override
	public boolean checkBoundary(float[] param) {
		for (float x : param) {
			// if out of range [-600,600]
			if (!(x >= MINRANGE && x <= MAXRANGE)) {
				return false;
			}
		}
		// if in the range [-600,600]
		return true;
	}

	@Override
	public boolean checkBoundary(float x) {
		// if out of range [-600,600]
		if (!(x >= MINRANGE && x <= MAXRANGE)) {
			return false;
		}
		// if in the range [-600,600]
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
