package kwaymergesort;

import timing.Ticker;

public class KWayMergeSort {

	/**
	 * 
	 * @param K some positive power of 2.
	 * @param input an array of unsorted integers.  Its size is either 1, or some other power of 2 that is at least K
	 * @param ticker call .tick() on this to account for the work you do
	 * @return
	 */
	public static Integer[] kwaymergesort(int K, Integer[] input, Ticker ticker) {
		int n = input.length;

		if (n == 1) {	//base case
			return input;
		}
		
		//the test is passing, but i'm not 100% sure why this is producing a sorted array
		else {
			//constant time, just initializing an array
			Integer[][] smallerArray = new Integer [K][n/K];
			int w = 0;
			for(int j = 0; j<K; ++j) { //row
				for (int i = 0; i<n/K; ++i) { //column
					smallerArray[j][i] = input[w++]; 
					ticker.tick();
				}
			}
			//
			for (int i = 0; i< K; ++i) {
				Integer [] temp = new Integer[n/K];
				//temp = kwaymergesort(K, extractRow(i, n/K, smallerArray), ticker);
				temp = kwaymergesort(K, smallerArray[i], ticker);
				ticker.tick();
				for (int j = 0; j<n/K; j++) {
					smallerArray[i][j] = temp[j];
					ticker.tick();
				}
			}

			return recursiveMerge(smallerArray, ticker);
			

			//			Integer [] one = extractRow(0, n/K, smallerArray);
			//			Integer [] another = mergeTwo(one, extractRow(1, n/K, smallerArray));
			//			for (int i = 2; i<K; ++i) {
			//				another = mergeTwo(another, extractRow(i, n/K, smallerArray));
			//			}
			//			return another;
			//			
		}
	}


	
	/**
	 * 
	 * @param one, a 1d array
	 * @param two, another 1d array
	 * @return the sorted combination of both arrays
	 */
	public static Integer [] mergeTwo(Integer [] one, Integer [] two) { //what is the runtime on this
		int n = one.length; 
		int m = two.length;

		Integer [] combined = new Integer[m+n];

		int j = 0; //one
		int k = 0; //two
		int i = 0; 
		while (j< n && k<m) {

			if (one[j]<two[k]) {
				combined [i++] = one[j++];
			}
			else {
				combined [i++] = two[k++];
			}
		}
		while (j<n) {
			combined[i++] = one[j++];
		}
		while (k<m) {
			combined[i++] = two[k++];
		}

		return combined;
	}

	//what is the runtime for this....is it really necessary 
	/**
	 * 
	 * @param index, index of the desired row
	 * @param length, length of the desired row (number of columns)
	 * @param smallerArray, the double array that the row will be extracted from
	 * @return
	 */
//	public static Integer [] extractRow (int index, int length, Integer [][] smallerArray) {
//		Integer [] arr = new Integer[length];
//		for (int i = 0; i < length; ++i) {
//			arr[i] = smallerArray[index][i];
//		}
//		return arr;
//	}

	/**
	 * 
	 * @param arr, double array 
	 * @return the sorted combination of all of the rows of that array
	 */
	public static Integer [] recursiveMerge (Integer [][] arr, Ticker ticker) {
		
		int n = arr.length; //num rows
		int m = arr[0].length; //num columns
		
		if (n == 1) {
			return arr[0];
		}

		else {
			Integer [][] arr1 = new Integer [n/2][m];
			Integer [][] arr2 = new Integer [n/2][m];
			for (int i = 0; i < n/2; ++i) {
				for (int j = 0; j < m; ++j) {
					arr1 [i][j] = arr[i][j];
					ticker.tick();
				}
			}
			for (int i = n/2; i < n; ++i) {
				for (int j = 0; j<m; j++) {
					arr2 [i-(n/2)][j] = arr[i][j];
					ticker.tick();
				}
			}
			return (mergeTwo(recursiveMerge(arr1, ticker), recursiveMerge(arr2, ticker)));
		}
		
	}
}



