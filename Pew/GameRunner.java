import javax.swing.JFrame;
import java.awt.Color;

/**
 * GameRunner serves as the entry point for the game application.
 * It sets up the main window, initializes the game model (GameGrid),
 * the view (GamePanel), and the controller (GameEngine), and loads
 * elements from a level file.

 *
 * This class should be run to launch the game.
 */
public class GameRunner {
    public static void main(String[] args) {
        // Create the main application window
        JFrame frame = new JFrame("Pixel Grid Game");

        // Create the game grid (MODEL) with 120 rows and 200 columns
        GameGrid grid = new GameGrid(120, 200);

        // Create the game panel (VIEW) using the grid and setting pixel size to 6
        GamePanel panel = new GamePanel(grid, 6);

        // Create the game engine (CONTROLLER) linking model and view at 60 FPS
        GameEngine engine = new GameEngine(grid, panel, 60);

        // Set up the frame to close on exit, add panel to it, and show it
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        // Attach the game engine as a KeyListener for user input
        frame.addKeyListener(engine);
        frame.addMouseListener(engine);

        // Load elements (like Ball and Wall) into the engine from a level file
        // Format of level1.txt:
        //   BALL,x,y
        //   WALL,x,y,width,height
        LevelLoader.load("level1.txt", engine);

        // Start the game loop (runs updates and redraws screen continuously)
        engine.start();
    }
}

