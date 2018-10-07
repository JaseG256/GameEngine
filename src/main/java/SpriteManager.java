import java.util.*;

public class SpriteManager {

    private static final List<Sprite> GAME_ACTORS = new ArrayList<>();
    private static final List<Sprite> CHECK_COLLISION_LIST = new ArrayList<>();
    private static final Set<Sprite> CLEAN_UP_SPRITES = new HashSet<>();

    public List<Sprite> getAllSprites() {
        return GAME_ACTORS;
    }

    public void addSprites(Sprite... sprites) {
        GAME_ACTORS.addAll(Arrays.asList(sprites));
    }

    /** Returns a set of sprite objects to be removed from the GAME_ACTORS.
     * @return CLEAN_UP_SPRITES
     */
    public Set<Sprite> getSpritesToBeRemoved() {
        return CLEAN_UP_SPRITES;
    }

    public void addSpritesToBeRemoved(Sprite... sprites) {
        if (sprites.length > 1) {
            CLEAN_UP_SPRITES.addAll(Arrays.asList((Sprite[]) sprites));
        } else {
            CLEAN_UP_SPRITES.add(sprites[0]);
        }
    }

    /**
     * Returns a list of sprite objects to assist in collision checks.
     * This is a temporary and is a copy of all current sprite objects
     * (copy of GAME_ACTORS).
     * @return CHECK_COLLISION_LIST
     */
    public List<Sprite> getCollisionsToCheck() {
        return CHECK_COLLISION_LIST;
    }

    /**
     * Clears the list of sprite objects in the collision check collection
     * (CHECK_COLLISION_LIST).
     */
    public void resetCollisionsToCheck() {
        CHECK_COLLISION_LIST.clear();
        CHECK_COLLISION_LIST.addAll(GAME_ACTORS);
    }

    /**
     * Removes sprite objects and nodes from all
     * temporary collections such as:
     * CLEAN_UP_SPRITES.
     * The sprite to be removed will also be removed from the
     * list of all sprite objects called (GAME_ACTORS).
     */
    public void cleanUpSprites() {
        GAME_ACTORS.removeAll(CLEAN_UP_SPRITES);
        CLEAN_UP_SPRITES.clear();
    }

}
