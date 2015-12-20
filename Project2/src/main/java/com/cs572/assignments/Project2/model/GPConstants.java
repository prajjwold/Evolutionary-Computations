/**
 *
 */
package com.cs572.assignments.Project2.model;

/**
 * @author prajjwol
 *
 */
public class GPConstants {

	public static final float CONST_LIMIT = 10.0f;
	public static String[] functionSet = { "+", "-", "/", "*" };
	public static final int POPSIZE = 100;
	public static final int MAXDEPTH = 4;
	public static final int GENERATIONS = 3000;
	public static final float CROSSOVER_PROB = 0.4f;
	public static float MUTATION_PROB = 0.2f;
	// public static float MUTATION_PROB = 1 - CROSSOVER_PROB;
	public static float MUTATION_PER_NODE_PROB = 0.3f;
	public static int TOURNAMENT_SIZE = 10;

	// Unused Constants
	public static final int MAX_ARITY = 4;
	public static final int MAXLENGTH = 100000;
	public static float FITNESS_TOLERANCE = 0.00001f;

}
