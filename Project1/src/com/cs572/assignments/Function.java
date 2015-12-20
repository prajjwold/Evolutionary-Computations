/**
 * 
 */
package com.cs572.assignments;

/**
 * @author prajjwol
 * @category CS572
 */
public interface Function {
	public double getMINRANGE();
	public double getMAXRANGE();
	public float calcFitness(float[] param);
	public boolean checkBoundary(float[] param); 
	public boolean checkBoundary(float x);
	public float[] getInitSolution();

}
