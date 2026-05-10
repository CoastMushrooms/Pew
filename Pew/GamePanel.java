import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

/**
 * The GamePanel class is the VIEW in the MVC architecture.
 * It is responsible for rendering the current state of the game grid to the screen.
 * It displays each pixel of each element in the correct position and color.
 */
public class GamePanel extends JPanel {
    private GameGrid gameGrid;
    private int pixelSize;
    private Element player;
    private Element boss;
    private String topMessage;
    private boolean introMode;
    private boolean endMode1;
    private boolean endMode2;

    /**
     * Constructs a new GamePanel that will display the contents of the provided GameGrid.
     *
     * @param grid       the game grid containing all pixel data
     * @param pixelSize  the size (in pixels) of each grid square to draw
     */

   public boolean isIntroMode(){
		return introMode;
	}

	public boolean isEndMode1(){
			return endMode1;
	}

	public boolean isEndMode2(){
			return endMode2;
	}

	public void setIntroMode(boolean introMode){
		this.introMode = introMode;
	}

	public void setEndMode1(boolean endMode1){
			this.endMode1 = endMode1;
	}

	public void setEndMode2(boolean endMode2){
				this.endMode2 = endMode2;
	}

    public GamePanel(GameGrid grid, int pixelSize) {
        this.gameGrid = grid;
        this.pixelSize = pixelSize;
        this.topMessage = "";
        setPreferredSize(new Dimension(
            grid.getGrid()[0].length * pixelSize,
            grid.getGrid().length * pixelSize));
    }

   	/**
     * Designates a Player object so infor about them can be displayed
     */
    public void setPlayer(Element player) {
		    this.player = player;
		}

	public void setBoss(Element boss){
		this.boss = boss;
	}

	public void setTopMessage(String topMessage){
		this.topMessage = topMessage;
	}

