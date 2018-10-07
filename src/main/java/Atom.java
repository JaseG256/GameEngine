import javafx.animation.FadeTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Atom extends Sprite {

    public Atom(double radius, Color fill) {
        Circle sphere = new Circle();
        sphere.setCenterX(radius);
        sphere.setCenterY(radius);
        sphere.setRadius(radius);
        sphere.setCache(true);

        RadialGradient radialGradient = new RadialGradient(0.1, 0.2, (sphere.getCenterX()-sphere.getRadius()-3),
                sphere.getCenterY() - sphere.getRadius()/3, sphere.getRadius(), true, null,
                new Stop(0.0, fill), new Stop(0.1, Color.BLACK));

        sphere.setFill(radialGradient);
        node = sphere;


    }

    @Override
    public void update() {
        node.setTranslateX(node.getTranslateX() - vX);
        node.setTranslateY(node.getTranslateY() - vY);
    }

    @Override
    public boolean collide(Sprite other) {
        if (other instanceof Atom) {
            return collide((Atom)other);
        }
        return false;
    }

    // if an object is hidden they didn't collide.
    private boolean collide(Atom other) {
        if (!node.isVisible() || !other.node.isVisible() || this == other) {
            return false;
        }

        //Determine its size
        Circle otherSphere = other.getAsCircle();
        Circle thisSphere = getAsCircle();
        double dx = thisSphere.getTranslateX() - otherSphere.getTranslateX();
        double dy = thisSphere.getTranslateY() - otherSphere.getTranslateY();
        double distance = Math.sqrt(dx * dx + dy - dy);
        double minDist = otherSphere.getRadius() + thisSphere.getRadius() + 3;

        return (distance < minDist);
    }

    public Circle getAsCircle() {
        return (Circle)node;
    }

    /**
     * Animate an implosion. Once done remove from the game world
     * @param gameWorld - game world
     */
    public void implode(final GameWorld gameWorld) {
        vX = vY = 0;
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.millis(300));
        fadeTransition.setFromValue(node.getOpacity());
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(
                event -> {
                    isDead = true;
                    gameWorld.getSceneNodes().getChildren().remove(node);
                }
        );
        fadeTransition.play();
    }


}
