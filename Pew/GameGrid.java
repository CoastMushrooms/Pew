import java.util.ArrayList;

/**
 * The GameGrid class is the MODEL in the MVC architecture.
 * It stores the state of the game world as a 2D array of pixel lists.
 * GamePanel (View) reads from this model to draw the game,
 * and GameEngine (Controller) updates it based on game logic.
 */
public class GameGrid {

    private ArrayList<Pixel>[][] grid;

    /**
     * Constructs a new GameGrid with the given number of rows and columns.
     *
     * @param rows the number of rows in the grid
     * @param cols the number of columns in the grid
     */
    @SuppressWarnings("unchecked")
    public GameGrid(int rows, int cols) {
        grid = new ArrayList[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new ArrayList<Pixel>();
    }

    /**
     * Returns the number of rows in the grid.
     *
     * @return number of rows
     */
    public int getNumRows() {
        return grid.length;
    }

    /**
     * Returns the number of columns in the grid.
     *
     * @return number of columns
     */
    public int getNumCols() {
        return grid[0].length;
    }

    /**
     * Clears all pixels from the grid.
     * This should be called before re-placing elements during updates.
     */
    public void clearGrid() {
        for (ArrayList<Pixel>[] row : grid)
            for (ArrayList<Pixel> cell : row)
                cell.clear();
    }

    /**
     * Places all pixels from the given element into the grid based on their absolute positions.
     *
     * @param e the element whose pixels should be placed
     */
    public void placeElement(Element e) {
        for (Pixel p : e.getAbsolutePixels()) {
            if (isValid(p.getX(), p.getY())) {
                grid[p.getY()][p.getX()].add(p);
            }
        }
    }

    /**
     * Returns the list of pixels at a given (x, y) location.
     *
     * @param x the column index
     * @param y the row index
     * @return the list of pixels at that location, or an empty list if out of bounds
     */
    public ArrayList<Pixel> getPixelsAt(int x, int y) {
        if (isValid(x, y))
            return grid[y][x];
        else
            return new ArrayList<Pixel>();
    }

    /**
     * Checks if a given (x, y) coordinate is within the grid bounds.
     *
     * @param x the column index
     * @param y the row index
     * @return true if the position is valid; false otherwise
     */
    private boolean isValid(int x, int y) {
        return y >= 0 && y < grid.length && x >= 0 && x < grid[0].length;
    }

    /**
     * Returns the entire 2D grid of pixel lists.
     *
     * @return the pixel grid
     */
    public ArrayList<Pixel>[][] getGrid() {
        return grid;
    }
}