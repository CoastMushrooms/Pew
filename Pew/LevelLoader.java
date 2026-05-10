import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The LevelLoader class reads a level definition file and adds game elements
 * to a provided GameEngine instance. The file is expected to be in a simple,
 * comma-separated format, one line per element.
 *
 * Each line represents a single element with the following supported formats:
 *
 * - BALL,x,y                adds a Ball at position (x, y)
 * - WALL,x,y,width,height   adds a Wall at position (x, y) with the given dimensions
 *
 * Lines starting with # are treated as comments and ignored.
 * Blank or malformed lines are also skipped.
 */

public class LevelLoader {

	/**
	 * Loads elements from the specified level file and adds them to the given GameEngine.
	 *
	 * @param filename the path to the level file (e.g., "level1.txt")
	 * @param engine   the GameEngine to populate with elements from the file
	 */
    public static void load(String filename, GameEngine engine) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip comments and empty lines
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String type = parts[0].trim();
                int x = Integer.parseInt(parts[1].trim());
                int y = Integer.parseInt(parts[2].trim());

                /*
                if (type.equalsIgnoreCase("BALL")){
					engine.addElement(new Ball(x, y));
				}
				*/

				if (type.equalsIgnoreCase("EXAMPLE")){
					Example e = new Example(x, y);
					engine.addElement(e);
					engine.setPlayer(e);
					//engine.addElement(e.getHealthBar());
				}

				if (type.equalsIgnoreCase("BOSS")){
					Boss b = new Boss(x, y);
					engine.addElement(b);
					engine.setBoss(b);
					//engine.addElement(b.getHealthBar());
				}

				if (type.equalsIgnoreCase("WALL") && parts.length > 4){
						int w = Integer.parseInt(parts[3].trim());
			int h = Integer.parseInt(parts[4].trim());
							engine.addElement(new Wall(x, y, w, h));
				}
								// Add more like this to tload additional objects


            }
        } catch (IOException e) {
            System.err.println("Error loading level: " + e.getMessage());
        }
    }

}