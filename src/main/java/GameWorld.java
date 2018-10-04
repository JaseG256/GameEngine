import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class GameWorld {

    private Scene gameSurface;

    private Group sceneNodes;

    private static Timeline gameLoop;

    private final int framesPerSecond;

    private final String windowTitle;

    private final SpriteManager spriteManager = new SpriteManager();

    public GameWorld(final int fps, final String title) {
        framesPerSecond = fps;
        windowTitle = title;
        buildAndSetGameLoop();
    }

    protected final void buildAndSetGameLoop() {
        final Duration oneFrameAmount = Duration.millis(1000 / getFramesPerSecond());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmount,
                (event) -> {
                    updateSprites();
                    checkCollisions();
                    cleanUpSprites();

                }); // Handles One Frame

        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.getKeyFrames().add(oneFrame);

        setGameLoop(gameLoop);
    }

    // Initialize the game world
    public abstract void initialize(final Stage primaryStage);

    public void beginGameLoop() {
        getGameLoop().play();
    }

    protected void updateSprites() {
        for (Sprite sprite : SpriteManager.getAllSprites()) {
            handleUpdate(sprite);
        }
    }

    protected void handleUpdate(Sprite sprite) {
    }

    protected void checkCollisions() {
        //check other sprites collisions
        spriteManager.resetCollisionsToCheck();
        //check each sprite against other sprite objects
        for (Sprite spriteA : spriteManager.getCollisionsToCheck()) {
            for (Sprite spriteB : spriteManager.getAllSprites()) {
                if (handleCollision(spriteA, spriteB)) {
                    // The break helps optimize the collisions
                    //  The break statement means one object only hits another
                    // object as opposed to one hitting many objects.
                    // To be more accurate comment out the break statement.
                    break;
                    ;
                }
            }
        }
    }

    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        //If collision occurs return true
        return false;
    }

    protected void cleanUpSprites() {
        spriteManager.cleanUpSprites();
    }

    public String getWindowTitle() {
        return windowTitle;
    }

    protected static Timeline getGameLoop() {
        return gameLoop;
    }

    protected static void setGameLoop(Timeline gameLoop) {
        GameWorld.gameLoop = gameLoop;
    }

    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Returns the sprite manager containing the sprite objects to
     * manipulate in the game.
     *
     * @return SpriteManager The sprite manager.
     */
    protected SpriteManager getSpriteManager() {
        return spriteManager;
    }

    /**
     * Returns the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     *
     * @return
     */
    public Scene getGameSurface() {
        return gameSurface;
    }

    /**
     * Sets the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     *
     * @param gameSurface The main game surface (JavaFX Scene).
     */
    protected void setGameSurface(Scene gameSurface) {
        this.gameSurface = gameSurface;
    }

    /**
     * All JavaFX nodes which are rendered onto the game surface(Scene) is
     * a JavaFX Group object.
     *
     * @return Group The root containing many child nodes to be displayed into
     * the Scene area.
     */
    public Group getSceneNodes() {
        return sceneNodes;
    }

    /**
     * Sets the JavaFX Group that will hold all JavaFX nodes which are rendered
     * onto the game surface(Scene) is a JavaFX Group object.
     *
     * @param sceneNodes The root container having many children nodes
     *                   to be displayed into the Scene area.
     */
    protected void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }
}
