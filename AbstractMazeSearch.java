import java.util.LinkedList;
import java.awt.Color;
import java.awt.Graphics;

public abstract class AbstractMazeSearch {
    //atributes
    public int exploredNum;
    private Maze maze;
    private Cell start;
    private Cell target;
    private Cell cur;

    public AbstractMazeSearch(Maze maze) {
        //constructor
        this.exploredNum = 0;
        this.maze = maze;
        this.start = null;
        this.target = null;
        this.cur = null;
    }

    //method returns the next cell
    public abstract Cell findNextCell();

    //method adds the next cell to the data structure
    public abstract void addCell(Cell next);

    //method returns the number of cells in the data structure
    public abstract int numRemainingCells();

    public Maze getMaze() {
        //method returns the maze
        return maze;
    }

    public void setTarget(Cell target) {
        //method sets the target cell
        this.target = target;
    }

    public Cell getTarget() {
        //method returns the target cell
        return target;
    }

    public void setCur(Cell cell) {
        //method sets the current cell
        this.cur = cell;
    }

    public Cell getCur() {
        //method returns the current cell
        return cur;
    }

    public void setStart(Cell start) {
        //method sets the start cell
        this.start = start;
        start.setPrev(start);
    }

    public Cell getStart() {
        //method returns the start cell
        return start;
    }

    public void reset() {
        //method resets the data structure
        cur = null;
        start = null;
        target = null;
    }

    public LinkedList<Cell> traceback(Cell cell) {
        //method returns the path from the start cell to the given cell
        LinkedList<Cell> reversePath = new LinkedList<>();
        while (cell != null) {
            reversePath.addFirst(cell);
            if (cell.equals(start)) {
                return reversePath;
            }
            cell = cell.getPrev();
        }
        return null;
    }
    
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) {
        //method returns the path from the start cell to the target cell
        MazeSearchDisplay searchDisplay = new MazeSearchDisplay(this, 20);
        setStart(start);
        setTarget(target);
        setCur(start);
        addCell(start);
        while (numRemainingCells() > 0) {
            if(display){
                searchDisplay.repaint();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Cell next = findNextCell();
            setCur(next);
            for (Cell neighbor : maze.getNeighbors(cur)) {
                if (neighbor.getPrev() == null) {
                    neighbor.setPrev(cur);
                    addCell(neighbor);
                    if (neighbor.equals(target)) {
                        return traceback(target);
                    }
                }
            }
            exploredNum++;
        }
        return null;
    }

    public void draw(Graphics g, int scale) {
        // Draws the base version of the maze
        getMaze().draw(g, scale);
        // Draws the paths taken by the searcher
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);
        // Draws the start cell
        getStart().draw(g, scale, Color.BLUE);
        // Draws the target cell
        getTarget().draw(g, scale, Color.RED);
        // Draws the current cell
        getCur().draw(g, scale, Color.MAGENTA);
    
        // If the target has been found, draws the path taken by the searcher to reach
        // the target sans backtracking.
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            while (!traceBackCur.equals(getStart())) {
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }
}
