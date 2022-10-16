import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.event.EventHandler;

// Make sure you extend application, because this is the entry method for JavaFX
public class Board extends Application{

    /**
     *  board: a 2D array that holds the pieces of the board
     *  playerTurn: boolean that keeps track of whose turn it is
     *  everything: global JavaFX Group object to be able to change the application in the event of a click
     */
    private BoardSpace[][] board = new BoardSpace[3][3];

    // TODO: create a function that stops all action in the app except for a button that will reset the game
    private boolean gameEnded = true;
    private Group everything;

    /**
     * main: launches the application
     * @param args
     */
    public static void main(String[]args){
        launch(args);
    }

    /**
     * start: executes the basic operations to initialize the applicatioon
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set. The primary stage will be embedded in
     * the browser if the application was launched as an applet.
     * Applications may create other stages, if needed, but they will not be
     * primary stages and will not be embedded in the browser.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        // Create the group where the elements will be placed
        everything = new Group(setUpGame());

        // Creates the Scene where the group will be placed
        Scene board = new Scene(everything);
        board.setOnMouseClicked(new playerClicks());
        //board.setOnMouseClicked(new playerClicks());

        primaryStage.setScene(board);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setHeight(800);
        primaryStage.setWidth(1000);
        primaryStage.show();
    }

    /**
     * setUpGame: sets up the board to begin playing by:
     *      drawing the lines in the application
     *      setting all the pieces on the board to "empty"
     * @return
     */
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
     * This returns the spaces where the user places his/her position bases on the x- and y-coordinates of the click
     * @param xPos: x-coordinate of the click on the screen
     * @param yPos: y-coordinate of the click on the screen
     * @return int[]:   the first value of the list being the row on the board where the piece is to be placed
     *                  the second value is the same, but it represents the column on the board
     */
    public int[] getPositionOfClick(double xPos, double yPos){
        int[] coordinate = new int[2];
        coordinate[0] = -1;
        coordinate[1] = -1;

        if(xPos < 200 || xPos > 800 || yPos < 50 || yPos > 650){
            System.out.println("Do nothing, he/she clicked outside of the board!");
            return coordinate;
        }else{
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
     * Adds a piece of the given value and coordinate to the global variable "board"
     * @param row: row position on the board for the piece to be plsced
     * @param col: column position on the board for the piece to be placed
     * @param pieceValue: the value of the piece (1 = cross, 2 = circle)
     * Function returns true if the position can be used, otherwise false
     */
    public boolean addPiece(int row, int col, int pieceValue){

        if(row != -1 && col != -1 && board[row][col].getValue() == 0){
            board[row][col] = new BoardSpace(pieceValue, 300 + 200*col, 150 + 200*row);
            everything.getChildren().add(board[row][col].getImage());
            //OpponentLogic.addPiece(row, col);
            return true;
        }

        return false;
    }

    /**
     * playerClicks class: event handler for the event of the user clicking the screen
     */
    public class playerClicks implements EventHandler<MouseEvent> {

        //static boolean clickedInBoard = false;
        @Override
        public void handle(MouseEvent event) {

            // Gets the user's coordinates and translates them to the board's coordinate
            int[] userPosition = getPositionOfClick(event.getSceneX(), event.getSceneY());

            if(userPosition[0] == -1){
                System.out.println("Clicked out side of the board.");
                return;
            }

            // If the position is valid, it adds the user's move the board
            if(addPiece(userPosition[0], userPosition[1], 1)) {

                //Checks if the player won
                if (GameLogic.checkWin(board) != 0) {
                    System.out.println("Player " + GameLogic.checkWin(board) + " won!");
                    return;
                }

                // Gets the opponent's move
                int[] opponentPosition = OpponentLogic.getOpponentsMove(userPosition[0], userPosition[1], board);
                System.out.println("The opponent's position: " + opponentPosition[0] + " " + opponentPosition[1]);

                // Adds the opponent's move to the board
                addPiece(opponentPosition[0], opponentPosition[1], 2);
            }

        }
    }

}
