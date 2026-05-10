/**
 * The CollisionReactor interface defines a behavior that can occur when
 * one element collides with another on the game grid.
 *
 * Classes that implement this interface can respond to collisions
 * in a custom way (e.g., bounce, disappear, change color, etc.).
 */
public interface CollisionReactor {

    /**
     * Defines how the implementing object reacts when it collides with another element.
     *
     * @param other the Element that this object has collided with
     */
    public abstract void reactToCollision(Element other);
}
