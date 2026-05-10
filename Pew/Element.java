import java.util.ArrayList;

/**
 * The Element class represents a movable or static object in the game.
 * It acts as a base class for all game entities (e.g., characters, items).
 *
 * Each element has a position (x, y), size based on its PixelMap,
 * and knowledge of the grid dimensions in which it exists.
 *
 * Subclasses must implement the update() method, which defines behavior
 * for each game tick (e.g., movement or animations).
 */
public abstract class Element {
    private int x, y; // top-left of element
    private int height, width;
    private int gridRows;
    private int gridCols;
    private PixelMap pixelMap;
    private boolean removeMe;

    /**
     * Constructs an Element at the specified location with a given PixelMap.
     * The height and width are calculated from the PixelMap.
     *
     * @param x the horizontal position (left edge)
     * @param y the vertical position (top edge)
     * @param pixelMap the PixelMap defining the shape and appearance
     */
    public Element(int x, int y, PixelMap pixelMap) {
        this.x = x;
        this.y = y;
        this.pixelMap = pixelMap;
        height = pixelMap.calculateHeight();
        width = pixelMap.calculateWidth();
        removeMe = false;
    }

    /**
     * Sets the total number of rows and columns of the grid this element is placed in.
     * This allows the element to make decisions based on the world size.
     */
    public void setGridSize(int rows, int cols) {
        this.gridRows = rows;
        this.gridCols = cols;
    }

		/* Accessors and Mutators */
    public int getGridRows() { return gridRows; }
    public int getGridCols() { return gridCols; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getHeight() { return height; }
    public int getWidth() { return width; }

    public boolean removeMe() { return removeMe; }
    public void markToRemove() { removeMe = true; }

    /**
     * Returns the bottom y-coordinate of the element (y + height).
     */
    public int getBottom() {
        return y + height;
    }

    /**
     * Returns the rightmost x-coordinate of the element (x + width).
     */
    public int getRightEdge() {
        return x + width;
    }
    public int getLeftEdge() {
	        return x;
    }

    public PixelMap getPixelMap() { return pixelMap; }

    /**
     * Updates the pixel map for this element and recalculates dimensions.
     */
    public void setPixelMap(PixelMap pixelMap) {
        this.pixelMap = pixelMap;
        height = pixelMap.calculateHeight();
        width = pixelMap.calculateWidth();
    }

    /**
     * Returns a list of absolute-position Pixels for this element,
     * based on its top-left corner (x, y) and its relative PixelMap.
     */
    public ArrayList<Pixel> getAbsolutePixels() {
        ArrayList<Pixel> abs = new ArrayList<Pixel>();
        for (Pixel p : pixelMap.getRelativePixels()) {
            abs.add(new Pixel(x + p.getX(), y + p.getY(), p.getColor()));
        }
        return abs;
    }

    /**
		 * Converts a list of absolute-position Pixels to relative-position Pixels,
		 * based on this element's top-left corner (x, y).
		 *
		 * @param absPixels the list of absolute Pixels
		 * @return a list of Pixels with relative positions to this element
		 */
		public ArrayList<Pixel> toRelativePixels(ArrayList<Pixel> absPixels) {
		    ArrayList<Pixel> rel = new ArrayList<>();
		    for (Pixel p : absPixels) {
		        int relX = p.getX() - x;
		        int relY = p.getY() - y;
		        rel.add(new Pixel(relX, relY, p.getColor()));
		    }
		    return rel;
		}

		@Override
		public String toString(){
			return this.getClass().getName()+"@["+getX()+","+getY()+"]";
		}

    /**
     * Abstract method to define how this element behaves each game tick.
     * Subclasses must override this to implement movement or animation.
     */
    public abstract void update();
}