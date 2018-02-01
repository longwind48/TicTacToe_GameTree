// Candidate Number: 91755
// Implements a Noughts and Crosses board with positions numbered as follows:
//  0 | 1 | 2 
// ---+---+---
//  3 | 4 | 5
// ---+---+---
//  6 | 7 | 8
public class Board {
    public char[][] board;
    public static final char X_TOKEN = 'X'; // Contains a character that represents X
    public static final char O_TOKEN = 'O'; // Contains a character that represents O
    public static final char EMPTY = ' '; // Contains a character that represents and empty position
    public int numberOfOccupied; // Counter for number of occupied cells
    public final int row = 3, col = 3;
    public Board temp;


    // Constructor for empty board
    public Board() {
    	board = new char[row][col];
    	for (int j=0; j <row; j++) {
    		for(int k=0; k<col; k++) {
    			board[j][k] = EMPTY;
//    			numberOfOccupied = 0;
    		}
    	}
    }

    // Constructor for board contained in string c
    public Board(String c) { 
    	//check if string has exactly 9 characters.
    	if (c.length()>9 || c.length()<9) 
    		throw new IllegalArgumentException("Input must have 9 characters! Or put ' ' if there are spaces within your string!");
    	else {
    		//check if string contains O, X or Spaces.
    		for (int p=0; p<c.length(); p++) { 
    			if (c.charAt(p)!=X_TOKEN)
        			if (c.charAt(p)!=O_TOKEN)
            			if (c.charAt(p)!=EMPTY)
            				throw new IllegalArgumentException("Input must contain only O, X or space!");
    		}

    	board = new char[row][col];
    	int i = 0; //an index for positions of char of string c
    	for (int j=0; j<row; j++) {
    		for(int k=0; k<col; k++) {
    			board[j][k] = c.charAt(i++);
				if (isOccupied(getNumber(j,k))) //Counts number of non-empty entries on board 
					numberOfOccupied++;
    		}
    	}
    	}
    }
    // Check for three similar entries
    boolean checkThree(char ch1, char ch2, char ch3) {
    	return (ch1 != EMPTY && ch1==ch2 && ch2==ch3);
    }
    
    // Returns true if there are 3 X's joined in a row.
    boolean checkRowForWinningX() {
    	for (int j=0; j<row; j++) {
    		if (checkThree(board[j][0], board[j][1], board[j][2]) && board[j][0]==X_TOKEN) 
    			return true;
    	}
    	return false;
    }
    
    // Returns true if there are 3 O's joined in a row.
    boolean checkRowForWinningO() {
    	for (int j=0; j<row; j++) {
    		if (checkThree(board[j][0], board[j][1], board[j][2]) && board[j][0]==O_TOKEN) 
    			return true;
    	}
    	return false;
    }
   
   // Returns true if there are 3 X's joined in a column.
   boolean checkColForWinningX() {
	   for (int k=0; k<col; k++) {
   		if (checkThree(board[0][k], board[1][k], board[2][k]) && board[0][k]==X_TOKEN) 
   			return true;
   	}
   	return false;
   }
   
   // Returns true if there are 3 O's joined in a column.
   boolean checkColForWinningO() {
	   for (int k=0; k<col; k++) {
   		if (checkThree(board[0][k], board[1][k], board[2][k]) && board[0][k]==O_TOKEN) 
   			return true;
   	}
   	return false;
   }
   
   // Returns true if there are 3 X's joined diagonally.
   boolean checkDiagonalForWinningX() {
	   return (checkThree(board[0][0],board[1][1],board[2][2]) && board[0][0]==X_TOKEN ||
			   checkThree(board[0][2], board[1][1], board[2][0]) && board[0][2]==X_TOKEN);
   }
   // Returns true if there are 3 O's joined diagonally.
   boolean checkDiagonalForWinningO() {
	   return (checkThree(board[0][0],board[1][1],board[2][2]) && board[0][0]==O_TOKEN ||
			   checkThree(board[0][2], board[1][1], board[2][0]) && board[0][2]==O_TOKEN);
   }


    // Returns true if there are three consecutive X's in a row
    boolean isWinForX() {
    	return (checkRowForWinningX() ||
    			checkColForWinningX() ||
    			checkDiagonalForWinningX());
    }

    // Returns true if there are three consecutive O's in a row
    boolean isWinForO() {
    	return (checkRowForWinningO() ||
    			checkColForWinningO() ||
    			checkDiagonalForWinningO());
    }

    // Returns true if no three consecutive X's or O's in a row and when board is full
    boolean isDraw() {
    	return (!isWinForX() && !isWinForO() && isFull()); 
    }
    
    /* Check each position of board for empty entries, 
     * If at least 1 of them is empty, board is not full.
     */
    boolean isFull() {
    	boolean full = true;
    	for (int j=0; j<row; j++) {
    		for(int k=0; k<col; k++) {
    			if (board[j][k] == EMPTY)
    				full = false;
    		}
    	}
        return full;
    }
    // Game is over when there is a draw, or winning player.
    boolean isGameOver() {
    	return (isDraw() || isWinForX() || isWinForO());
    }
  
    /* returns true if pos is a valid position number from 1 to 9 
     * and space is not empty.
     */
    boolean isOccupied(int pos) {
    	if (pos>=0 && pos<9) {
    		return ( board[pos/3][pos-((pos/3)*3)] == X_TOKEN 	
    				|| board[pos/3][pos-((pos/3)*3)] == O_TOKEN );
    	}
    	return false;
    }

    /* Return contents of position (0 to 8) of the board.
     * board[pos/3][pos-((pos/3)*3)] is our method of conversion from int to 2D array.
     */
    char getPos(int pos) {
    	if (pos>=0 && pos<9) {
    		return ( board[pos/3][pos-((pos/3)*3)] );
    	}
    	return EMPTY;
    }
    
