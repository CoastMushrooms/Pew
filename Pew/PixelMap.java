import java.awt.Color;
import java.util.ArrayList;

/**
 * The PixelMap class represents the shape and structure of a game element using
 * a list of Pixels defined by their relative positions.
 *
 * Each PixelMap is meant to be attached to an Element and determines how that
 * element will be rendered in the game grid.
 */
public class PixelMap {
    private ArrayList<Pixel> relativePixels;

    /**
     * Constructs a PixelMap using a list of pixels.
     * Each Pixel should have its x and y set relative to the top-left of the element.
     *
     * @param relativePixels an ArrayList of Pixel objects representing the shape
     */
    public PixelMap(ArrayList<Pixel> relativePixels) {
        this.relativePixels = relativePixels;
    }

    /**
     * Returns the list of relative pixels that make up this PixelMap.
     *
     * @return the ArrayList of relative Pixels
     */
    public ArrayList<Pixel> getRelativePixels() {
        return relativePixels;
    }

    /**
     * Calculates the height (number of rows) of the bounding box needed to
     * contain all the pixels in this map.
     *
     * @return the height of the map
     */
    public int calculateHeight() {
        int max = 0;
        for (Pixel p : relativePixels) {
            if (p.getY() > max)
                max = p.getY();
        }
        return max + 1;
    }

    /**
     * Calculates the width (number of columns) of the bounding box needed to
     * contain all the pixels in this map.
     *
     * @return the width of the map
     */
    public int calculateWidth() {
        int max = 0;
        for (Pixel p : relativePixels) {
            if (p.getX() > max)
                max = p.getX();
        }
        return max + 1;
    }

    @Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || getClass() != obj.getClass()) return false;

		    PixelMap other = (PixelMap) obj;
		    return relativePixels.equals(other.relativePixels);
		}


}
