package app;

import java.io.Serializable;
import java.util.Random;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;

/**
 * This class represents an enemy drone.
 */
public class Enemy extends Circle implements Serializable{

    int maxUp;
    int maxDown;
    int maxLeft;
    int maxRight;

    transient Text positionalData;

    public float speed = 600;

    transient Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    transient Image image = new Image("app/assets/images/enemy.png", 40, 40, true, false);
    transient ImageView enemyShip;
    transient PathTransition pathTransition;

    /**
     * Class constructor which initializes the radius of the enemy and color.
     * 
     * @param a
     * The radius of the circle.
     * @param e
     * The colour of the circle.
     */

    Enemy(int a, Color e) {

        

        super(a, e);
        enemyShip = new ImageView(image);

        setTranslateX(randomX());
        setTranslateY(randomY());

        positionalData = new Text();

    }

    /**
     * Function to return enemy Image
     * 
     * @return
     * returns enemy ship object
     */

    ImageView getEnemy() {

        return enemyShip;
    }



    public void moveEnemy(Enemy enemy, double x, double y) {

        Path path = new Path();
        path.getElements().add(new MoveTo(enemy.getTranslateX(), enemy.getTranslateY()));
        path.getElements().add(new CubicCurveTo(x, y, x, y, x, y));
        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(speed));
        pathTransition.setPath(path);
        pathTransition.setNode(enemy);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.play();

    }

    /**
     * Function to return random X value in the canvas
     * 
     * @return
     * Returns random x position
     */

    private int randomX() {

        Random r = new Random();

        int high = (int) visualBounds.getWidth();
        int low = 0;

        return r.nextInt(high - low) + low;

    }

    /**
     *
     * @return
     * Returns random Y position
     */

    private int randomY() {

        Random r = new Random();

        int high = (int) visualBounds.getHeight();
        int low = 0;

        return r.nextInt(high - low) + low;

    }

}