import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * The GameEngine class serves as the CONTROLLER in the MVC framework.
 * It manages the game loop, handles input from the keyboard and mouse,
 * updates the game state, and communicates with the GamePanel (View) to repaint the screen.
 */
public class GameEngine implements ActionListener, KeyListener, MouseListener {
    private GameGrid grid;
    private ArrayList<Element> elements;
    private GamePanel panel;
    private Timer timer;
    private Example player;
    private Boss boss;
    private Boss bs;
    private long startTime;
    private int currentLevel = 0; // index of array below
	private String[] levelFiles = {"level1.txt", "level2.txt", "level3.txt"};
	long elapsedTimeSec;
	private int levelCount = 0;
	long twoTime;
	private int shots;

    /**
     * Constructs a GameEngine to control game updates and rendering.
     *
     * @param grid the model representing the game's logical state
     * @param panel the panel responsible for displaying the grid
     * @param fps the desired updates per second
     */

    public GameEngine(GameGrid grid, GamePanel panel, int fps) {
        this.grid = grid;
        this.panel = panel;
        panel.setIntroMode(true);
        panel.setEndMode1(false);
        panel.setEndMode2(false);
        this.elements = new ArrayList<>();
        this.timer = new Timer(1000/fps, this); // timer interval in ms
    }

	public Element getPlayer() {
		return player;
	}

	public Element getBoss() {
		return boss;
	}

	public void loadNextLevel() {
	    currentLevel= (currentLevel+1)%levelFiles.length;
	    levelCount++;
	    elements.clear();         // clear existing elements
	    grid.clearGrid();         // clear grid pixels
	    String filename = levelFiles[currentLevel];
	    System.out.println("About to load "+filename);
	    LevelLoader.load(filename, this); // reload from file
	    pause(1000);
	}

    /**
     * Starts the main game loop using a Swing Timer.
     */
    public void start() {

	  if (panel.isIntroMode()) // only do this once
	    placeElementsOnIntroScreen();

	    while (panel.isIntroMode()) // keep painting mode change
	          panel.repaint();

	    timer.start();  // begin the actual game
	    startTime = System.currentTimeMillis();
	}

	public void placeElementsOnIntroScreen(){
	      // Puts things on screen without including in game loop
	      grid.placeElement(new Example(80,50));
	      grid.placeElement(new Blast(100,58));
	      grid.placeElement(new Enemy(110,55));
    }

	public boolean levelUp(){
		return elapsedTimeSec > 5;
	}

    /**
     * Adds a new element to the game and sets its grid size reference.
     *
     * @param e the element to add
     */
    public void addElement(Element e) {
        e.setGridSize(grid.getNumRows(), grid.getNumCols());
        elements.add(e);
    }

    /**
     * Sets the player-controlled element for keyboard interaction.
     *
     * @param e the element controlled by the player
     */
    public void setPlayer(Element e) {
        player = (Example)e;
        panel.setPlayer(player);
    }

    public void setBoss(Element b){
		if(currentLevel == 2){
			boss = (Boss)b;
			bs = (Boss)b;
			panel.setBoss(boss);
		}
	}

	public int getEnding() {
		if(player.getHealth() < 1)// check conditions such as score, timer, health, life
			return 1;
		if(currentLevel == 2){
			if(boss.getHealth() < 1)
				return 2;
		}
		return 0;
	}

    /**
     * Called on every timer tick. Updates all elements, resolves collisions,
     * and repaints the game panel.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
			//add the blast for boss
			if(levelCount != 2)
				elapsedTimeSec = (System.currentTimeMillis() - startTime) / 1000;
			else{
				elapsedTimeSec = 0;
				twoTime = (System.currentTimeMillis() - startTime) / 1000;
			}

			if(twoTime % 3 == 0&& shots == 0){
				shots = 0;
				if(levelCount == 2 && shots == 0){
					Boss bs2 = (Boss)boss;
					bs2.shootBlaster();
					ArrayList<blastEnemy> blasts = bs2.getBlasts();
					for (Element b : blasts){
						if (!elements.contains(b))
							addElement(b);
					}
					shots = 1;
				}
			}
			if(twoTime % 3 != 0)
				shots = 0;
			/*
			if(levelCount == 2){
				int timeThree = 0;
				if(twoTime % 15 == 0){
					boss.slamMode();
				}
				if(timeThree % 4 == 0)
					boss.normalMode();
				timeThree++;
			}*/

