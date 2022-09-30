public class OpponentLogic {

    /**
     * table to keep track of the possible wins:
     *      0: empty cell
     *      1: occupied by x
     *      2: occupied by o
     *      3: possible win for x
     */
    private static int[][] possibleWins = new int[3][3];

    /**
     * getOpponentsMove: main class that will have all the functions for the logic of the opponent's move
     * @param row: the row where the player placed their piece
     * @param col: the column where the player placed their piece
     * @param board: the board form the Board file
     * @return int[]: returns the row and column that the computer should place its piece in the format of an array
     *                [row, column]
     */
    public static int[] getOpponentsMove(int row, int col, BoardSpace[][] board){

        int [] coordinate = coverOpponentMove(row, col, board);
        if(coordinate[0]!=-1){
            return coordinate;
        }

        // Finds the first empty slot starting at the top left
        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                System.out.println("Opponent:   row: " + r + ", col: " + c);
                if(board[r][c].getValue()==0) {
                    coordinate[0] = r;
                    coordinate[1] = c;
                    return coordinate;
                }
            }
        }
        return coordinate;
    }

    /**
     *  THIS IS USELESS AT THE TIME
     * addPiece: used to add the piece that the player placed on the board to the possible win board
     * @param r: row
     * @param c: column
     */
    public static void addPiece(int r,  int c){
        possibleWins[r][c] = 1;
    }

    /**
     * coverOpponentMove: this function returns the position the computer should input prevent the player from winning
     * @param row: row that the user placed the piece
     * @param col: column that the user placed the piece
     * @param board: board from Board file to check what places are empty
     * @return int[]: the row and column in the format of an array: [row, column]
     */
    public static int[] coverOpponentMove(int row, int col, BoardSpace[][] board){
        // the position is the position that the opponent will place its piece
        int[] position = new int[2];
        position[0] = -1;
        position[1] = -1;

        /**
         * if the player's position is at the center row, check if you need to cover a horizontal win
         */

        // if the player places a piece at the top row, check the lower two rows for a win
        if(row == 0){
            //Check if the middle position is empty and the bottom one is taken. If so, cover middle
            if(board[1][col].getValue() == 0 && board[2][col].getValue() == 1){
                position[0] = 1;
                position[1] = col;
                System.out.println("Cover a vertical from top placement of player (middle empty)");
                return position;
            }else if(board[1][col].getValue() == 1 && board[2][col].getValue() == 0){
                position[0] = 2;
                position[1] = col;
                System.out.println("Cover a vertical from top placement of player (bottom empty)");
                return position;
            }
        }
        // If the player places a piece at the bottom row, check the upper two rows for a win
        else if(row == 2){
            //Check if the middle position is empty and the bottom one is taken. If so, cover middle
            if(board[1][col].getValue() == 0 && board[0][col].getValue() == 1){
                position[0] = 1;
                position[1] = col;
                System.out.println("Cover a vertical from bottom placement of player (middle empty)");
                return position;
            }else if(board[1][col].getValue() == 1 && board[0][col].getValue() == 0){
                position[0] = 0;
                position[1] = col;
                System.out.println("Cover a vertical from bottom placement of player (top empty)");
                return position;
            }
        }

        // If the player place a piece at the far left column, check columns to the right for a win
        if(col == 0){
            if(board[row][1].getValue() == 0 && board[row][2].getValue() == 1){
                position[0] = row;
                position[1] = 1;
                System.out.println("Cover a horizontal from left placement of player (middle empty)");
                return position;
            }else if(board[row][1].getValue() == 1 && board[row][2].getValue() == 0){
                position[0] = row;
                position[1] = 2;
                System.out.println("Cover a horizontal from left placement of player (right empty)");
                return position;
            }
        }
        // If the player places a piece at the far right column, check columns to the left for a win
        else if(col == 2){
            if(board[row][1].getValue() == 0 && board[row][0].getValue() == 1){
                position[0] = row;
                position[1] = 1;
                System.out.println("Cover a horizontal from left placement of player (middle empty)");
                return position;
            }else if(board[row][1].getValue() == 1 && board[row][0].getValue() == 0){
                position[0] = row;
                position[1] = 0;
                System.out.println("Cover a horizontal from left placement of player (left empty)");
                return position;
            }
        }

        // If the player places a piece at the center column, check the position to the sides for a win
        if(col==1){
            if(board[row][col - 1].getValue() == 1 && board[row][col + 1].getValue() == 0){
                position[0] = row;
                position[1] = col+1;
                System.out.println("Cover a horizontal");
                return position;
            }else if(board[row][col - 1].getValue() == 0 && board[row][col + 1].getValue() == 1){
                position[0] = row;
                position[1] = 0;
                System.out.println("Cover a horizontal");
                return position;
            }
        }

        // If the player places a piece at center row, check position up and down for a win
        if(row == 1){
            if(board[row - 1][col].getValue() == 1 && board[row + 1][col].getValue() == 0){
                position[0] = row + 1;
                position[1] = col;
                System.out.println("Cover a vertical");
                return position;
            }else if(board[row - 1][col].getValue() == 0 && board[row + 1][col].getValue() == 1){
                position[0]=0;
                position[1] = col;
                System.out.println("Cover a vertical");
                return position;
            }
        }

        // If the player's piece is at the center position, check if she/he can win with the diagonals
        if(row == 1 && col == 1){
            //Check positive diagonal (/)
            if(board[0][2].getValue() == 0 && board[2][0].getValue() == 1){
                position[0] = 0;
                position[1] = 2;
                System.out.println("Cover top right, form positive diagonal win");
                return position;
            }else if(board[0][2].getValue() == 1 && board[2][0].getValue() == 0){
                position[0] = 2;
                position[1] = 1;
                System.out.println("Cover bottom left, form positive diagonal win");
                return position;
            }
            // Check the negative diagonal (\)
            else if(board[0][0].getValue() == 0 && board[2][2].getValue() == 1){
                position[0]=0;
                position[1]=0;
                System.out.println("Cover the top left, form a negative diagonal win");
                return position;
            }else if(board[0][0].getValue() == 1 && board[2][2].getValue() == 0){
                position[0]=2;
                position[1]=2;
                System.out.println("Cover the bottom right, from a negative diagonal win");
                return position;
            }
        }

        if(row == 0 && col == 0){
            //Check negative diagonal (\)
            if(board[1][1].getValue() == 0 && board[2][2].getValue() == 1){
                position[0] = 1;
                position[1] = 1;
                System.out.println("Cover middle, form negative diagonal win with place at top left");
                return position;
            }else if(board[1][1].getValue() == 1 && board[2][2].getValue() == 0){
                position[0] = 1;
                position[1] = 1;
                System.out.println("Cover bottom right, form negative diagonal win with a place at top left");
                return position;
            }
        }else if(row == 2 && col == 2){
            //Check negative diagonal (\)
            if(board[1][1].getValue() == 0 && board[0][0].getValue() == 1){
                position[0] = 1;
                position[1] = 1;
                System.out.println("Cover middle, form negative diagonal win with place at bottom right");
                return position;
            }else if(board[1][1].getValue() == 1 && board[0][0].getValue() == 0){
                position[0] = 0;
                position[1] = 0;
                System.out.println("Cover top left, form negative diagonal win with a place at bottom right");
                return position;
            }
        }else if(row == 0 && col == 2){
            //Check positive diagonal (/)
            if(board[1][1].getValue() == 0 && board[2][0].getValue() == 1){
                position[0] = 1;
                position[1] = 1;
                System.out.println("Cover middle, form positive diagonal win with place at top right");
                return position;
            }else if(board[1][1].getValue() == 1 && board[2][0].getValue() == 0){
                position[0] = 2;
                position[1] = 0;
                System.out.println("Cover bottom left, form positive diagonal win with a place at top right");
                return position;
            }
        }else if(row == 2 && col == 0){
            //Check positive diagonal (/)
            if(board[1][1].getValue() == 0 && board[0][2].getValue() == 1){
                position[0] = 1;
                position[1] = 1;
                System.out.println("Cover middle, form positive diagonal win with place at bottom right");
                return position;
            }else if(board[1][1].getValue() == 1 && board[0][2].getValue() == 0){
                position[0] = 0;
                position[1] = 2;
                System.out.println("Cover top right, form positive diagonal win with a place at bottom right");
                return position;
            }
        }

        return position;
    }

    //TODO: make a function that will anticipate the player's move

}