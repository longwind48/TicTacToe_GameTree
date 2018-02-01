// Candidate Number: 91755
// Usage:
// java NoughtsAndCrosses string
// where string consists of a string of X and O.
// Determines the game tree of noughts and crosses played 
// according to the order of turns given by
// string. Then plays such a game with the user.
// Example:
// java NoughtsAndCrosses XOXOXOXOX
// plays the standard Noughts and Crosses game, started by X.
// java NoughtsAndCrosses XOOXOXOXO
// plays a variant starting with X, then O gets
// two turns, then afterwards it alternates.

import java.util.Scanner;

public class NoughtsAndCrosses {

    public static void main(String[] args) {

        if (args.length < 1) {
            return;
        }
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
        //gameTree.print(); 
        gameTree.printNode(gameTree.root);

        Scanner scan = new Scanner(System.in);
        System.out.print("Would you like to play a game? (y/n) ");
        while (scan.next().charAt(0) == 'y') {
            System.out.print("Would you like to play X or O? (X/O) ");
            char user = scan.next().charAt(0);
            TreeNode currentNode = gameTree.root;
            int turnNumber = 0;
            while (currentNode != null && !currentNode.getBoard().isGameOver()) {
                if (gameTree.turn[turnNumber] == user) {
                    currentNode.getBoard().printWithNumbers();
                    System.out.print("Position? ");
                    int pos = Character.getNumericValue(scan.next().charAt(0));
                    while (currentNode.getBoard().isOccupied(pos)) {
                        System.out.print("Already taken. Position? ");
                        pos = Character.getNumericValue(scan.next().charAt(0));
                    }
                    currentNode = currentNode.getChild(pos);
                } else {
                    currentNode = currentNode.getChild(currentNode.getNextMove());
                }
                turnNumber++;
            }
            currentNode.getBoard().print();
            if (currentNode.getBoard().isWinFor(user))
                System.out.println("You won!");
            else if (currentNode.getBoard().isDraw())
                System.out.println("It's a draw.");
            else
                System.out.println("I won!");
            System.out.print("Would you like to play another game? (y/n)");
        }
    }
}