    /**
     * Called automatically whenever the panel should be redrawn.
     * Paints the background and all visible pixels from the game grid.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

		if (introMode){
			paintIntroScreen(g);
			return;
		}

		if (endMode1){
			paintBadEndScreen(g);
			return;
		}

		if (endMode2){
			paintGoodEndScreen(g);
			return;
		}

        // Paint background sky blue
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,
            gameGrid.getGrid()[0].length * pixelSize,
            gameGrid.getGrid().length * pixelSize);

        // Show Score or other game info
				Font font = new Font("Serif", Font.BOLD, 24);
				g.setFont(font);
				g.setColor(Color.WHITE);
				g.drawString(topMessage,3*pixelSize,3*pixelSize);

	    // paint health bar


        // Paint each pixel in the grid
        ArrayList<Pixel>[][] grid = gameGrid.getGrid();
        for (int r = 0; r < grid.length; r++) {
					for (int c = 0; c < grid[r].length; c++) {
						for (Pixel p : grid[r][c]) {
								g.setColor(p.getColor());
								g.fillRect(c * pixelSize, r * pixelSize, pixelSize, pixelSize );
						}
					}
        }
    }

    private void paintIntroScreen(Graphics g){
	  // Paint background
	  // Try https://rgbcolorpicker.com/
	  g.setColor(Color.BLACK); // Dark Purple
	  g.fillRect(0, 0,
	  gameGrid.getGrid()[0].length * pixelSize,
	  gameGrid.getGrid().length * pixelSize);

	  // Title:  Use a Big Font
	  Font titleFont = new Font("Serif", Font.BOLD, 60);
	  g.setFont(titleFont);
	  g.setColor(Color.WHITE);
	  g.drawString("Town Hunt",3*pixelSize,10*pixelSize); // String, starting x, starting y

	  // Overview: Medium Font
	  Font bigFont = new Font("Serif", Font.BOLD, 36);
	  g.setFont(bigFont);
	  g.drawString("Survive until time is up!",3*pixelSize,20*pixelSize); // String, starting x, starting y
	  g.drawString("Blast enemies!",3*pixelSize,30*pixelSize);
	  g.drawString("Don't lose all your hp!",3*pixelSize,40*pixelSize);

	  // Overview: Controls
	  Font regularFont = new Font("Serif", Font.BOLD, 20);
	  g.setFont(regularFont);
	  g.setColor(Color.WHITE); // Fixed: was BLACK on BLACK background
	  g.drawString("Press arrows to move",3*pixelSize,50*pixelSize); // String, starting x, starting y
	  g.drawString("Press space to fire",3*pixelSize,58*pixelSize);

	  // Example Shape Drawing around the elements added for display by GameEngine
	  // OVAL
	  g.setColor(new Color(146, 252, 3)); // Bright Yellow
	  g.drawOval(75*pixelSize,45*pixelSize,52*pixelSize,25*pixelSize); // outline
	  g.setColor(new Color(232, 45, 116)); // Mid Gray
	  g.fillOval(77*pixelSize,46*pixelSize,48*pixelSize,23*pixelSize); ;// x,y, width, height

	  // POLYGON
	  // Define the coordinates of the polygon (in this case a triangle)
	  // the xPoints and yPoints are used in parallel as ordered pairs
	  g.setColor(new Color(156, 62, 249)); // Bright Violet
	  int[] xPoints = {3*pixelSize, 63*pixelSize, 123*pixelSize};
	  int[] yPoints = {100*pixelSize, 80*pixelSize, 100*pixelSize};
	  g.fillPolygon(xPoints, yPoints, xPoints.length); // last argument is # or points


	  // RECTANGLE
	  g.setColor(new Color(240, 176, 40)); // Pumpkin Orange
	  g.fillRect(3*pixelSize,95*pixelSize,120*pixelSize,15*pixelSize);// x,y, width, height
	  g.setColor(new Color(42, 99, 39)); // Dark Green
	  g.drawRect(3*pixelSize,95*pixelSize,120*pixelSize,15*pixelSize); // outline on top

	  // Write on top of Box
	  g.setFont(bigFont);
	  g.setColor(Color.BLACK);
	  g.drawString("Press Any Key to Begin",35*pixelSize,105*pixelSize);

	  // Paint each pixel in the grid (same code as in run loop
	  // can seperate into its own method
	  ArrayList<Pixel>[][] grid = gameGrid.getGrid();
	  for (int r = 0; r < grid.length; r++) {

		for (int c = 0; c < grid[r].length; c++) {
		  for (Pixel p : grid[r][c]) {
			  g.setColor(p.getColor());
			  g.fillRect(c * pixelSize, r * pixelSize, pixelSize, pixelSize );
		  }
		}
	  }
	}

	private void paintBadEndScreen(Graphics g){
		// Paint background
		// Try https://rgbcolorpicker.com/
		g.setColor(new Color(184, 182, 182)); // Grey
		g.fillRect(0,0,250*pixelSize,250*pixelSize);// x,y, width, height
		g.setColor(Color.BLACK); // Pale Blue
	  	g.drawString("GAME OVER",20*pixelSize,100*pixelSize); // String, starting x, starting y

	  	ArrayList<Pixel>[][] grid = gameGrid.getGrid();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++) {
				for (Pixel p : grid[r][c]) {
					g.setColor(p.getColor());
					g.fillRect(c * pixelSize, r * pixelSize, pixelSize, pixelSize );
				}
			}
	    }
	}

	private void paintGoodEndScreen(Graphics g){
		// Paint background
		// Try https://rgbcolorpicker.com/
		g.setColor(new Color(43, 194, 116)); // Grey
		g.fillRect(0,0,250*pixelSize,250*pixelSize);// x,y, width, height
		g.setColor(Color.BLACK); // Pale Blue
		g.drawString("YOU WIN",20*pixelSize,100*pixelSize); // String, starting x, starting y

		ArrayList<Pixel>[][] grid = gameGrid.getGrid();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++) {
				for (Pixel p : grid[r][c]) {
					g.setColor(p.getColor());
					g.fillRect(c * pixelSize, r * pixelSize, pixelSize, pixelSize );
				}
			}
		}
	}

	public int getPixelSize(){
		return pixelSize;
	}
}