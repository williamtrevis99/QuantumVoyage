package app;

import java.io.Serializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * The bullet class allows for a bullet 
 * 
 * object to be created.
 */

public class Bullet implements Serializable{

    
    double xpos;
    double ypos;
    Point2D velocity;

    transient Image image = new Image("app/assets/images/missile.png", 20, 20, true, false);
    transient ImageView ship;

    /**
     * Class constructor
     */

    Bullet() {

        ship = new ImageView(image);

    }
    /**
     * Method Initialises variables
     * @param xPos
     * @param yPos
     * @param v
     * @param rotate
     */
    public void init(double xPos, double yPos, Point2D v, double rotate) {

        ship.setTranslateX(xPos);
        ship.setTranslateY(yPos);
        ship.setRotate(rotate);
        velocity = v;

        xpos = xPos;
        ypos = yPos;

        

    }
    /**
     * Method returns the bullet
     * @return
     */
    Node getBullet() {
        
        return ship;
    }

    

}