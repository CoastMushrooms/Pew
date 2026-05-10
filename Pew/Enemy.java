import java.util.ArrayList;
import java.awt.Color;
/**
 * Enemy is a sample Element that demonstrates animation,
 * direction changes, basic collision response, and multiple visual states.
 *
 * This class is intended as a starting point for students to explore how
 * characters can move, animate, and respond to collisions in a 2D grid.
 */
public class Enemy extends Element implements CollisionReactor {
    private int xVelocity, yVelocity;
    private ArrayList<blastEnemy> blasts;

    // Cool down is used to temporarily turn off reactions to avoid glitches
    // resulting from repeated actions
    private int cooldownTimer = 0;
    private int cooldownSetPoint = 0; // 0 means not being used

    // A sample circular shape made of numbers for PixelMapFactory parsing
    private static String[] enemy = {
        "..8888888 ",
        ".999999999 ",
        "88008880088",
        "99999999999",
        ".888888888 ",
        "..9999999 "
     };

    /**
     * Constructs an Enemy with a predefined pixel plane.
     *

     * @param x starting x position
     * @param y starting y position
     */
    public Enemy(int x, int y) {
        super(x, y, PixelMapFactory.fromStringArray(enemy));
        xVelocity = -2;
        blasts = new ArrayList<blastEnemy>();
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
     */

	@Override
	public void reactToCollision(Element e) {
		if (cooldownTimer > 0) return; //return early if in cooldown

	    if (e instanceof Blast){
	    	this.markToRemove(); // remove Enemy AND Blast
	        e.markToRemove();
	    }

	    if (e instanceof Wall){
			xVelocity *= -1;
		}

	    cooldownTimer = cooldownSetPoint;
    }

	public void thirdGone(Element e){
		e.markToRemove();
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