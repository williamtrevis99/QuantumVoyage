package app;

import java.io.Serializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract droneObject class which allows for all dronObjects to use the
 * encapsulated functions. Also extends certain features from entity.
 */

public abstract class droneObject extends Entity implements Serializable{

    transient Image image;
    transient ImageView ship;
    Integer droneHealth;
    Integer droneKills;
    protected final double radius;

    /**
     * Class constructor which initialised various values
     * 
     * 
     * @param objectImage
     * Url of image
     * @param a
     * @param b
     * @param c
     * @param d
     */

    droneObject(String objectImage, double a, double b, double c, double d) {

        super(objectImage, 80, 80);

        image = new Image(objectImage, 80, 80, true, false);
        ship = new ImageView(image);
        entity = ship;
        droneHealth = 0;
        droneKills = 0;

        radius = this.image.getHeight() / 2;

    }

    /**
     *  
     * @return ship
     */

     ImageView getShip() {

        return ship;

    }

    /**
     * 
     * @return entity
     */

    Node getNode() {

        return entity;
    }

}