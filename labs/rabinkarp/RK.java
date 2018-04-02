package rabinkarp;

public class RK {
	
	//
	// Be sure to look at the write up for this assignment
	//  so that you get full credit by satisfying all
	//  of its requirements
	//
	
	//do i need these instance variables?
	private int m; 
	private String query;
	private String target;
	private int h;

	/**
	 * Rabin-Karp string matching for a window of the specified size
	 * @param m size of the window
	 */
	public RK(int m) {
		this.m = m;
//		this.query = query;
//		this.target = target;
//		this.h = h;
		
		for(int i = 0; i<target.length(); ++i) {
			char[] charArray = query.toCharArray(); //gets the value of the rolling hash of the first value in the query string
			int sum = 0;
			for (int j = 0; j<m; ++j) {
				sum = sum + nextCh(charArray[j]); //at the end of loop, sum will be the hash for query
			}
			
		
		}
		
	}
	

	/**
	 * Compute the rolling hash for the previous m-1 characters with d appended.
	 * @param d the next character in the target string
	 * @return
	 */
	//must take theta(1) time
	//incremental approach
	public int nextCh(char d) {
		
		int [] circBuffer = new int [m];
		char [] targetChars = target.toCharArray();
		int index = target.indexOf(d);
		
		if(index < m-1) {
			return 0; //h = 0
		}
		
		//load the first 4 chars of target into circular buffer
		for (int i = (index - m + 1); i < index; i++) {
			circBuffer[i] = targetChars[i];
			h = (int) (31*h - Math.pow(31, m)*(index - m) + i) % 511; //change me!
		}
		
				
			
		int rollingHash = 0;
		return rollingHash;

		
	}

}
