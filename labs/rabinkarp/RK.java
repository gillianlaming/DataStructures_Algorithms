package rabinkarp;

public class RK {

	//
	// Be sure to look at the write up for this assignment
	//  so that you get full credit by satisfying all
	//  of its requirements
	//

	private int m; 
	private String query;
	private String target;
	private int h;
	private char circBuffer [];
	private int counter;

	/**
	 * Rabin-Karp string matching for a window of the specified size
	 * @param m size of the window
	 */
	public RK(int m) {
		this.m = m;
		h = 0;
		circBuffer = new char [m];
		counter = 0;
	}


	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return
	 */
	public int nextCh(char d) {
		
		int a = circBuffer[counter];
		circBuffer[counter] = d;

		h =  Math.floorMod((31*h - power(m)*a + d), 511); 

		counter = (counter+1) % m;
		return h;

	}

	public int power (int m) {
		int sum = 1;
		if(m == 0) {
			return 0;
		}
		for (int i = 0; i < m; i++) {

			sum = (sum * 31)%511;
		}
		return sum;
	}

}
