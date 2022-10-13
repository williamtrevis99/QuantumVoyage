package app;

import java.io.Serializable;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 * Class entity which all objects use, this class provides the base
 * features/functionality
 */

public abstract class Entity implements Serializable{

    transient Image image;
    transient ImageView eimage;
    protected Node entity;
    transient Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

    private double maxUp = 0;
    private double maxDown = visualBounds.getHeight() - 45;
    private double maxRight = visualBounds.getWidth() - 90;
    private double maxLeft = 0;

    protected double multiplier;
    protected Point2D velocity;
    protected final double radius;

    transient Text positionalData;

    double currentXpos;
    double currentYpos;

    /**
     * Class constructor to initialize declared variables.
     * 
     * @param entityImage
     * entityImage is a string url to the image of the object.
     * @param x
     * is the x size of the object
     * @param y
     * is the y size of the object
     *
     *
     */

    Entity(String entityImage, int x, int y) {

        image = new Image(entityImage, x, y, true, false);
        eimage = new ImageView(image);
        entity = eimage;
        multiplier = 3;

        entity.setTranslateX(randomX());
        entity.setTranslateY(randomY());

        currentXpos = entity.getTranslateX();
        currentYpos = entity.getTranslateY();

        positionalData = new Text("gfhjdjf");
        
        radius = this.image.getHeight() / 2;

    }

    /**
     * Overloaded constructor for diffirent entities
     * 
     * @param entityImage
     * Is the string address to the image.
     */
    Entity(String entityImage) {

        image = new Image(entityImage, 25, 25, true, false);
        eimage = new ImageView(image);
        entity = eimage;
        multiplier = 3;

        entity.setTranslateX(randomX());
        entity.setTranslateY(0);

        radius = this.image.getHeight() / 2;

    }

    /**
     * Function returns a random X position within certain bounds.
     * 
     * @return
     * returns a random number between high and low values.
     */

    private int randomX() {

        Random r = new Random();

        int high = (int) visualBounds.getWidth() - 150;
        int low = 150;

        return r.nextInt(high - low) + low;

    }

    /**
     * Function returns a random Y position within certain bounds.
     * 
     * @return
     * returns a random number between high and low values.
     */

    private int randomY() {

        Random r = new Random();

        int high = (int) visualBounds.getHeight() - 150;
        int low = 150;

        return r.nextInt(high - low) + low;

    }



    /**
     * Returns the entity
     * 
     * @return Node
     */

    public Node getEntity() {

        return entity;
    }

    /**
     * Returns current rotation
     * 
     * @return
     * Returns the current rotation of the entity
     */


    public double getRotate() {
        return entity.getRotate();
    }

    /**
     * Sets velocity
     * 
     * @param velocity
     * Returns the Velocity of the entity.
     */

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets velocity
     * 
     * @return
     *
     */

    public Point2D getVelocity() {
        return velocity;
    }

    /**
     * Rotates entity
     */

    public void rotateRight() {
        entity.setRotate(entity.getRotate() + 20);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    /**
     * Rotates entity to a right angle
     */

    public void rotateRightAngle() {
        entity.setRotate(entity.getRotate() + 90);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    /**
     * Rotates entity left
     */

    public void rotateLeft() {
        entity.setRotate(entity.getRotate() - 20);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    /**
     * Reverses entity
     */

    public void reverse() {
        entity.setRotate(entity.getRotate() - 180);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));

    }

    /**
     * Keeps the entity in the arena
     */

    protected void isInArena() {

        if (this.entity.getTranslateY() < maxUp) {

            this.entity.setTranslateY(maxUp);
        }
        if (this.entity.getTranslateY() + 2 * radius > maxDown) {

            this.entity.setTranslateY(maxDown - 2 * radius);
        }
        if (this.entity.getTranslateX() < maxLeft) {

            this.entity.setTranslateX(1);
        }
        if (this.entity.getTranslateX() + 2 * radius > maxRight) {

            this.entity.setTranslateX(maxRight - 2 * radius);
        }

    }

    /**
     * Returns the centre X,Y position of the entity
     * 
     * @return
     * returns the Point2D object.
     */

    protected Point<Double> getCenter() {
        Point<Double> point = new Point<Double>();
        javafx.geometry.Bounds bounds = this.entity.getBoundsInParent();
        point.x = (bounds.getMaxX() + bounds.getMinX()) / 2.0;
        point.y = (bounds.getMaxY() + bounds.getMinY()) / 2.0;

        return point;
    }

    /**
     * Moves the entity forward relative to its rotation
     */

    protected void moveUp() {

        isInArena();

        this.entity.setTranslateX(entity.getTranslateX() + velocity.getX() * multiplier);
        this.entity.setTranslateY(entity.getTranslateY() + velocity.getY() * multiplier);

    }

    protected void moveDown() {

        this.entity.setTranslateX(entity.getTranslateX() + velocity.getX() * multiplier);
        this.entity.setTranslateY(entity.getTranslateY() + velocity.getY() * multiplier);

    }

    /**
     * Moves entity
     */

    protected void moveEntity() {

        if (this.entity.getTranslateY() < maxUp) {

            this.entity.setTranslateY(maxUp);
            entity.setRotate(entity.getRotate() - 180);
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        }
        if (this.entity.getTranslateY() + 2 * radius > maxDown) {

            this.entity.setTranslateY(maxDown - 2 * radius);
            entity.setRotate(entity.getRotate() - 180);
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        }

        if (this.entity.getTranslateX() < maxLeft) {

            this.entity.setTranslateX(1);
            entity.setRotate(entity.getRotate() - 180);
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        }
        if (this.entity.getTranslateX() + 2 * radius > maxRight) {

            this.entity.setTranslateX(maxRight - 2 * radius);
            entity.setRotate(entity.getRotate() - 180);
            setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        }

        this.entity.setTranslateX(entity.getTranslateX() + velocity.getX() * multiplier);
        this.entity.setTranslateY(entity.getTranslateY() + velocity.getY() * multiplier);

    }

    /**
     * Adjusts rotation and velocity of entity
     */

    protected void destroyCollision() {

        Random r = new Random();
        int random = r.nextInt(180);

        entity.setRotate(entity.getRotate() + random);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));

    }



}