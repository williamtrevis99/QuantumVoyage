package app;

import javafx.geometry.Point2D;

import java.io.Serializable;

import app.droneObject;

/**
 * Player class, which extends abstract class droneObject allows for a player to
 * be created, which then inherits various functions.
 */
public class Player extends droneObject implements Serializable {

    public double speed = 0, maxSpeed, rotSpeed;

    /**
     * Class constructor which initialises values and calls super functions.
     * 
     * 
     * @param a
     * @param b
     * @param c
     * @param d
     * @param maxSpeed
     */

    Player(double a, double b, double c, double d, double maxSpeed) {

        super("app/assets/images/ship1.png", a, b, c, d);

        entity.setTranslateX(400);
        entity.setTranslateY(400);
        droneHealth = 100;
        droneKills = 0;
        this.maxSpeed = maxSpeed;
    }

    /**
     * Overriden function which moves the player object in a diffirent way to all
     * droneObjects
     */

    @Override
    protected void moveUp() {
        isInArena();
        this.entity.setRotate(getRotate() + rotSpeed);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        this.entity.setTranslateY(entity.getTranslateY() + velocity.getY() * speed);
        this.entity.setTranslateX(entity.getTranslateX() + velocity.getX() * speed);

    }
}
