package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;

import javafx.event.ActionEvent;

/**
 * This is a class that manages the game environment, in this class, i create
 * objects, destroy objects, and move around the arena @
 */

public class gameViewManager implements Serializable {

    
    transient private Pane gamePane;
    transient private Scene gameScene;
    transient private Stage gameStage;
    transient private Stage menuStage;

    private double screenWidth;
    private double screenHeight;

    transient BackgroundImage back;
    transient Image newimage;

    private double maxUp;
    private double maxDown;
    private double maxRight;
    private double maxLeft;

    private Integer level = 1;
    private Integer totalKills;
    private Integer killTarget;
    protected double playerSpeed = 7;

    Player player;
    transient ImageView ship;
    transient public Text text;
    Satellite enemy;

    transient Image backgroundImage;

    transient Text gameLevel;
    transient Text gameKills;
    transient Text playerHealth;
    transient Text gameover;
    transient Text thetotalKills;
    Boolean isDisplayer;
    SpaceStation meteor;

    transient private AnimationTimer timer;
    transient final long startNanoTime = System.nanoTime();
    //SoundManager soundManager = new SoundManager();
    transient BackgroundImage currentBackground;
    List<Button> menuButtons;
    transient ToolBar toolbar;
    Boolean isClosed = true;

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Satellite> enteties = new ArrayList<Satellite>();
    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    private ArrayList<SpaceStation> stations = new ArrayList<SpaceStation>();

    private ArrayList<Portal> portals = new ArrayList<Portal>();
    private ArrayList<Meteor> meteors = new ArrayList<Meteor>();
    transient ArrayList<BackgroundImage> backgrounds = new ArrayList<BackgroundImage>();
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    /**
     * Main class constructor
     * 
     * @param height
     * @param width
     */

    gameViewManager(double height, double width) {

        screenHeight = height;
        screenWidth = width;
        menuButtons = new ArrayList<>();
        isDisplayer = false;

        totalKills = 0;
        killTarget = 10;

        maxUp = 0;
        maxDown = (int) screenHeight;
        maxRight = (int) screenWidth;
        maxLeft = 0;

        createBackGrounds();
        initializeStage();
        setBackground();
        createPlayer();
        createEnemies(2);
        createSatellites(6);
        createKeyListeners();
        createGameinformation();
        createToolbar();

    }

    /**
     * Initialised the stage, adds associated scenes.
     */

    private void initializeStage() {

        gamePane = new Pane();
        gameScene = new Scene(gamePane, screenWidth, screenHeight);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

    }

    /**
     * Creates the toolbar objects
     */

    private void createToolbar() {

        gamePane.getStylesheets().add("app/styles.css");

        Button addEnemy = new Button("Add Enemy");
        // addEnemy.setId("settings-button");
        addEnemy.setOnAction(this::handleButtonAction);
        menuButtons.add(addEnemy);

        Button addSatellite = new Button("Add Satellite");
        // addSatellite.setId("settings-button");
        addSatellite.setOnAction(this::handleButtonAction);
        menuButtons.add(addSatellite);

        Button addObstacle = new Button("Add Obstacle");
        // addSatellite.setId("settings-button");
        addObstacle.setOnAction(this::handleButtonAction);
        menuButtons.add(addObstacle);

        Button addMeteor = new Button("Add Meteor");
        // addSatellite.setId("settings-button");
        addMeteor.setOnAction(this::handleButtonAction);
        menuButtons.add(addMeteor);

        Button addStation = new Button("Add Station");
        // addSatellite.setId("settings-button");
        addStation.setOnAction(this::handleButtonAction);
        menuButtons.add(addStation);

        Button addInfo = new Button("Add Info");
        // addSatellite.setId("settings-button");
        addInfo.setOnAction(this::handleButtonAction);
        menuButtons.add(addInfo);

        Button removeInfo = new Button("Remove Info");
        // addSatellite.setId("settings-button");
        removeInfo.setOnAction(this::handleButtonAction);
        menuButtons.add(removeInfo);

        Button stopSim = new Button("Stop Sim");
        // addSatellite.setId("settings-button");
        stopSim.setOnAction(this::handleButtonAction);
        menuButtons.add(stopSim);

        Button startSim = new Button("Start Sim");
        // addSatellite.setId("settings-button");
        startSim.setOnAction(this::handleButtonAction);
        menuButtons.add(startSim);

        toolbar = new ToolBar(addEnemy, new Separator(), addSatellite, new Separator(), addObstacle, new Separator(),
                addMeteor, new Separator(), addStation, new Separator(), addInfo, new Separator(), removeInfo,
                new Separator(), stopSim, new Separator(), startSim

        );

        toolbar.setPrefWidth(screenWidth);
        // toolbar.set

    }

