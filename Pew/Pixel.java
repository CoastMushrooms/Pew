import java.awt.Color;

/**
 * The Pixel class represents a single colored square on the grid.
 * Each pixel has a position (x, y) and a color.
 */
public class Pixel {
    private Color color;
    private int x, y;

    /**
     * Creates a new Pixel with the specified position and color.
     *
     * @param x the horizontal position
     * @param y the vertical position
     * @param color the color of the pixel
     */
    public Pixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /** ACESSOR METHODS */

    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }

		/** MUTATOR */
    public void setColor(Color color) { this.color = color; }


		/**  Custom equals and hashCodeallow pixels with the same x and y
		  *  value to be considered the same regardless of Color
		  */
    @Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || getClass() != obj.getClass()) return false;
		    Pixel other = (Pixel) obj;
		    return this.x == other.x && this.y == other.y;
		}

		@Override
		public int hashCode() {
		    return 31 * x + y;
		}


}

