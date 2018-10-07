import javafx.animation.Animation;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class Sprite {

    public List<Animation> animations = new ArrayList<>();
    public Node node;
    public double vX, vY;
    public boolean isDead;

    public abstract void update();

    public boolean collide(Sprite other) {
        return false;
    }
}
