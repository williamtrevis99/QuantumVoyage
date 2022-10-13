package app;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.event.ActionEvent;

/**
 * This class manages the menu view of the application.
 */
public class MainViewManager {

    private StackPane pane;
    private Scene scene;
    private Stage stage;
   
    transient Image backgroundImage;
    transient BackgroundImage background;

    List<Button> menuButtons;
    
    // declare the screen size
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds(); 
    //SoundManager soundManager = new SoundManager();
    gameViewManager gameView;
    

    /**
     * Class Constructor
     */

    MainViewManager() { // class constructor

        // initialize the ArrayList
        menuButtons = new ArrayList<>();

        pane = new StackPane();
        scene = new Scene(pane, screenWidth(), screenHeight());
        stage = new Stage();
        stage.setScene(scene);

        setBackground();
        createButtons();

    }

    /**
     * gets the class stage
     * 
     * @return stage
     */

    public Stage getStage() { // return the stage
        return stage;
    }

    /**
     * These two functions return double
     * 
     * @return
     */

    private double screenWidth() { // return the screen width

        return visualBounds.getWidth() - 90;
    }

    private double screenHeight() { // return the screen height

        return visualBounds.getHeight() - 45;
    }

    /**
     * This function sets the background of the scene to a preset image.
     */

    private void setBackground() {

        backgroundImage = new Image("app/assets/images/menubackground.jpg", screenWidth(), screenHeight(), false, true);

        background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
                null);

        pane.setBackground(new Background(background)); // set the background of the menuPane

    }

    /**
     * This function creates the buttons for this view and then adds them to the
     * pane
     */

    private void createButtons() {

        VBox vbox = new VBox(40);
        pane.getStylesheets().add("app/styles.css"); // apply stylesheet

        Button button = new Button("New Game");
        button.setId("record-sales");
        menuButtons.add(button);
        button.setOnAction(this::handleButtonAction);

        Button button2 = new Button("Load Game");
        button2.setId("record-sales");
        menuButtons.add(button2);
        button2.setOnAction(this::handleButtonAction);

        Button button4 = new Button("Exit");
        button4.setId("record-sales");
        menuButtons.add(button4);
        button4.setOnAction(this::handleButtonAction);

        vbox.getChildren().addAll(button, button2, button4); // adds the children to the node
        vbox.setAlignment(Pos.CENTER); // centers the vbox in the canvas

        pane.getChildren().add(vbox);

    }

    protected void saveGame() {

        try (FileOutputStream f = new FileOutputStream(new File("savegame.bin"))) {

            ObjectOutputStream os = new ObjectOutputStream(f);

            os.writeObject(gameView);

        } catch (Exception e) {

        }

    }

    private gameViewManager loadGame() {

        try (ObjectInputStream os = new ObjectInputStream(new FileInputStream("savegame.bin"))) {

            gameViewManager tempObj = (gameViewManager) os.readObject();
            gameView = tempObj;

        } catch (Exception e) {

        }

        return gameView;

    }

    /**
     * This function listens for button clicks, and then performs an action based on
     * the particular button clicked.
     * 
     * @param event
     */

    public void handleButtonAction(ActionEvent event) {

        if (event.getSource() == menuButtons.get(0)) { // if new game pressed

            //soundManager.buttonSound();
            gameView = new gameViewManager(screenHeight(), screenWidth()); // creates new instance
            gameView.showGame(stage); // passes menu stage to game class
            gameView.gameLoop(); // starts game loop

        }
        if (event.getSource() == menuButtons.get(1)) { // if load game pressed
            if (gameView != null) {
                saveGame();
                //soundManager.buttonSound();
                loadGame().showGame(stage);
                loadGame().gameLoop();
            }

        }
        if (event.getSource() == menuButtons.get(2)) { // if exit game pressed
            //soundManager.buttonSound();
            stage.close();

        }

        event.consume();

    }

    /**
     * null function to provide menuStage variable
     * 
     * @param menuStage menuStage is the stage of the menu
     */

    public void returnHome(Stage menuStage) {

    }

}