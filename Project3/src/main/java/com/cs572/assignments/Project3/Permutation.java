/**
 * 
 */
package com.cs572.assignments.Project3;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prajjwol
 *
 */
public class Permutation {
	public static int[] getSingleInstance(int Dimension) {
		int array[] = new int[Dimension];
		for (int i = 0; i < Dimension; i++) {
			array[i] = i + 1;
		}
		int j, k;
		for (int i = 0; i < Dimension / 2; i++) {
			j = RandomUtils.getInt(Dimension);
			do {
				k = RandomUtils.getInt(Dimension);
			} while (j == k);
			// swap j and k th elements
			swap(array, j, k);
		}
		return array;

	}

	public static int[][] getAllInstance(int Dimension) {
		int arr[] = new int[Dimension];
		for (int i = 0; i < Dimension; i++) {
			arr[i] = i + 1;
		}
		// int searchSpace=IntMath.factorial(Dimension);
		// int array[][]=new int[searchSpace][Dimension];
		List<int[]> resultlist = new ArrayList<int[]>();
		permute(arr, resultlist, 0);
		// for (int[] is : resultlist) {
		// for (int i : is) {
		// System.out.print(i+"\t");
		// }
		// System.out.println();
		// }
		int result[][] = new int[resultlist.size()][Dimension];
		int temp[];
		for (int i = 0; i < result.length; i++) {
			temp = resultlist.get(i);
			for (int j = 0; j < Dimension; j++) {
				result[i][j] = temp[j];
			}
		}
		return result;
	}

	public static void printAllInstance(int Dimension) {
		int result[][] = getAllInstance(Dimension);
		for (int i = 0; i < result.length; i++) {
			System.out.print(i + 1 + ":\t\t");
			for (int j = 0; j < result[0].length; j++) {
				System.out.print(result[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private static void permute(int[] array, List<int[]> result, int k) {

		if (k == array.length - 1) {
			result.add(array.clone()); // use clone, or you will get the same
										// int array
			return;
		}
		for (int i = k; i < array.length; i++) {
			swap(array, i, k);
			permute(array, result, k + 1);
			swap(array, i, k);
		}
	}

	// swap the characters at indices i and j
	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

}
