import java.awt.Color;
import java.awt.Graphics;

public class CellExtension {
    /**
     * Specifies the row and column of this CellExtension
     */
    private int row, col;
    private int regularTime = 1;
    private int webTime = 2;
    private int boostTime = 0;

    /**
     * Specifies the CellExtension which, when explored, revealed this CellExtension for the first
     * time
     */
    private CellExtension prev;

    /**
     * Specifies the CellExtensionType of this CellExtension (either FREE or OBSTACLE)
     */
    private CellExtensionType type;


    public int getTime() {
        if(getType() == CellExtensionType.FREE){
            return regularTime;
        }
        else if(getType() == CellExtensionType.WEB){
            return webTime;
        }
        else{
            return boostTime;
        }
    }
    /**
     * Constructs a CellExtension from the given parameters.
     * 
     * @param r    the row of the CellExtension
     * @param c    the column of the CellExtension
     * @param type the CellExtensionType of the CellExtension (either FREE or OBSTACLE)
     */
    public CellExtension(int r, int c, CellExtensionType type) {
        row = r;
        col = c;
        this.type = type;
    }

    /**
     * Sets the previous CellExtension of this to the given {@code CellExtension prev}.
     * 
     * This means that when {@code prev} was explored, this CellExtension was found for the
     * first time.
     * 
     * @param prev the previous CellExtension of this one.
     */
    public void setPrev(CellExtension prev) {
        this.prev = prev;
    }

    /**
     * Returns the previous CellExtension of this one.
     * 
     * @return the previous CellExtension of this one.
     */
    public CellExtension getPrev() {
        return prev;
    }

    /**
     * Resets this CellExtension back to its initial state (which just sets prev to null)
     */
    public void reset() {
        setPrev(null);
    }

    /**
     * Returns the CellExtensionType of this CellExtension (either FREE or OBSTACLE).
     * 
     * @return the CellExtensionType of this CellExtension (either FREE or OBSTACLE).
     */
    public CellExtensionType getType() {
        return type;
    }

    public void setType(CellExtensionType type) {
        this.type = type;
    }

    /**
     * Returns the row of this CellExtension.
     * 
     * @return the row of this CellExtension.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of this CellExtension.
     * 
     * @return the column of this CellExtension.
     */
    public int getCol() {
        return col;
    }

    public boolean equals(Object o) {
        if (!(o instanceof CellExtension))
            return false;
        CellExtension c = (CellExtension) o;
        return row == c.row && col == c.col && type == c.type;
    }

    public String toString() {
        return "(" + row + ", " + col + ", " + type + ")";
    }

    /**
     * Draws this CellExtension to the given Graphics object.
     * 
     * If this CellExtension is FREE, then it will be drawn with yellow if it has been
     * visited, otherwise gray.
     * 
     * If this CellExtension is an OBSTACLE, it will be drawn black.
     * 
     * @param g     the Graphics object on which to draw.
     * @param scale the scale at which to draw this CellExtension.
     */
    public void drawType(Graphics g, int scale) {
        g.setColor(Color.BLACK);
        g.drawRect(getCol() * scale, getRow() * scale, scale, scale);
        switch (getType()) {
            case FREE:
                draw(g, scale, getPrev() != null ? Color.YELLOW : Color.GRAY);
                break;
            case OBSTACLE:
                draw(g, scale, Color.BLACK);
                break;
            case WEB:
                draw(g, scale, Color.WHITE);
                break;
            case BOOST:
                draw(g, scale, Color.CYAN);
                break;
            
        }
    }

    /**
     * Recursively draws lines from each visited CellExtension to the CellExtension that they revealed
     * by exploration.
     * 
     * @param MazeExtension  the MazeExtension in which this CellExtension resides.
     * @param g     the Graphics object on which to draw.
     * @param scale the scale at which to draw.
     * @param c     the Color to draw with.
     */
    public void drawAllPrevs(MazeExtension MazeExtension, Graphics g, int scale, Color c) {
        g.setColor(c);
        for (CellExtension neighbor : MazeExtension.getNeighbors(this)) {
            if (neighbor.getPrev() == this) {
                g.drawLine(getCol() * scale + scale / 2, getRow() * scale + scale / 2,
                        neighbor.getCol() * scale + scale / 2, neighbor.getRow() * scale + scale / 2);
                neighbor.drawAllPrevs(MazeExtension, g, scale, c);
            }
        }
    }

    /**
     * Recursively draws lines from this CellExtension until reaching a CellExtension whose
     * {@code prev} is {@code null} or itself.
     * 
     * @param g     the Graphics object on which to draw.
     * @param scale the scale by which to draw.
     * @param c     the Color to drawn with.
     */
    public void drawPrevPath(Graphics g, int scale, Color c) {
        g.setColor(c);
        if (getPrev() != null && getPrev() != this) {
            g.drawLine(getCol() * scale + scale / 2, getRow() * scale + scale / 2,
                    getPrev().getCol() * scale + scale / 2, getPrev().getRow() * scale + scale / 2);
            getPrev().drawPrevPath(g, scale, c);
        }
    }

    /**
     * Draws this CellExtension onto the given Graphics object with the given Color.
     * 
     * @param g     the Graphics object on which to draw.
     * @param scale the scale by which to draw.
     * @param c     the Color to draw with.
     */
    public void draw(Graphics g, int scale, Color c) {
        g.setColor(c);
        g.fillRect(getCol() * scale + 2, getRow() * scale + 2, scale - 4, scale - 3);
    }
}