    /**
     * Displays the tool bar
     */

    private void displayToolBar() {

        gamePane.getChildren().add(toolbar);

    }

    /**
     * Removes the tool bar
     */

    private void removeToolBar() {

        gamePane.getChildren().remove(toolbar);

    }

    public void handleButtonAction(ActionEvent event) {

        if (event.getSource() == menuButtons.get(0)) {
            createEnemy();
        }
        if (event.getSource() == menuButtons.get(1)) {
            createSatellite();
        }
        if (event.getSource() == menuButtons.get(2)) {
            createObstacle();
        }
        if (event.getSource() == menuButtons.get(3)) {
            createMeteor();
        }
        if (event.getSource() == menuButtons.get(4)) {
            createSpaceStation();
        }
        if (event.getSource() == menuButtons.get(5)) {
            addInformation();
        }
        if (event.getSource() == menuButtons.get(6)) {
            removeInformation();
        }
        if (event.getSource() == menuButtons.get(7)) {
            timer.stop();
        }
        if (event.getSource() == menuButtons.get(8)) {
            timer.start();
        }

        event.consume();

    }

    /**
     * Initialises backgrounds, and adds them to a List
     */

    private void createBackGrounds() {

        String[] paths = { "app/assets/images/mine.jpg", "app/assets/images/newBack.png",
                "app/assets/images/newBack.png", "app/assets/images/back3.png", "app/assets/images/wallpaper4.png",
                "app/assets/images/6.png", "app/assets/images/7.png",

        };

        for (String path : paths) {
            newimage = new Image(path, screenWidth, screenHeight, false, true);
            backgroundImage = newimage;
            back = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
                    null);
            backgrounds.add(back);
        }

    }

    /**
     * Displays Information associated with said Entity
     */

    private void addInformation() {
        for (Satellite satellite : enteties) {
            gamePane.getChildren().add(satellite.positionalData);
        }
        for (Enemy enemy : enemies) {
            gamePane.getChildren().add(enemy.positionalData);
        }
        for (SpaceStation station : stations) {
            gamePane.getChildren().add(station.positionalData);
        }

    }

    /**
     * Removes Information associated with said Entity
     */

    private void removeInformation() {
        for (Satellite satellite : enteties) {
            gamePane.getChildren().remove(satellite.positionalData);
        }
        for (Enemy enemy : enemies) {
            gamePane.getChildren().remove(enemy.positionalData);
        }

        for (SpaceStation station : stations) {
            gamePane.getChildren().remove(station.positionalData);
        }
    }

    private void removeInfo(Entity e) {
        gamePane.getChildren().remove(e.positionalData);

    }

    private void removeEnemyInfo(Enemy e) {
        gamePane.getChildren().remove(e.positionalData);

    }

    /**
     * This function creates the information displayed in the gameView
     */

    private void createGameinformation() {

        gameLevel = new Text("LEVEL 1");
        gameLevel.setFill(Color.WHITE);
        gameLevel.setFont(new Font(40));
        // gameLevel.setFont(Font.loadFont("file:assets/fonts/defconzero.ttf", 70));
        double thiswidth = gameLevel.getBoundsInLocal().getWidth() / 2;
        gameLevel.setLayoutX(screenWidth / 2 + -thiswidth);
        gameLevel.setLayoutY(55);
        gamePane.getChildren().add(gameLevel);

        playerHealth = new Text("100%");
        playerHealth.setFill(Color.WHITE);
        playerHealth.setFont(new Font(40));
        // playerHealth.setFont(Font.loadFont("file:./assets/fonts/defconzero.ttf",
        // 70));
        playerHealth.setLayoutX(screenWidth - 100);
        playerHealth.setLayoutY(55);
        gamePane.getChildren().add(playerHealth);

        gameKills = new Text("KILLS: ");
        gameKills.setFill(Color.WHITE);
        gameKills.setFont(new Font(40));
        // gameKills.setFont(Font.loadFont("file:./assets/fonts/defconzero.ttf", 70));
        gameKills.setLayoutX(60);
        gameKills.setLayoutY(55);
        gamePane.getChildren().add(gameKills);

        gameover = new Text("GAME OVER");
        gameover.setFill(Color.RED);
        gameover.setFont(new Font(230));
        // gameover.setFont(Font.loadFont("file:./assets/fonts/defconzero.ttf", 300));
        double width = gameover.getBoundsInLocal().getWidth() / 2;
        gameover.setLayoutX(screenWidth / 2 - width);
        gameover.setLayoutY(screenHeight / 2);

        thetotalKills = new Text("");
        thetotalKills.setFill(Color.GREEN);
        thetotalKills.setFont(Font.loadFont("file:./assets/fonts/defconzero.ttf", 100));
        thetotalKills.setLayoutY(screenHeight / 2 + 220);

    }

    /**
     * This function creates an obstace, adds the obstacle to the pane, and adds it
     * to an obstacle ArrayList.
     */

    private void createObstacle() {

        Obstacle obstacle = new Obstacle("app/assets/images/obstacle.png", 80, 80);
        gamePane.getChildren().add(obstacle.getEntity());
        obstacles.add(obstacle);

    }

    /**
     * This function takes a parameter which is the number of obstacles are wished
     * to be added.
     * 
     * @param number
     */

    private void createObstacles(int number) {

        for (int i = 0; i < number; i++) {
            createObstacle();
        }

    }

    /**
     * This function adds a Meteor to the arena, and adds the particular meteor to a
     * meteor arraylist.
     */

    private void createMeteor() {

        Meteor meteor = new Meteor("app/assets/images/meteor.png");
        meteor.setVelocity(new Point2D(1, 0));
        meteor.rotateRightAngle();
        gamePane.getChildren().add(meteor.getEntity());
        meteors.add(meteor);

    }

    /**
     * This function creates multiple meteors using a provided number, and the
     * createMeteor() function.
     * 
     * @param number
     */

    private void createMeteors(int number) {

        for (int i = 0; i < number; i++) {
            createMeteor();
        }

    }

    /**
     * This function adds multiple satellites using a provided number.
     *
     * @param number
     */

    private void createSatellites(int number) {

        for (int i = 0; i < number; i++) {
            createSatellite();
        }

    }

    /**
     * This function adds a portal to the GamePane and adds the portal to a portal
     * arrayList
     */

    private void createPortal() {

        Portal portal = new Portal("app/assets/images/portal.png", 100, 100);
        gamePane.getChildren().add(portal.getEntity());
        portals.add(portal);

    }

    /**
     * This function creates a singular
     */

    private void createSatellite() {

        enemy = new Satellite("app/assets/images/satellite.png", 50, 50);
        enemy.setVelocity(new Point2D(1, 0));
        gamePane.getChildren().add(enemy.getEntity());
        enteties.add(enemy);

    }

    /**
     * This function moves all entities, in the entity arrayList
     */

    private void moveEntities() {

        if (enteties.size() != 0) {
            for (Satellite ent : enteties) {
                ent.moveEntity();
            }
        }

    }

    private void moveStations() {

        if (stations.size() != 0) {
            for (SpaceStation st : stations) {
                st.moveEntity();
            }
        }

    }

    /**
     * This function creates a space Station object and adds it to the gamePane
     */

    private void createSpaceStation() {

        meteor = new SpaceStation("app/assets/images/station.png", 50, 50);
        meteor.setVelocity(new Point2D(1, 0));
        gamePane.getChildren().add(meteor.getEntity());
        stations.add(meteor);

    }

    private void createStations(int number) {
        for (int i = 0; i < number; i++) {
            createSpaceStation();
        }
    }

    /**
     * This function creates an enemy, adds said enemy to the gamePane, and adds to
     * enemy arrayList.
     */

    private void createEnemy() {

        Enemy enemy = new Enemy(20, Color.RED);
        gamePane.getChildren().add(enemy);
        enemies.add(enemy);

    }

    /**
     * This function adds a number of enemies
     * 
     * @param number
     */

    private void createEnemies(int number) {

        for (int i = 0; i < number; i++) {
            createEnemy();
        }

    }

    /**
     * For each enemy, in enemy arrayList, animateEnemy
     */

    public void animateEnemies() {

        if (enemies.size() != 0) {

            for (Enemy enemy : enemies) {
                enemy.moveEnemy(enemy, ship.getTranslateX(), ship.getTranslateY());
            }

        }

    }

    /**
     * For each meteor in arrayList, move meteor
     */

    private void moveMeteors() {

        if (meteors.size() != 0) {

            for (Entity meteor : meteors) {
                meteor.moveDown();
            }

        }

    }

    /**
     * This function creates a player object, adds the player to the gamePane.
     */

    private void createPlayer() {

        player = new Player(maxUp, maxDown, maxLeft, maxRight, playerSpeed);
        ship = player.getShip();
        player.setVelocity(new Point2D(1, 0));
        gamePane.getChildren().add(ship);

    }

    transient BackgroundImage bg;

    /**
     * Sets the background to the first image in the background image arraylist
     */

    private void setBackground() {

        gamePane.setBackground(new Background(backgrounds.get(0)));
        currentBackground = backgrounds.get(0);

    }

    /**
     * Sets a newBackground which isnt equal to the current background
     */

    private void setNewBackground() {

        Random r = new Random();

        do {

            bg = backgrounds.get(r.nextInt(backgrounds.size()));

        } while (bg.hashCode() == currentBackground.hashCode());

        currentBackground = bg;

        gamePane.setBackground(new Background(bg));

    }

    /**
     * The main game loop which updates all objects in the game.
     */

    public void gameLoop() {

        // game loop
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                // updateLaser();
                animateEnemies();
                player.moveUp();
                moveEntities();
                moveMeteors();
                moveStations();
                updateBullets();
                updatePositions();
                checkAllCollisions();
                spawnPortal();

                if (isDead()) {

                    endGame();

                }

            }

        };

        timer.start();

    }

    /**
     * Code that will be execute when the player has no health left.
     */

    private void endGame() {
        try {
            gamePane.getChildren().add(gameover);
            String b = totalKills.toString();
            thetotalKills.setText("Kills :" + b);
            double width2 = thetotalKills.getBoundsInLocal().getWidth() / 2;
            thetotalKills.setLayoutX(screenWidth / 2 - width2);
            gamePane.getChildren().add(thetotalKills);
            gamePane.getChildren().remove(player.getEntity());
        } catch (Exception e) {

        }

    }

    /**
     * Returns if players health is below a given level.
     * 
     * @return
     */

    private boolean isDead() {

        return player.droneHealth <= 0;

    }

    /**
     * This function spawns a portal in a random position when the players total
     * kills equal the kill target. The kill target is then increased.
     */

    public void spawnPortal() {

        if (totalKills == killTarget) {
            createPortal();
            //soundManager.levelUpSound();
            killTarget += 5;

        }

    }

    /**
     * Code to fire a bullet
     */

    private void fireBullet() {

        //soundManager.gunSound();
        Bullet bullet = new Bullet();
        bullet.init(player.getCenter().x, player.getCenter().y, player.getVelocity(), ship.getRotate());
        bullets.add(bullet); // add bullet to linked list
        gamePane.getChildren().add(bullet.getBullet());

    }

    private void createKeyListeners() {

        gameScene.setOnKeyPressed(event -> {

            KeyCode key = event.getCode();
            if (key == KeyCode.W)
                player.speed = player.maxSpeed;
            if (key == KeyCode.A)
                player.rotSpeed = -player.maxSpeed / 2;
            if (key == KeyCode.S)
                player.speed = -player.maxSpeed;
            if (key == KeyCode.D)
                player.rotSpeed = player.maxSpeed / 2;

            if (event.getCode() == KeyCode.ESCAPE) {

                timer.stop();
                gameStage.hide();
                menuStage.show();

            }

            if (event.getCode() == KeyCode.T) {
                if (!isDisplayer) {
                    displayToolBar();
                    isDisplayer = true;
                }
            }
            if (event.getCode() == KeyCode.Y) {
                if (isDisplayer) {
                    removeToolBar();
                    isDisplayer = false;
                }
            }

        });

        gameScene.setOnKeyReleased(e -> {
            KeyCode key1 = e.getCode();
            if (key1 == KeyCode.W)
                player.speed = player.speed != -player.maxSpeed ? 0 : player.speed;
            if (key1 == KeyCode.A)
                player.rotSpeed = player.rotSpeed != player.maxSpeed / 2 ? 0 : player.rotSpeed;
            if (key1 == KeyCode.S)
                player.speed = player.speed != player.maxSpeed ? 0 : player.speed;
            if (key1 == KeyCode.D)
                player.rotSpeed = player.rotSpeed != -player.maxSpeed / 2 ? 0 : player.rotSpeed;
        });

        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE) {

                fireBullet();
                e.consume();
            }
        });

    }

    /**
     * Function that allows a newGame to be created from the main Menu
     * 
     * @param menuStage
     */

    protected void showGame(Stage menuStage) {

        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();

    }

    /**
     * Function that updates the position of the bullets
     */

    private void updateBullets() {

        for (Bullet bullet : bullets) {

            bullet.xpos += bullet.velocity.getX() * 20;
            bullet.ypos += bullet.velocity.getY() * 20;
            bullet.ship.setTranslateX(bullet.xpos);
            bullet.ship.setTranslateY(bullet.ypos);

        }

    }

    /**
     * Function that increments the players total kills, and then updates the
     * display
     */

    private void addKill() {

        totalKills++;
        String stringKills = totalKills.toString();
        gameKills.setText("KILLS = " + stringKills);

    }

    private void reducePlayerHealth() {

        player.droneHealth += -10;
        String stringHealth = player.droneHealth.toString();
        playerHealth.setText(stringHealth + "%");
        //soundManager.deny();

    }

    private void nextLevel() {

        setNewBackground();

        level++;

        String stringKills = totalKills.toString();
        gameKills.setText("KILLS = " + stringKills);

        String stringLevel = level.toString();
        gameLevel.setText("LEVEL " + stringLevel);

        deleteAllObjects();
        createEnemies(3);
        createObstacles(2);
        createSatellites(level * 2);
        createMeteors(level * 2);
        createStations(level * 2);

    }

    /**
     * Function with encapsulated for loops that check for bullet collisions
     */

    private void checkBulletCollisions() {

        try {

            for (Enemy enemy : enemies) {

                Bounds enemyBounds = enemy.getBoundsInParent();

                for (Bullet bullet : bullets) {

                    Bounds bulletBounds = bullet.getBullet().getBoundsInParent();

                    if (enemyBounds.intersects(bulletBounds)) {

                        //soundManager.destroySound();
                        gamePane.getChildren().remove(enemy);
                        enemies.remove(enemy);
                        gamePane.getChildren().remove(bullet.getBullet());
                        bullets.remove(bullet);
                        removeEnemyInfo(enemy);
                        addKill();

                    }

                }

            }

            for (Enemy enemy : enemies) {

                Bounds enemyBounds = enemy.getBoundsInParent();

                for (Entity obstacle : obstacles) {

                    Bounds obstacleBounds = obstacle.getEntity().getBoundsInParent();

                    if (enemyBounds.intersects(obstacleBounds)) {

                        gamePane.getChildren().remove(enemy);
                        enemies.remove(enemy);
                        removeEnemyInfo(enemy);

                    }

                }

            }

            for (Entity obstacle : obstacles) {

                Bounds obstacleBounds = obstacle.getEntity().getBoundsInParent();

                for (Bullet bullet : bullets) {

                    Bounds bulletBounds = bullet.getBullet().getBoundsInParent();

                    if (obstacleBounds.intersects(bulletBounds)) {

                        gamePane.getChildren().remove(bullet.getBullet());
                        bullets.remove(bullet);

                    }
                }

            }

        } catch (Exception e) {

        }

    }

    private void checkSatelliteCollisions() {
        try {

            for (Entity entity : enteties) {

                Bounds satelliteBounds = entity.getEntity().getBoundsInParent();

                for (Bullet bullet : bullets) {

                    Bounds bulletBounds = bullet.getBullet().getBoundsInParent();

                    if (satelliteBounds.intersects(bulletBounds)) {

                        addKill();

                        enteties.remove(entity);
                        gamePane.getChildren().remove(entity.getEntity());

                        gamePane.getChildren().remove(bullet.getBullet());
                        bullets.remove(bullet);

                        removeInfo(entity);

                    }

                }
            }

            for (Entity entity : enteties) {

                Bounds satelliteBounds = entity.getEntity().getBoundsInParent();

                for (Entity entity2 : enteties) {

                    if (entity.hashCode() == entity2.hashCode()) {
                        continue;
                    }

                    Bounds satelliteBounds2 = entity2.getEntity().getBoundsInParent();

                    if (satelliteBounds.intersects(satelliteBounds2)) {

                        entity.destroyCollision();
                        entity2.reverse();

                    }

                }
            }

            for (Entity entity : stations) {

                Bounds stationBounds = entity.getEntity().getBoundsInParent();

                for (Entity entity2 : stations) {

                    if (entity.hashCode() == entity2.hashCode()) {
                        continue;
                    }

                    Bounds stationBounds2 = entity2.getEntity().getBoundsInParent();

                    if (stationBounds.intersects(stationBounds2)) {

                        entity.reverse();
                        entity2.destroyCollision();

                    }

                }
            }

            for (Entity station : stations) {

                Bounds stationBounds = station.getEntity().getBoundsInParent();

                for (Entity satellite : enteties) {

                    Bounds satelliteBounds = satellite.getEntity().getBoundsInParent();

                    if (stationBounds.intersects(satelliteBounds)) {

                        station.destroyCollision();
                        satellite.destroyCollision();
                    }
                }
            }

            for (Entity station : stations) {

                Bounds stationBounds = station.getEntity().getBoundsInParent();

                for (Entity meteor : meteors) {

                    Bounds meteorBounds = meteor.getEntity().getBoundsInParent();

                    if (stationBounds.intersects(meteorBounds)) {

                        gamePane.getChildren().remove(meteor.getEntity());
                        meteors.remove(meteor);
                        station.destroyCollision();

                    }
                }
            }

            for (Entity station : stations) {

                Bounds stationBounds = station.getEntity().getBoundsInParent();

                for (Enemy enemy : enemies) {

                    Bounds enemyBounds = enemy.getBoundsInParent();

                    if (stationBounds.intersects(enemyBounds)) {

                        station.destroyCollision();
                        gamePane.getChildren().remove(enemy);
                        enemies.remove(enemy);
                        removeEnemyInfo(enemy);
                    }
                }
            }

            for (Entity station : stations) {

                Bounds stationBounds = station.getEntity().getBoundsInParent();

                for (Entity obstacle : obstacles) {

                    Bounds obstacleBounds = obstacle.getEntity().getBoundsInParent();

                    if (stationBounds.intersects(obstacleBounds)) {

                        station.destroyCollision();

                    }
                }
            }

            for (Entity station : stations) {

                Bounds stationBounds = station.getEntity().getBoundsInParent();

                for (Bullet bullet : bullets) {

                    Bounds bulletBounds = bullet.getBullet().getBoundsInParent();

                    if (stationBounds.intersects(bulletBounds)) {

                        gamePane.getChildren().remove(station.getEntity());
                        stations.remove(station);
                        removeInfo(station);

                        gamePane.getChildren().remove(bullet.getBullet());
                        bullets.remove(bullet);

                        addKill();

                    }
                }
            }

            for (Entity meteor : meteors) {
                Bounds meteorBounds = meteor.getEntity().getBoundsInParent();

                for (Bullet bullet : bullets) {

                    Bounds bulletBounds = bullet.getBullet().getBoundsInParent();

                    if (bulletBounds.intersects(meteorBounds)) {
                        gamePane.getChildren().remove(meteor.getEntity());
                        meteors.remove(meteor);

                        gamePane.getChildren().remove(bullet.getBullet());
                        bullets.remove(bullet);

                    }
                }
            }

            for (Entity meteor : meteors) {
                Bounds meteorBounds = meteor.getEntity().getBoundsInParent();

                for (Entity satellite : enteties) {

                    Bounds satelliteBounds = satellite.getEntity().getBoundsInParent();

                    if (satelliteBounds.intersects(meteorBounds)) {
                        gamePane.getChildren().remove(meteor.getEntity());
                        meteors.remove(meteor);

                        satellite.destroyCollision();

                    }
                }
            }

            for (Entity entity : enteties) {

                Bounds satelliteBounds = entity.getEntity().getBoundsInParent();

                for (Enemy enemy : enemies) {

                    Bounds enemyBounds = enemy.getBoundsInParent();

                    if (enemyBounds.intersects(satelliteBounds)) {

                        gamePane.getChildren().remove(enemy);
                        enemies.remove(enemy);
                        this.enemy.destroyCollision();
                        removeEnemyInfo(enemy);

                    }

                }
            }

        } catch (Exception e) {

        }
    }

    private void updatePositions() {

        for (Satellite satellite : enteties) {

            double positionX = satellite.getEntity().getTranslateX();

            String xVal = df2.format(positionX);
            double positionY = satellite.getEntity().getTranslateY();
            String yVal = df2.format(positionY);
            satellite.positionalData.setFill(Color.WHITE);
            satellite.positionalData.setText("X: " + xVal + "  Y: " + yVal);
            satellite.positionalData.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
            satellite.positionalData.setTranslateY(satellite.getEntity().getTranslateY() + 40);
            satellite.positionalData.setTranslateX(satellite.getEntity().getTranslateX() - 20);

        }

        for (SpaceStation station : stations) {

            double positionX = station.getEntity().getTranslateX();
            String xVal = df2.format(positionX);
            double positionY = station.getEntity().getTranslateY();
            String yVal = df2.format(positionY);
            station.positionalData.setFill(Color.WHITE);
            station.positionalData.setText("X: " + xVal + "  Y: " + yVal);
            station.positionalData.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
            station.positionalData.setTranslateY(station.getEntity().getTranslateY() + 40);
            station.positionalData.setTranslateX(station.getEntity().getTranslateX() - 20);

        }

        for (Enemy enemy : enemies) {

            double positionX = enemy.getTranslateX();
            String xVal = df2.format(positionX);
            double positionY = enemy.getTranslateY();
            String yVal = df2.format(positionY);
            enemy.positionalData.setFill(Color.WHITE);
            enemy.positionalData.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
            enemy.positionalData.setText("X: " + xVal + "  Y: " + yVal);
            enemy.positionalData.setTranslateY(enemy.getTranslateY() + 40);
            enemy.positionalData.setTranslateX(enemy.getTranslateX() - 20);

        }

    }

    private void checkDroneCollisions() {

        try {

            for (Enemy enemy : enemies) {

                Bounds playerBounds = player.getNode().getBoundsInParent();
                Bounds enemyBounds = enemy.getBoundsInParent();

                if (playerBounds.intersects(enemyBounds)) {

                    gamePane.getChildren().remove(enemy);
                    enemies.remove(enemy);
                    reducePlayerHealth();
                    removeEnemyInfo(enemy);

                }

            }

            for (Entity meteor : meteors) {

                Bounds playerBounds = player.getNode().getBoundsInParent();
                Bounds meteorBounds = meteor.getEntity().getBoundsInParent();

                if (playerBounds.intersects(meteorBounds)) {

                    gamePane.getChildren().remove(meteor.getEntity());
                    meteors.remove(meteor);
                    reducePlayerHealth();

                }

            }

            for (Entity station : stations) {

                Bounds playerBounds = player.getNode().getBoundsInParent();
                Bounds stationBounds = station.getEntity().getBoundsInParent();

                if (playerBounds.intersects(stationBounds)) {

                    gamePane.getChildren().remove(station.getEntity());
                    stations.remove(station);
                    reducePlayerHealth();
                    removeInfo(station);

                }

            }

            for (Entity entity : enteties) {

                Bounds playerBounds = player.getNode().getBoundsInParent();
                Bounds satelliteBounds = entity.getEntity().getBoundsInParent();

                if (playerBounds.intersects(satelliteBounds)) {

                    gamePane.getChildren().remove(entity.getEntity());
                    enteties.remove(entity);
                    reducePlayerHealth();
                    removeInfo(entity);

                }

            }

            for (Entity meteor : meteors) {

                Bounds meteorBounds = meteor.getEntity().getBoundsInParent();

                for (Entity obstacle : obstacles) {

                    Bounds obstacleBounds = obstacle.getEntity().getBoundsInParent();

                    if (meteorBounds.intersects(obstacleBounds)) {

                        gamePane.getChildren().remove(meteor.getEntity());
                        meteors.remove(meteor);

                    }
                }

            }

            for (Entity obstacle : obstacles) {

                Bounds obstacleBounds = obstacle.getEntity().getBoundsInParent();

                for (Entity satellite : enteties) {

                    Bounds satelliteBounds = satellite.getEntity().getBoundsInParent();

                    if (satelliteBounds.intersects(obstacleBounds)) {

                        satellite.destroyCollision();

                    }

                }

            }

            for (Entity portal : portals) {

                Bounds playerBounds = player.getNode().getBoundsInParent();
                Bounds portalBounds = portal.getEntity().getBoundsInParent();

                if (playerBounds.intersects(portalBounds)) {

                    gamePane.getChildren().remove(portal.getEntity());
                    portals.remove(portal);
                    nextLevel();

                }

            }

            for (Entity obstacle : obstacles) {

                Bounds playerBounds = player.getNode().getBoundsInParent();
                Bounds obstacleBounds = obstacle.getEntity().getBoundsInParent();

                if (playerBounds.intersects(obstacleBounds)) {

                    reducePlayerHealth();
                    player.reverse();

                }

            }

        } catch (Exception e) {

        }

    }

    /**
     * This function removes all objects (apart from player) from the scene
     */

    private void deleteAllObjects() {

        for (Enemy enemi : enemies) {
            gamePane.getChildren().remove(enemi);
        }
        enemies.clear();

        for (Entity satellite : enteties) {
            gamePane.getChildren().remove(satellite.getEntity());
        }
        enteties.clear();

        for (Entity obstacle : obstacles) {
            gamePane.getChildren().remove(obstacle.getEntity());
        }
        obstacles.clear();

        for (Entity meteor : meteors) {
            gamePane.getChildren().remove(meteor.getEntity());
        }
        meteors.clear();
    }

    /**
     * Function to call all check object functions
     */

    private void checkAllCollisions() {
        checkBulletCollisions();
        checkDroneCollisions();
        checkSatelliteCollisions();
    }

}
