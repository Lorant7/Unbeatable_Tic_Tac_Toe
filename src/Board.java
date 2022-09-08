import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.event.EventHandler;

// Make sure you extend application, because this is the entry method for JavaFX
public class Board extends Application{

    private BoardSpace[][] board = new BoardSpace[3][3];

    // true = player 1; false = player 2
    private boolean playerTurn = true;
    private Group everything;


    public static void main(String[]args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        everything = new Group(setUpGame());

        Scene board = new Scene(everything);
        board.setOnMouseClicked(new playerClicks());

        primaryStage.setScene(board);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setHeight(800);
        primaryStage.setWidth(1000);
        primaryStage.show();
    }

    public Group setUpGame(){
        /**
            This sets up the lines for the board
                Their positions
                Their widths
         */
        Line left = new Line(400,50,400,650);
        left.setStrokeWidth(10);

        Line right = new Line(600,50,600,650);
        right.setStrokeWidth(10);

        Line top = new Line(200,250,800,250);
        top.setStrokeWidth(10);

        Line bottom = new Line(200,450,800,450);
        bottom.setStrokeWidth(10);

        Group spaces = new Group();
        for(int r = 0; r < 3; r++){
            for(int c = 0; c < 3; c++){
                board[r][c] = new BoardSpace(325 + c*175, 175 + r*175);
                spaces.getChildren().add(board[r][c].getImage());
            }
        }

        /*
            Add all the lines and shapes to the group so it can be added to the scene
         */
        Group g = new Group(left, right, top, bottom, spaces);
        return g;
    }

    /**
     * This returns the spaces where the user places his/her position
     * @param xPos
     * @param yPos
     * @return
     */
    public int[] getPositionOfClick(double xPos, double yPos){
        int[] coordinate = new int[2];
        coordinate[0] = -1;
        coordinate[1] = -1;

        if(xPos < 200 || xPos > 800 || yPos < 50 || yPos > 650){
            System.out.println("Do nothing, he/she clicked outside of the board!");
            return coordinate;
        }else{
            /**
             * This set of if-statements checks the point where the click was made in the following order:
             *      What column it was done
             *      What row it was done
             *      If the space is being used
             */
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    if(xPos < 400 + 200*c){
                        if(yPos < 250 + 200*r){
                            coordinate[0] = r;
                            coordinate[1] = c;
                            System.out.println("row: " + r + ", col: " + c);
                            return coordinate;
                        }
                    }
                }
            }
        }


        return coordinate;
    }

    /**
     * Adds a piece of the given value and coordinate to the board
     * @param row
     * @param col
     * @param pieceValue
     */
    public void addPiece(int row, int col, int pieceValue){

        if(row != -1 && col != -1 && board[row][col].getValue() == 0){
            board[row][col] = new BoardSpace(pieceValue, 300 + 200*col, 150 + 200*row);
            everything.getChildren().add(board[row][col].getImage());
            OpponentLogic.addPiece(row, col);
        }

    }
    public class playerClicks implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            // Gets the user's coordinates and translates them to the board's coordinate
            int[] userPosition = getPositionOfClick(event.getSceneX(), event.getSceneY());

            while(board[userPosition[0]][userPosition[1]].getValue() != 0){
                userPosition = getPositionOfClick(event.getSceneX(), event.getSceneY());
            }
            // Adds the user's move the board
            addPiece(userPosition[0], userPosition[1], 1);

            //Checks if the player won
            if(GameLogic.checkWin(board) != 0)
                System.out.println("Player " + GameLogic.checkWin(board) + " won!");

            /**
            // Gets the opponent's move
            int[] opponentPosition = OpponentLogic.getOpponentsMove(userPosition[0], userPosition[1], board);

            // Adds the opponent's move to the board
            addPiece(opponentPosition[0], opponentPosition[1], 2);
             */
        }
    }

}
