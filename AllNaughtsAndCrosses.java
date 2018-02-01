// Candidate Number: 91755
public class AllNaughtsAndCrosses {
	
	private static String stringArray[] = new String[256]; // Size of array is 2^8
	private static int i = 0; // An index for stringArray
	private static int countWinForX; // Counter for winning games for X
	private static int countWinForO;// Counter for winning games for O
	private static int countDraw;// Counter for draws
	
	
	/* Calls binaryHelper method, creating permutations that start with X
	 */
	public static void fillstringArray(int n){
	    binaryHelper(n,"X");
	}

	/* This method creates a permutation of binary strings of length n,
	 * and put them into stringArray[].
	 */
	public static void binaryHelper(int n,String s){
	    if(s.length()== n) {
	    	stringArray[i] = s;
	    	i++;
	    }
	    else{
	        binaryHelper(n, s+"X"); 
	        binaryHelper(n, s+"O");
	    }
	}
	
	/* For each string in the array, create a game tree, and checks root's 
	 * winning position, while counting them accordingly.
	 * For all games that result in a draw, print out the strings.
     */
	public static void checkWinningOXDRAW(String[] array) {
		for (int i=0; i<array.length; i++) {
	        GameTree newGameTree = new GameTree(array[i]);
	        if (newGameTree.root.getWinFor() == GameTree.player_X) 
	        	countWinForX++;
	        else if (newGameTree.root.getWinFor() == GameTree.player_O) 
	        	countWinForO++;
	        // if getWinFor() is a Draw
	        else {
	        	System.out.println(array[i]);
	        	countDraw++;
	        }
		}
	}
	
	public static void main(String[] args) {
		fillstringArray(9);
		System.out.println("List of draw games: ");
	    checkWinningOXDRAW(stringArray);
		System.out.println("Number of winning games for X: " + countWinForX);
		System.out.println("Number of winning games for O: " + countWinForO);
		System.out.println("Number of draws: " + countDraw);
//		System.out.println(stringArray[255]);
	}
	
}
