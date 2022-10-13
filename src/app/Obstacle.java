package app;

import java.io.Serializable;

/**
 * Class which extends Entity to create an Obstacle.
 */

public class Obstacle extends Entity implements Serializable{

    Obstacle(String entityImage, int x, int y) {

        super(entityImage, x, y);

    }

}