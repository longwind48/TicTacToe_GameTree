// Candidate Number: 91755
public class TreeNode {
	
	private Board boardPos;	// Board position
	private char nextPlayer; // The player that moves next
	private char winningPlayer; // The player with winning position
	private int winningMove; // Winning move for the node
	private TreeNode[] children = new TreeNode[9]; // An object array: children

    // Constructor: Creates a TreeNode w/o initialising any instance variables.
    public TreeNode() {
    }
    
    // Sets the board stored in the node
    public void setBoard(Board board) {
    	boardPos = board;
    }

    // Returns the board stored in the node
    public Board getBoard() {
    	return boardPos;
    }

    // Sets who has the next turn for this position, X or O,
    // (depends on the rule for the game)
    public void setNextTurn(char nextTurn) {
    	nextPlayer = nextTurn;
    }

    // Returns X or O depending on who has the next turn for this position
    // (depends on the rule for the game)
    public char getNextTurn() {
    	return nextPlayer;
    }

    // Sets for which player this is a winning position
    // (X or O or ' ' if a draw)
    public void setWinFor(char winFor) {
    	winningPlayer = winFor;
    }

    // Returns X if this position is a win for X,
    // O if a winning position for O,
    // ' ' if a draw
    public char getWinFor() {
    	return winningPlayer;
    }

    // Sets the winning or drawing move to be made if this position is
    // a winning or drawing move for the player who has the next turn
    public void setNextMove(int nextMove) {
    	winningMove = nextMove;
    }

    // Returns the winning or drawing move to be made if this position is
    // a winning or drawing move for the player who has the next turn
    public int getNextMove() {
    	return winningMove;
    }

    // Sets the child node corresponding to next move pos
    public void setChild(int pos, TreeNode child) {
    	this.children[pos] = child;
    }

    // Returns the child node corresponding to next move pos
    public TreeNode getChild(int pos) {
    	return children[pos];
    }
}