	        grid.clearGrid();

	        for (int i=0; i < elements.size(); i++) {
	            Element el = elements.get(i);
	            if (el.removeMe()){
	              elements.remove(i);
	              i--;
	              continue;  // ingnore rest of loop and proceed to next element
	            }

	            el.update(); // element-specific logic

	            Element intersecting = InteractionHandler.getIntersecting(el, elements);
	            if (intersecting != null){
	              if (intersecting instanceof CollisionReactor)
	                ((CollisionReactor)intersecting).reactToCollision(el);
	              //if (el instanceof CollisionReactor)
	              //  ((CollisionReactor)el).reactToCollision(intersecting);
	            }
	            grid.placeElement(el);
	            if (levelUp()){
					elapsedTimeSec = 0;
					startTime = System.currentTimeMillis();
					if(currentLevel < 2){
						System.out.println(currentLevel);
						loadNextLevel();
					}
				}
	        }

			if(levelCount < 2){
				int randChance = (int)(Math.random()*10000);  //random num under 10,0000
				int randomYPos = (int)(Math.random()*(grid.getNumRows()-20))+1;
				if (randChance < 100){  // one in 100 chance of making new Enemy
				  addElement(new Enemy(200,randomYPos));
				}
			}

	        Example xample = (Example)player;
	        if (boss != null)
	        	bs = (Boss)boss;

	        if(levelCount < 2)
	        	panel.setTopMessage("Player Health: " + xample.getHealth()+ " | Time: " + elapsedTimeSec);

			if(levelCount == 2 && boss != null){
				panel.setTopMessage("Player Health: " + xample.getHealth() + " | Time: Unlimited                                                                                          Boss Health: " + bs.getHealth());
			}

	        if (levelUp()){
			  elapsedTimeSec = 0;
			  startTime = System.currentTimeMillis();
			  if(levelCount < 2)
	          	loadNextLevel();
	        }

	        panel.repaint();
				if (getEnding() != 0){
					timer.stop(); // This will stop all motion on the screen
					elements.clear();
					panel.repaint();
					if (getEnding() == 1)
						panel.setEndMode1(true);
					if (getEnding() == 2)
						panel.setEndMode2(true);
				}
    }

	public int toPixelX(int screenX){
		return screenX/panel.getPixelSize();
	}

	public int toPixelY(int screenY){
		return screenY/panel.getPixelSize();
	}

	public void pause(int milliseconds) {  // Stops the game from refreshing for milliseconds
	    timer.stop();
	    try {
	        Thread.sleep(milliseconds);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt(); // good practice to restore interrupt status
	    }
	    timer.start();
	}

    /**
     * Handles key presses to control player object
     *
     * @param e the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Event => "+e.getKeyCode());
        Example t = (Example)player;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) { //down arrow
			t.moveDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) { //down arrow
			t.moveUp();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) { //down arrow
			t.moveLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) { //down arrow
			t.moveRight();
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){ // Space Bar
			Example xample = (Example)player;
			xample.shootBlaster();
		    ArrayList<Blast> blasts = xample.getBlasts();
		    for (Element b : blasts){
		    	if (!elements.contains(b))
		      		addElement(b);
			}
        }

        if (panel.isIntroMode()){
		  panel.setIntroMode(false);
		  return;
		}
    }

    @Override public void keyReleased(KeyEvent e) {
		Example t = (Example)player;
		t.stop();
	}

    @Override public void keyTyped(KeyEvent e) {}

    /**
     * Handles mouse clicks (currently logs position to console).
     *
     * @param e the mouse event
     */
    @Override public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());

    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
