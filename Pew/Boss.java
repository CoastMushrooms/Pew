import java.util.ArrayList;
import java.awt.Color;

public class Boss extends Element implements CollisionReactor {
    private int xVelocity, yVelocity;
	private int counter = 0;
	private int countDown;
	private ArrayList<blastEnemy> blasts;
    // Cool down is used to temporarily turn off reactions to avoid glitches
    // resulting from repeated actions
    private int cooldownTimer = 0;
    private HealthBar health;
    private int cooldownSetPoint = 0; // 0 means not being used

    // A sample circular shape made of numbers for PixelMapFactory parsing
    private static String[] enemy = {
        "..3333333 ",
        ".333333333 ",
        "33333333333",
        "33553335533",
        "33553335533",
        "33333333333",
        ".333333333 ",
        "..3333333 "
     };

    /**
     * Constructs an Enemy with a predefined pixel plane.
     *

     * @param x starting x position
     * @param y starting y position
     */
    public Boss(int x, int y) {
        super(x, y, PixelMapFactory.fromStringArray(enemy));
        yVelocity = -2;
        blasts = new ArrayList<blastEnemy>();
        health = new HealthBar();
    }

	public int getHealth(){
		return health.getHLevel();
	}

	public HealthBar getHealthBar() {
			return health;
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
		  /*countDown = System.currentTimeMillis();
		  if(countDown % 5 == 0){
			  shootBlaster();
			  ArrayList<Blast> blasting = getBlasts();
			  for (Element b : blasting){
			  		addElement(b);
			  }
		  } DO IT IN THE GAME ENGINE*/

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
     */

	@Override
	public void reactToCollision(Element e) {
		if (cooldownTimer > 0) return; //return early if in cooldown

	    if (e instanceof Blast){
	    	health.changeHealth(15);
	    }

		if (e instanceof Wall){
			xVelocity *= -1;
	    }

	    cooldownTimer = cooldownSetPoint;
    }

	public void slamMode(){
		yVelocity = 0;
		xVelocity = -4;
	}

	public void normalMode(){
		yVelocity = -2;
		xVelocity = 0;
	}

	public void shootBlaster(){
			blasts.add(new blastEnemy(this.getLeftEdge(), this.getY()+8));
	}

	public ArrayList<blastEnemy> getBlasts(){
		    removeOldBlasts();  // clean up before returning
		    return blasts;
	}

	private void removeOldBlasts(){
		    for (int i = blasts.size()-1; i >= 0; i--){
		    	if (blasts.get(i).removeMe())
		        	blasts.remove(i);
			}
    }

    /**
     * Returns a string representation of this character's direction and position.
     */
    @Override
    public String toString() {
        return this.getClass().getName() + "@(" + xVelocity + ","+yVelocity+")[" + getX() + "," + getY() + "]";
    }
}