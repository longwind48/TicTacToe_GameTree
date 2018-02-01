// Candidate Number: 91755
public class GameTree { 
	
    public TreeNode root = null; // the root of the tree
    public char[] turn; // stores the rule according to how turns are made.
    public static final char player_X = 'X'; // Contains a character that represents X
    public static final char player_O = 'O'; // Contains a character that represents O
    public static final char None = ' ';
    public final int row = 3, col = 3;

	/* This constructs a GameTree which initialises the root node,
	 * and recursively constructs a game tree starting at root
	 * by using makeGameTreeAt()
	*/ 
	public GameTree(String turns) {
	    turn = turns.toCharArray();
	    root = new TreeNode(); 		//initialise the root node
	    Board e = new Board();
	    root.setBoard(e); 			//root's board is an empty board
	    root.setNextTurn(turn[0]);	//root's turn is first char of turns array
	    makeGameTreeAt(root);
	}
	
	/* This method recursively calls itself to construct a game tree
	 * starting from a given (param) node.
	 * 1) Stores which player has winning position for nodes that are game-over.
	 * 2) Create a child node for every empty position on the board, 
	 *    calling setBoard() and setChild() afterwards.
	 * 3) Then recursively calls itself for each new child.  
	 * 4) Checks whether the position is a winning or losing one or a draw, 
	 *	  and store this information in the node. 
	 */
	public void makeGameTreeAt(TreeNode node) {
		// 1) 
		// If the board at the node is already a win for X or O or a draw, 
		// stores this information in the node and return.
		if (node.getBoard().isWinFor(player_X)) {
			node.setWinFor(player_X);
			node.setNextTurn(None);
			node.setNextMove(-1);
			return;
		}
		else if (node.getBoard().isWinFor(player_O)) {
			node.setWinFor(player_O);
			node.setNextTurn(None);
			node.setNextMove(-1);
			return;
		}
		else if (node.getBoard().isDraw()) {
			node.setWinFor(None);
			node.setNextTurn(None);
			node.setNextMove(-1);
			return;
		}
		//If node's board is not game-over, 
		else {
			// 2)
			// Creates a child node for every empty position on the board, 
			// by checking if position is empty and if node's board is not game-over.
			for (int i=0; i<9; i++) {
				if (!node.getBoard().isOccupied(i) 
					&& !node.getBoard().isGameOver()) {
					
					TreeNode nn = new TreeNode();								// Creates a new child node
					nn.setBoard(node.getBoard().move(i, node.getNextTurn()));   // Sets the board for ever child
					
					// Call setNextTurn for each child node
					if (nn.getBoard().isGameOver()) 
						nn.setNextTurn(None);
					else
						nn.setNextTurn(getTurn(nn.getBoard().numberOfOccupied));
					
					// Now that we have the nodes, with boards and next turns set, 
					// we can set each of them to be the child of the given (param) node.
					node.setChild(i, nn);
					
					// 3)
					makeGameTreeAt(node.getChild(i));
					
					// 4)
					// Node is winning if next move is made by player, 
					// and at least one child of the node is winning for player.
					if (node.getNextTurn() == node.getChild(i).getWinFor()) {
						node.setWinFor(node.getNextTurn());
						node.setNextMove(i);
					}
					// if none of children is winning,
					else if (node.getNextTurn() != node.getWinFor()) {
						// Node is draw if next move is made by player, and at least one child is a draw
						if (node.getChild(i).getWinFor() == None) {
							node.setWinFor(None);
							node.setNextMove(i);
						}
						// Node is losing for X if none of its children are draw or winning for X
						else if (node.getWinFor() != None 
								&& node.getNextTurn() == player_X) {
							node.setNextMove(i);
							node.setWinFor(player_O);
						}
						// Node is losing for O if none of its children are draw or winning for O
						else if (node.getWinFor() !=None
								&& node.getNextTurn() == player_O) {
							node.setNextMove(i);
							node.setWinFor(player_X);
						}
					}
				}
			}
		}
	}

	/* Returns X or O giving which player has the next turn,
	 * according to the rule for the game. 
	 */
	public char getTurn(int n) {
		return turn[n];
	}

    /* This method returns the player that has the winning move at the root of the game tree.
     */
    public char winner() {
    	return root.getWinFor();
    }
    
    /* This method prints out the game tree, starting at the root of the game tree.
     */
    public void print() {
    	print(root);
    }
    
    /* This method prints out the game tree, starting at the given (param) node.
     */
	public void print(TreeNode node) { 
		
		String spaces = "";
		// Depending on the numberofOccupied, the spaces or (depth) will change.
		for (int j=0; j<node.getBoard().numberOfOccupied; j++) {
	    	spaces = spaces + "    ";
	    }
		node.getBoard().printWithIndent(spaces);
		
    	System.out.print(spaces);
    	printNode(node);

    	System.out.println();
    	
    	if (node.getBoard().isWinForX() 
    		|| node.getBoard().isWinForO()
    		|| node.getBoard().isDraw())
    		return;
    	else {
    		for (int i=0; i<9; i++) {
    			if (!node.getBoard().isOccupied(i)) {
    				print(node.getChild(i)); 		// Recursively prints the next child
    			}
    		}
    	}
	}
	
    public void printNode(TreeNode node) { 
        if (node == null) {
            return;
        }
        if (node.getNextTurn() == Board.EMPTY) {
            if (node.getWinFor() == None) 
                System.out.println("The game is a draw.");
            else 
                System.out.println(node.getWinFor() + " wins.");
        } 
        else if (node.getWinFor() == None) {
        	System.out.print(node.getNextTurn() + " can force a draw by playing ");
        	System.out.println(node.getNextMove() + ".");
        } 
        else if (node.getWinFor() == node.getNextTurn()) {
        	System.out.print(node.getWinFor() + " can force a win by playing ");
        	System.out.println(node.getNextMove() + ".");
        } 
        else
        	System.out.println(node.getNextTurn() + " cannot force a win or a draw.");
    }

	
	public static void main(String[] args) {
		if (args.length < 1)
			return;
        String turns = args[0];
        // Check if input string consist only of X and O
        for (int i = 0; i < turns.length(); i++) {
        	if (turns.charAt(i) != Board.X_TOKEN && turns.charAt(i) != Board.O_TOKEN) {
        		System.out.println("Invalid turns");
        		return;
        	}
        }
        if (turns.length() < 9) {
            // if turns too short, lengthen it by alternating from
            // last character. E.g. for a 3x3 board, XXO is lengthened
            // to XXOXOXOXO
        	char lastChar = Board.X_TOKEN;
        	if (turns.length() > 0) {
        		lastChar = turns.charAt(turns.length()-1);
        		for (int i = turns.length() - 1; i < 9; i++) {
        			if (lastChar == Board.X_TOKEN)
        				lastChar = Board.O_TOKEN;
        			else
        				lastChar = Board.X_TOKEN;
        			turns += lastChar;
        		}
        	}
        }
        System.out.println(turns);
        GameTree gameTree = new GameTree(turns);
        gameTree.print();
    }
}


