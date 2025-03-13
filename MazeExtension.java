/*
 * file name:      MazeExtension.java
 * author:         Alexander Zhelonkin
 * last modified:  11/27/23
 * 
 * How to run: java MazeExtension
 */

import java.awt.Graphics;
import java.util.Iterator;
import java.util.Random;
import java.util.LinkedList;

public class MazeExtension implements Iterable<CellExtension> {

    /**
     * An iterator which iterates through all the CellExtensions in the Maze row by row and
     * column by column.
     */
    public Iterator<CellExtension> iterator() {
        return new Iterator<CellExtension>() {
            int r, c;

            public boolean hasNext() {
                return r < getRows();
            }

            public CellExtension next() {
                CellExtension next = get(r, c);
                c++;
                if (c == getCols()) {
                    r++;
                    c = 0;
                }
                return next;
            }
        };
    }

    /**
     * The number of rows and columns in this Maze.
     */
    private int rows, cols;

    /**
     * The density of this Maze. Each CellExtension independently has probability
     * {@code density} of being an OBSTACLE.
     */
    private double density;

    /**
     * The 2-D array of CellExtensions making up this Maze.
     */
    private CellExtension[][] landscape;

    /**
     * Constructs a Maze with the given number of rows and columns. Each CellExtension
     * independently has probability {@code density} of being an OBSTACLE.
     * 
     * @param rows    the number of rows.
     * @param columns the number of columns.
     * @param density the probability of any individual CellExtension being an OBSTACLE.
     */
    public MazeExtension(int rows, int columns, double density) {
        this.rows = rows;
        this.cols = columns;
        this.density = density;
        landscape = new CellExtension[rows][columns];
        reinitialize();
    }

    /**
     * Initializes every CellExtension in the Maze.
     */
    public void reinitialize() {
        Random rand = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                landscape[r][c] = new CellExtension(r, c, rand.nextDouble() < density ? CellExtensionType.OBSTACLE : CellExtensionType.FREE);
            }
        }
    }

    /**
     * Calls {@code reset} on every CellExtension in this Maze.
     */
    public void reset() {
        for (CellExtension CellExtension : this)
            CellExtension.reset();
    }

    /**
     * Returns the number of rows in the Maze.
     * 
     * @return the number of rows in the Maze.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the Maze.
     * 
     * @return the number of columns in the Maze.
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the CellExtension at the specified row and column in the Maze.
     * 
     * @param row the row
     * @param col the column
     * @return the CellExtension at the specified row and column in the Maze.
     */
    public CellExtension get(int row, int col) {
        return landscape[row][col];
    }

    /**
     * Returns a LinkedList of the non-OBSTACLE CellExtensions neighboring the specified
     * CellExtension.
     * 
     * @param c the CellExtension to explore around.
     * @return a LinkedList of the non-OBSTACLE CellExtensions neighboring the specified
     *         CellExtension.
     */
    public LinkedList<CellExtension> getNeighbors(CellExtension c) {
        LinkedList<CellExtension> CellExtensions = new LinkedList<CellExtension>();
        if(c!=null) {
        int[][] steps = new int[][] { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int[] step : steps) {
            int nextRow = c.getRow() + step[0];
            int nextCol = c.getCol() + step[1];
            if (nextRow >= 0 && nextRow < getRows() && nextCol >= 0 && nextCol < getCols()
                    && get(nextRow, nextCol).getType() != CellExtensionType.OBSTACLE)
                CellExtensions.addLast(get(nextRow, nextCol));
        }
    }
        return CellExtensions;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("-".repeat(cols + 3) + "\n");
        for (CellExtension[] CellExtensions : landscape) {
            output.append("| ");
            for (CellExtension CellExtension : CellExtensions) {
                output.append(CellExtension.getType() == CellExtensionType.OBSTACLE ? 'X' : ' ');
            }
            output.append("|\n");
        }
        return output.append("-".repeat(cols + 3)).toString();
    }

    /**
     * Calls {@code drawType} on every CellExtension in this Maze.
     * @param g
     * @param scale
     */
    public void draw(Graphics g, int scale) {
        for (CellExtension CellExtension : this)
            CellExtension.drawType(g, scale);
    }

    public static void main(String[] args) {
        Maze ls = new Maze(7, 7, .2);
        System.out.println(ls);
    }
}
