/**
 * 
 */
package com.cs572.assignments.Project2.utility;

import java.util.Random;

/**
 * @author prajjwol
 *
 */
public class RandomUtils {
	static Random rnd = new Random();
	public RandomUtils(){
		super();
		rnd = new Random();
	}
	
	public RandomUtils(int seed) {
		rnd = new Random(seed);
	}
	
	public static float getFloat(){
		return rnd.nextFloat();
	}
	public static double getDouble(){
		return rnd.nextDouble();
	}
	public static int getInt(int bound){
		return rnd.nextInt(bound);
	}

	public static float genRandomFloatBetnRange(float startRange, float endRange) {
		float random = startRange + rnd.nextFloat() * (endRange - startRange);
		System.out.println(random);
		return random;
	}

	public static int genRandomIntBetnRange(int startRange, int endRange) {
		
		int random = startRange + (int) (rnd.nextFloat() * (endRange - startRange+1));
		System.out.println(random);
		return random;
	}

}
