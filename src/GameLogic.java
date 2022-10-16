//TODO change this logic: it should check if with the surrounding cells of the last placed piece, the player can win
// (This to improve the implementation)

public class GameLogic {

    public static int checkWin(BoardSpace[][] b){
        int win = checkHorizontalWin(b);
        if(win != 0)
            return win;

        win = checkVerticalWin(b);
        if(win != 0)
            return win;

        win = checkDiagonalsWin(b);
        if(win != 0)
            return win;

        return 0;
    }

    public static int checkHorizontalWin(BoardSpace[][] board){
        for(int c = 0; c < 3; c++){
            if(board[0][c].getValue() != 0 &&
                    board[0][c].getValue() == board[1][c].getValue() &&
                    board[1][c].getValue() == board[2][c].getValue()){
                System.out.println("hor: " + board[0][c].getValue());
                return board[0][c].getValue();
            }
        }
        return 0;
    }

    public static int checkVerticalWin(BoardSpace[][] board){
        for(int r = 0; r < 3; r++){
            if(board[r][0].getValue() != 0 &&
                    board[r][0].getValue() == board[r][1].getValue() &&
                    board[r][1].getValue() == board[r][2].getValue()){
                System.out.println("vert: " + board[r][0].getValue());
                return board[r][0].getValue();
            }
        }
        return 0;
    }

    public static int checkDiagonalsWin(BoardSpace[][] board){
        if(board[0][0].getValue() != 0 &&
                board[0][0].getValue() == board[1][1].getValue() &&
                board[1][1].getValue() == board[2][2].getValue()){
            System.out.println("neg. diag: " + board[0][0].getValue());
            return board[0][0].getValue();
        }
        else if(board[0][2].getValue() != 0 &&
                board[0][2].getValue() == board[1][1].getValue() &&
                board[1][1].getValue() == board[2][0].getValue()){
            System.out.println("pos. diag: " + board[0][2].getValue());
            return board[0][2].getValue();
        }
        return 0;
    }

}
