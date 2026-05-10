import java.util.ArrayList;
import java.awt.Color;
/* This class is intended as a starting point for students to explore how
 * characters can move, animate, and respond to collisions in a 2D grid.
 */
public class Example extends Element implements CollisionReactor {
    private int xVelocity, yVelocity;
    private ArrayList<Blast> blasts;
    private HealthBar health;
    private Color[] customColors = {new Color(0, 0, 0),
									new Color(255, 255, 255),
									new Color(69, 83, 84),
									new Color(57, 42, 24),
									new Color(187, 128, 56),
									new Color(255, 231, 3),
									new Color(22, 112, 41)};

    // Cool down is used to temporarily turn off reactions to avoid glitches
    // resulting from repeated actions
    private int cooldownTimer = 0;
    private int cooldownSetPoint = 0; // 0 means not being used

    // A sample circular shape made of numbers for PixelMapFactory parsing
    private static String[] ball = {
        "....0000000  ",
        "...000000000",
        "...000500000",
        "...005550050",
        "55.052652650.55",
        "55..5555555..55",
        ".77.5555555.77",
        ".777..555..777",
		"..77777777777",
		"...777777777",

    };

    /**
     * Constructs an Example with a predefined pixel plane.
     *

     * @param x starting x position
     * @param y starting y position
     */
    public Example(int x, int y) {
        super(x, y, PixelMapFactory.fromStringArray(ball));
        xVelocity = 0;
        yVelocity = 0;
        blasts = new ArrayList<Blast>();
        health = new HealthBar();
    }

	public int getHealth(){
		return health.getHLevel();
	}

	public HealthBar getHealthBar() {
		return health;
	}
    /**
     * Alternate constructor for Example using two custom colors.
     *
     * @param x starting x position
     * @param y starting y position
     * @param color1 first color used in the pattern
     * @param color2 second color used in the pattern
     */
    public Example(int x, int y, Color color1, Color color2) {
        super(x, y, createPixelMap(color1, color2));
    }

    /**
     * Creates a small hardcoded shape using alternating colors.
     * This demonstrates building a PixelMap programmatically.
     */
    private static PixelMap createPixelMap(Color color1, Color color2) {
        ArrayList<Pixel> pixels = new ArrayList<Pixel>();
        pixels.add(new Pixel(0, 0, color1));
        pixels.add(new Pixel(1, 0, color2));
        pixels.add(new Pixel(2, 0, color1));
        pixels.add(new Pixel(0, 1, color2));
        pixels.add(new Pixel(1, 1, color1));
        pixels.add(new Pixel(2, 1, color2));
        pixels.add(new Pixel(0, 2, color1));
        pixels.add(new Pixel(1, 2, color2));
        pixels.add(new Pixel(2, 2, color1));
        return new PixelMap(pixels);
    }

	public void moveDown() {
		yVelocity = 1;
	}
	public void moveUp() {
		yVelocity = -1;
	}
	public void moveRight() {
		xVelocity = 1;
	}
	public void moveLeft() {
		xVelocity = -1;
	}

    /**
     * Updates the character's position based on direction.
     * Also handles animation frame switching and screen edge bouncing.
     */
    @Override
    public void update() {
				setX(getX() + xVelocity);
        setY(getY() + yVelocity);
				enforceBounds();

        cooldownTimer--;
        if (cooldownTimer < 0 ) cooldownTimer = 0;
    }

    private void enforceBounds(){

          while (getY() <= 0 || getBottom() >= getGridRows() || getX() <= 0 || getRightEdge() >= getGridCols()){
						//System.out.println("MOVING AWAY FROM WALL XVel = "+xVelocity+", YVel = "+yVelocity);
						setX(getX() + xVelocity);
        		setY(getY() + yVelocity);
        		if  (xVelocity <0 && getX() <= 0)
        			xVelocity = Math.abs(xVelocity);
        		if (xVelocity>0 && getRightEdge() >= getGridCols())
        			xVelocity = -Math.abs(xVelocity);
        		if (yVelocity <0 && getY() <= 0)
        			yVelocity = Math.abs(yVelocity);
        		if (yVelocity>0 && getBottom() >= getGridRows())
        			yVelocity = -Math.abs(yVelocity);
					}
		}

    /**
     * Handles collision with another element.
     * Reverses direction and removes from collision zone.

    @Override
    public void reactToCollision(Element e) {
        if (cooldownTimer > 0) return; //return early if in cooldown
        String dir = InteractionHandler.collisionDirection(this,e);
        //System.out.println("------->"+dir);
        if (dir.equals("LEFT") || dir.equals("RIGHT")){
          xVelocity *= -1;
        }
        else if (dir.equals("ABOVE") || dir.equals("BELOW")) {
          yVelocity *= -1;
        }
        else { // Multiple
          xVelocity *= -1;
        }
        while (InteractionHandler.isOverlapping(this,e)){
          setX(getX() + xVelocity);
          setY(getY() + yVelocity);
         }
        cooldownTimer = cooldownSetPoint;
    }
  */

  	/**
  	 * Handles collision with another element.
  	 */
  	@Override
  	public void reactToCollision(Element e) {
  		if (e instanceof Enemy) {
				health.changeHealth(5);
				e.markToRemove();
  		}


  		if (e instanceof Boss){
			health.changeHealth((int)(Math.random() * 5 + 10));
			//e.markToRemove();
	    }

	    if (e instanceof Wall){
			setX(getX() - xVelocity);
			setY(getY() - yVelocity);
			stop();
		}

		if(e instanceof blastEnemy){
			health.changeHealth(7);
			e.markToRemove(); // remove blast after it hits player
		}
	}
    /**
     * Returns a string representation of this character's direction and position.
     */

    public void shootBlaster(){
		blasts.add(new Blast(this.getRightEdge(), this.getY()+8));
	}

	public ArrayList<Blast> getBlasts(){
	    removeOldBlasts();  // clean up before returning
	    return blasts;
	}

	private void removeOldBlasts(){
	    for (int i = blasts.size()-1; i >= 0; i--){
	    	if (blasts.get(i).removeMe())
	        	blasts.remove(i);
		}
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "@(" + xVelocity + ","+yVelocity+")[" + getX() + "," + getY() + "]";
    }

    public void stop(){
		xVelocity = 0;
        yVelocity = 0;
	}
}