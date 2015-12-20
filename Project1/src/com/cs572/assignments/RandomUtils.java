/**
 * 
 */
package com.cs572.assignments;

import java.util.Random;

/**
 * @author prajjwol
 *
 */
public class RandomUtils {
	static Random rnd = new Random();

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
