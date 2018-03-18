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
		else {
			//K arrays, each size n/K
			Integer[][] smallerArray = new Integer [K][n/K];
			int w = 0;
			for(int j = 0; j<K; ++j) { //row
				for (int i = 0; i<n/K; ++i) { //column
					smallerArray[j][i] = input[w++]; 
				}
			}
			//row extractor method, double array, length and index number as params
			//go thrua each row, sort it, put it back into the originial double array
			
			for (int i = 0; i< K; ++i) {
				Integer [] temp = new Integer[n/K];
				temp = kwaymergesort(K, extractRow(i, n/K, smallerArray), ticker);
				for (int j = 0; j<n/K; j++) {
					smallerArray[i][j] = temp[j];
				}
			}
			
			Integer [] one = extractRow(0, n/K, smallerArray);
			Integer [] another = mergeTwo(one, extractRow(1, n/K, smallerArray));
			for (int i = 2; i<K; ++i) {
				another = mergeTwo(another, extractRow(i, n/K, smallerArray));
			}
			return another;
			
		

		}


	}


	//merging two arrays method
	public static Integer [] mergeTwo(Integer [] one, Integer [] two) {
		int n = one.length; //this is the length of both of the arrays
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


	//make sure this is working!!
	public static Integer [] extractRow (int index, int length, Integer [][] smallerArray) {
		Integer [] arr = new Integer[length];
		for (int i = 0; i < length; ++i) {
			arr[i] = smallerArray[index][i];
		}
		return arr;
	}
}

// FIXME
// Following just copies the input as the answer
//
// You must replace the loop below with code that performs
// a K-way merge sort, placing the result in ans
//
// The web page for this assignment provides more detail.
//
// Use the ticker as you normally would, to account for
// the operations taken to perform the K-way merge sort.
//
//	Integer[] ans = new Integer[n];
//	for (int i=0; i < n; ++i) {
//		ans[i] = input[i];
//		ticker.tick();
//	}
//
//	return ans;
//}


