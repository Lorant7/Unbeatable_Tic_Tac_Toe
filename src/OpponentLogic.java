public class OpponentLogic {

    /**
     * table to keep track of the possible wins:
     *      0: empty cell
     *      1: occupied by x
     *      2: occupied by o
     *      3: possible win for x
     */
    private static int[][] possibleWins = new int[3][3];
    public static int[] getOpponentsMove(int row, int col, BoardSpace[][] board){


        int [] coordinate = coverOpponentMove(row, col, board);
        if(coordinate[0]!=-1){
            return coordinate;
        }
        coordinate[0]=0;
        coordinate[1]=0;
        while(board[coordinate[0]][coordinate[0]].getValue() != 0){
            coordinate[0] = (int)(Math.random()*3);
            coordinate[1] = (int)(Math.random()*3);
        }
        System.out.println("Random piece at: (" +coordinate[0] + ", " + coordinate[1]);

        return coordinate;
    }

    /**
     * this method is used to add the piece that the player placed on the board to the possible win board
     * @param r
     * @param c
     */
    public static void addPiece(int r,  int c){
        possibleWins[r][c] = 1;
    }

    public static int[] coverOpponentMove(int row, int col, BoardSpace[][] board){
        int[] position = new int[2];
        position[0] = -1;
        position[1] = -1;

        /**
         * if the player's position is at the center row, check if you need to cover a horizontal win
         */
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

        return position;
    }

}


