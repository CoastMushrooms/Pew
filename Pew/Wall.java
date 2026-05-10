import java.awt.Color;
import java.util.ArrayList;

/**
 * The Wall class is a static game element that can respond to collisions.
 * It is typically used as an obstacle or barrier within the game grid.
 *
 * A Wall is defined by its position (x, y), width, height, and visual mode.
 * Modes include:
 * - "NORMAL": No reaction on collision
 * - "COLOR CHANGE": Wall changes color when hit
 * - "BREAK": Pixels are removed from the wall when hit
 */
public class Wall extends Element implements CollisionReactor {

    private static final Color brown  = new Color(139, 69, 19); // Brown
    private static final Color redBrown = new Color(190, 69, 19); // Red Brown
    private int width;
    private int height;
    private Color c;
    private String mode; // "NORMAL", "COLOR CHANGE", or "BREAK"
    private int cooldownTimer = 0;
    private int cooldownSetPoint = 0; // 0 means not being used

    /**
     * Constructs a new Wall at the given position and size with a default mode of "COLOR CHANGE".
     *
     * @param x the horizontal (left) position
     * @param y the vertical (top) position
     * @param w the width of the wall
     * @param h the height of the wall
     */
    public Wall(int x, int y, int w, int h) {
        super(x, y, createPixelMap(w, h, brown));
        width = w;
        height = h;
        c = brown;
        mode = "BREAK";  // Set to "NORMAL", "COLOR CHANGE", or "BREAK"
    }

    /**
     * Builds a PixelMap for the wall using the given dimensions and color.
     *
     * @param w the width in pixels
     * @param h the height in pixels
     * @param c the color of the wall
     * @return a PixelMap of the specified dimensions and color
     */
    private static PixelMap createPixelMap(int w, int h, Color c) {
        ArrayList<Pixel> pixels = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                pixels.add(new Pixel(i, j, c));
            }
        }
        return new PixelMap(pixels);
    }

    /**
     * Updates the wall each game tick by reducing the cooldown timer.
     */
    @Override
    public void update() {
        cooldownTimer--;
        if (cooldownTimer < 0) cooldownTimer = 0;
    }

    /**
     * Responds to a collision based on the wall's current mode.
     * - "COLOR CHANGE": toggles color
     * - "BREAK": removes intersecting pixels
     * - "NORMAL": no response
     *
     * @param e the element that collided with this wall
     */
    @Override
    public void reactToCollision(Element e) {
        if (cooldownTimer > 0) return; // exit early if in cooldown period

		/*
        if (mode.equals("NORMAL")) {
					 ArrayList<Pixel> pixels  = getPixelMap().getRelativePixels();
					 for (Pixel p: pixels){
					 		if (p.getColor().equals(brown))
					 			p.setColor(redBrown);
					 		else
					 			p.setColor(brown);
						}

            setPixelMap(new PixelMap(pixels));
        }*/

        if (mode.equals("BREAK")) {
			if(e instanceof Enemy){
				ArrayList<Pixel> toDestroy = InteractionHandler.getIntersectingPixels(e, this);
				destroyPixels(toDestroy);
				e.markToRemove(); // this would cause the thing that hits wall to disappear
			}
        }

        // No action for "NORMAL"
    }

    /**
     * Removes specified pixels from the wall's pixel map.
     *
     * @param pixels the list of pixels to remove from the wall
     */
    public void destroyPixels(ArrayList<Pixel> pixels) {
        ArrayList<Pixel> newPixels = getPixelMap().getRelativePixels();
        if (newPixels.removeAll(toRelativePixels(pixels))) {
            System.out.println("Removed "+pixels.size()+" pixels");
        }
        setPixelMap(new PixelMap(newPixels));
    }
}
