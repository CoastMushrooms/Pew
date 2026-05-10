import java.awt.Color;
import java.util.ArrayList;


public class blastEnemy extends Element{

    private int xVelocity = -4;

    public blastEnemy(int x, int y) {
        super(x, y, createPixelMap());
    }

    /* Move Horizontal, remove if off screen */
    @Override
    public void update() {
        setX(getX()+xVelocity);
        if (getX() < 0){
          markToRemove();

        }
    }

   /**
     * Creates a small hardcoded shorizontal white line.
     */
    private static PixelMap createPixelMap() {
        ArrayList<Pixel> pixels = new ArrayList<Pixel>();
        pixels.add(new Pixel(0, 0, Color.WHITE));
        pixels.add(new Pixel(1, 0, Color.WHITE));
        return new PixelMap(pixels);
    }
}