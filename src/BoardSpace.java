import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.Group;
import javafx.scene.paint.Paint;

public class BoardSpace {

    /**
     * This will store the value of the space
     *  0 = empty
     *  1 = x
     *  2 = o
     */
    private int value = 0;
    private Group image;
    private double xPos;
    private double yPos;

    public BoardSpace(double x, double y){
        value = 0;
        image = new Group();
        xPos = x;
        yPos = y;
    }

    public BoardSpace(int val, double x, double y){
        value = val;
        xPos = x;
        yPos = y;
        if(value == 1){
            image = cross();
        }else{
            image = circle();
        }
    }

    public int getValue(){return value;}
    public Group getImage(){return image;}
    public Group circle(){
        Circle c = new Circle(xPos, yPos, 50);
        c.setFill(Paint.valueOf("#00f"));
        Group g = new Group(c);
        return g;
    }

    public Group cross(){

        Line l1 = new Line(xPos-25, yPos-25, xPos+25, yPos+25);
        l1.setStrokeWidth(20);
        l1.setStroke(Paint.valueOf("#f00"));

        Line l2 = new Line(xPos-25, yPos+25, xPos+25, yPos-25);
        l2.setStrokeWidth(20);
        l2.setStroke(Paint.valueOf("#f00"));

        Group cross = new Group(l1, l2);
        return cross;
    }
}