    /* returns true if there are three consecutive c's in a row
     * otherwise false
     */
    boolean isWinFor(char c) {
    	if (c == X_TOKEN)
    		return (isWinForX());
    	if (c == O_TOKEN)
    		return (isWinForO());
		return false;
    }
    
    
    /* places character c in position pos of board,  
     * returns new board if this position is unoccupied.
     * Returns null if invalid pos or if position is occupied.
     */
    Board move(int pos, char c) {

    	temp = new Board();
    	temp.board = board;
    	Board h = new Board();

    	if (temp.isOccupied(pos)) 
    		return null;
    	else if (pos<0 || pos >8)
    		return null;
    	else {
        	h.numberOfOccupied = numberOfOccupied+1; //increase count by one because move is valid
    		for (int j=0; j<row; j++) {
        		for(int k=0; k<col; k++) {
    				h.board[j][k] = temp.board[j][k]; // copy entries of old board to new board
        			if (pos == h.getNumber(j,k)) {  
        				//replace position index's entry with c if pos = position index
        				h.board[j][k] = c;		
        			}
        		}
    		}
    	}
		return h;		
    }
    
    // Prints out board with internal borders, e.g.
    // X | X | O 
    //---+---+---
    // O | O | X 
    //---+---+---
    // O | O | X 
    void print() {

    	for (int j=0; j<row; j++) {
    		for(int k=0; k<col; k++) {
    			if (k!=col-1)
					System.out.print(" " + board[j][k] + " " + "|");
				else
	    			System.out.println(" " + board[j][k] + " ");
    		}
    		if (j!=row-1) 
                System.out.println("---+---+---");
    		else  
    			System.out.println();
    	}
    }
    
    /* Prints board with indentation. (used in GameTree.java)
     */
    void printWithIndent(String spaces) { 

    	for (int j=0; j<row; j++) {
    		System.out.format(spaces);
    		for(int k=0; k<col; k++) {
    			if (k!=col-1)
					System.out.print(" " + board[j][k] + " " + "|" );
				else
	    			System.out.println(" " + board[j][k] + " ");
    		}
    		System.out.format(spaces);
    		if (j!=row-1) 
                System.out.println("---+---+---");
    		else  
    			System.out.println();
    	}
    }
  

    /* Convert index of 2D array to integer position.
     * (j*3)+k is the conversion formula.
     */
    public int getNumber(int j, int k) {
    	if (j<=3 && j>=0 && k<=3 && k>=0 )
    		return (j*3)+k;
    	else 
    		return -1;
	}

	// Prints out board with internal borders and with numbers at empty positions, e.g.
    // X | 1 | O 
    //---+---+---
    // O | O | 5 
    //---+---+---
    // O | 7 | X 
    void printWithNumbers() {
    	for (int j=0; j<row; j++) {
    		for(int k=0; k<col; k++) {
    			if (board[j][k] == EMPTY) {
    				if (k!=col-1)
    					System.out.print(" " + getNumber(j,k) + " " + "|");
    				else
    	    			System.out.println(" " + getNumber(j,k) + " ");
    			}
    			else {
    				if (k!=col-1)
        				System.out.print(" " + board[j][k] + " " + "|");
        			else
        				System.out.println(" " + board[j][k] + " ");
    			}
    		}
    		if (j!=row-1) 
                System.out.println("---+---+---");
    		else  
    			System.out.println();
    		}
    }

    // Only to test this class
    private void printOutcome() {
        if (isWinForX())
            System.out.println("X wins");
        if (isWinForO())
            System.out.println("O wins");
        if (isDraw())
            System.out.println("Is a draw");
    }

    public static void main(String[] args) {

    	//Creates empty board
        Board board1 = new Board();
        System.out.println("Prints an empty board:");
        System.out.println("Number of Occupied: " + board1.numberOfOccupied);

        board1.print();
        
        // Prints board with position index
        System.out.println("Print board with position index:");
        board1.printWithNumbers();

        // Move 
        System.out.println("Place O in position 2");
        board1 = board1.move(2, O_TOKEN);
        System.out.println("Number of Occupied: " + board1.numberOfOccupied);
        board1.print();
        board1.printOutcome();
        System.out.println("Place X in position 0");
        board1 = board1.move(0, X_TOKEN);
        System.out.println("Number of Occupied: " + board1.numberOfOccupied);
        board1.print();
        
        // Prints board with position index
        System.out.println("Prints board with position index:");
        board1.printWithNumbers();

        // Creates 3x3 board X X O
        //                   O O X
        //                   O O X
        board1 = new Board("XXOOOXOX ");
        System.out.println("Print board with string 'XXOOOXOX ':");
        System.out.println("Number of Occupied: " + board1.numberOfOccupied);
        board1.print();
        board1.printOutcome();

        
        // Print "Position i contains X/O/EMPTY"
        for (int i = 0; i < 9; i++) {
            System.out.println("Position " + i + " contains " + board1.getPos(i));
        }
        System.out.println();
        
        // Creates board and puts string in it if called as follows:
        // java Board string
        if (args.length < 1)
            return;
        if (args.length > 1)
        	throw new IllegalArgumentException("Put ' ' if there are spaces within your input!");
        
        // Prints board from input.
        System.out.println("Prints board from input:");
        board1 = new Board(args[0]);
        System.out.println("Input is: " + args[0]);
        System.out.println("Number of Occupied: " + board1.numberOfOccupied);
        board1.print();
        board1.printOutcome();
        
        System.out.print("Is game over? ");
        System.out.println(board1.isGameOver());
    }

}