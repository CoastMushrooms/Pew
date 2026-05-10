import java.awt.Color;
import java.util.ArrayList;

public class PixelMapFactory {

    // Static color mapping where index represents the number in the input string
    private static final Color[] COLORS = {
        Color.BLACK,      // 0
        Color.RED,        // 1
        Color.BLUE,       // 2
        Color.GREEN,      // 3
        Color.YELLOW,     // 4
        Color.ORANGE,     // 5
        Color.MAGENTA,    // 6
        Color.CYAN,       // 7
        Color.PINK,       // 8
        Color.WHITE       // 9
    };

    /**
     * Creates a PixelMap from an array of strings. Each character in the strings
     * should be a digit from 0-9. OR '.' to indicate no pixel.
     *
     * @param rows an array of strings like {"1112", "0002", ...}
     * @return a PixelMap object containing the generated pixels
     */
    public static PixelMap fromStringArray(String[] rows) {
        ArrayList<Pixel> pixels = new ArrayList<>();

        for (int y = 0; y < rows.length; y++) {
            String row = rows[y];
            for (int x = 0; x < row.length(); x++) {
								char ch = row.charAt(x);
            		if (!Character.isDigit(ch))
            			continue; //not a color code, move to next
                int code = Character.getNumericValue(ch);
                if (code >= 0 && code < COLORS.length) {
                    pixels.add(new Pixel(x, y, COLORS[code]));
                }
            }
        }

        return new PixelMap(pixels);
    }

    /**
		     * Creates a PixelMap from an array of strings. Each character in the strings
		     * should be a digit from 0-9. OR '.' to indicate no pixel. This will
		     * use a cusom color array as a parameter.
		     *
		     * @param rows an array of strings like {"1112", "0002", ...}
		     * @return a PixelMap object containing the generated pixels
		     */
		    public static PixelMap fromStringArray(String[] rows, Color[] colors) {
		        ArrayList<Pixel> pixels = new ArrayList<>();

		        for (int y = 0; y < rows.length; y++) {
		            String row = rows[y];
		            for (int x = 0; x < row.length(); x++) {
										char ch = row.charAt(x);
		            		if (!Character.isDigit(ch))
		            			continue; //not a color code, move to next
		                int code = Character.getNumericValue(ch);
		                if (code >= 0 && code < colors.length) {
		                    pixels.add(new Pixel(x, y, colors[code]));
		                }
		            }
		        }

		        return new PixelMap(pixels);
    }
}
