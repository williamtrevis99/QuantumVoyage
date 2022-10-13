package app;

import java.io.Serializable;

/**
 * Class which extends Entity to create an Meteor.
 */

public class Meteor extends Entity implements Serializable{

    Meteor(String entityImage, int x, int y) {

        super(entityImage, x, y);

    }

    Meteor(String entityImage) {

        super(entityImage);

    }

}