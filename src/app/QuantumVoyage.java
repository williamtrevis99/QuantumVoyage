package app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This is the main class for the program, this class extends application which
 * allows for javafx to run.
 */

public class QuantumVoyage extends Application {

    MainViewManager viewManager = new MainViewManager();

    /**
     * The main entry point for all JavaFX applications. The start method is called
     * after the init method has returned, and after the system is ready for the
     * application to begin running.
     */

    public void start(Stage stage) {

        try {

            stage = viewManager.getStage();
            stage.show();

        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {

        launch(args);

    }

}