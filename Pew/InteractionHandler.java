/**
 * The InteractionHandler class provides utility methods for detecting
 * when two elements in the game are overlapping.
 */
import java.util.ArrayList;
public class InteractionHandler {

    /**
     * Finds ALL elements in the list that are currently overlapping
     * with the given element based on their pixel positions.
     *
     * @param current the element to check for overlaps
     * @param allElements a list of all elements in the game
     * @return a list of elements that are intersecting with the current one
		 */
    public static ArrayList<Element> getAllIntersecting(Element current, ArrayList<Element> allElements) {
        ArrayList<Element> intersecting = new ArrayList<Element>();

        for (Element other : allElements) {
            if (other == current) continue;

            if (isOverlapping(current, other))
							intersecting.add(other);
        }
        return intersecting;
    }


		public static boolean isOverlapping(Element a, Element b){
			return !getIntersectingPixels(a,b).isEmpty();
		}


    /**
         * Finds a single element in the list that is currently overlapping
         * with the given element based on their pixel positions.
         * If multiple are overlapping the first one found will be arbitrary
         *
         * @param current the element to check for overlaps
         * @param allElements a list of all elements in the game
         * @return single element that is intersecting with the current one
       */
        public static Element getIntersecting(Element current, ArrayList<Element> allElements) {
           for (Element e : allElements){
            if (e.equals(current)) continue;
           	if (isOverlapping(current,e))
           		return e;

						}
            return null;
    		}

    /**
     * Returns a list of pixels on the target that intersect with pixels from the projectile.
     *
     * @param projectile the moving element (e.g., bullet, bomb)
     * @param target     the element being hit (e.g., enemy, wall)
     * @return a list of overlapping pixels from the target
     */
    public static ArrayList<Pixel> getIntersectingPixels(Element projectile, Element target) {
			ArrayList<Pixel> p = projectile.getAbsolutePixels();
			ArrayList<Pixel> t = target.getAbsolutePixels();
			t.retainAll(p);
			return t;
		}



/**
 * Estimates the direction from which a projectile collided with a target.
 * Returns one of: "LEFT", "RIGHT", "ABOVE", "BELOW" or "MULTIPLE"
 */
public static String collisionDirection(Element projectile, Element target) {
    ArrayList<Pixel> projPixels = projectile.getAbsolutePixels();
    int[] pos = new int[4]; //Right,Left,Above,Below

    int tLeft = target.getX();
    int tRight = target.getRightEdge();
    int tTop = target.getY();
    int tBottom = target.getBottom();

    for (Pixel p1 : projPixels) {
        if (p1.getX() >= tRight) pos[0]++;     // to the right of target
        else if (p1.getX() < tLeft) pos[1]++;  // to the left
        else if (p1.getY() < tTop) pos[2]++;   // above
        else if (p1.getY() >= tBottom) pos[3]++; // below
    }

    // Debug print (Uncomment below)
    System.out.println("Right = " + pos[0] + " | Left = " + pos[1] + " | Above = " + pos[2] + " | Below = " + pos[3]);

    // Decide based on majority vote
    int max = pos[0];
    int dir = 0;
    for (int i = 1; i < 4; i++) {
        if (pos[i] > max) {
            max = pos[i];
            dir = i;
        }
    }

    switch (dir) {
        case 0: return "RIGHT";
        case 1: return "LEFT";
        case 2: return "ABOVE";
        case 3: return "BELOW";
    }
    return "MULTIPLE";
}



}