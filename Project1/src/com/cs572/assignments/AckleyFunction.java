package com.cs572.assignments;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/**
 * @author prajjwol </br>
 * @description Ackley Function</br>
 *              MINRANGE = -30 </br>
 *              MAXRANGE = 30 </br>
 *              At x=[0,0,0....0] f(x)=0 </br>
 */
public class AckleyFunction implements Function {

	public final double MINRANGE = -30;
	public final double MAXRANGE = 30;
	public final int DIMENSION = 30;

	public double getMINRANGE() {
		return MINRANGE;
	}

	public double getMAXRANGE() {
		return MAXRANGE;
	}

	/**
	 * This function calculate the fitness value for Ackley Function
	 * 
	 * @param param
	 *            Individual genome representation as float array
	 * @return the fitness value of the Individual
	 */
	@Override
	public float calcFitness(float[] param) {
		float result = (float) (20.0f + Math.exp(1));
		for (int i = 0; i < param.length; i++) {
			result -= 20 * Math.exp(-0.2 * Math.sqrt((1.0f / DIMENSION) * Math.pow(param[i], 2)))
					+ Math.exp((1.0f / DIMENSION) * Math.cos(2 * Math.PI * param[i]));
		}
		return Math.abs(result);
	}

	@Override
	public boolean checkBoundary(float[] param) {
		for (float x : param) {
			// if out of range [-30,30]
			if (!(x >= MINRANGE && x <= MAXRANGE)) {
				return false;
			}
		}
		// if in the range [-30,30]
		return true;
	}

	@Override
	public boolean checkBoundary(float x) {
		// if out of range [-30,30]
		if (!(x >= MINRANGE && x <= MAXRANGE)) {
			return false;
		}
		// if in the range [-30,30]
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
