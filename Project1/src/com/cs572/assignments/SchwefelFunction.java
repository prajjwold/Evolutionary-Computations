package com.cs572.assignments;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author prajjwol </br>
 * @description SchwefelFunction Function </br>
 *              MINRANGE = -512.03 </br>
 *              MAXRANGE = 511.97 </br>
 *              At x=[0,0,0,.....0] f(x)=0 </br>
 */
public class SchwefelFunction implements Function {
	public final double MINRANGE = -512.03;
	public final double MAXRANGE = 511.97;
	public final int DIMENSION = 30;

	public double getMINRANGE() {
		return MINRANGE;
	}

	public double getMAXRANGE() {
		return MAXRANGE;
	}

	@Override
	public float calcFitness(float[] param) {
		float result = 0;
		if (param.length != DIMENSION) {
			System.out.println("Invalid parameters");
			return 0;
		}

		else {

			result = (float) (418.9829 * DIMENSION);
			for (int i = 0; i < param.length; i++) {
				result += param[i] * Math.sin(Math.sqrt(Math.abs(param[i])));
			}
		}
		return result;
	}

	@Override
	public boolean checkBoundary(float[] param) {
		for (float x : param) {
			// if out of range [-512.03, 511.97]
			if (!(x >= MINRANGE && x <= MAXRANGE)) {
				return false;
			}
		}
		// if in the range [-5.12,5.12]
		return true;
	}

	@Override
	public boolean checkBoundary(float x) {
		// if out of range [-512.03, 511.97]
		if (!(x >= MINRANGE && x <= MAXRANGE)) {
			return false;
		}
		// if in the range [-512.03, 511.97]
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
