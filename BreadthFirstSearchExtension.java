import java.util.Queue;
import java.util.LinkedList;

public class BreadthFirstSearchExtension extends AbstractMazeSearchExtension {
    //global variable
    private Queue<CellExtension> queue;

    public BreadthFirstSearchExtension(MazeExtension maze) {
        //constructor
        super(maze);
        queue = new LinkedList<>();
    }

    public CellExtension findNextCell() {
        //this method returns the next cell
        return queue.poll();
    }

    public void addCell(CellExtension next) {
        //this method adds the next cell to the queue
        queue.offer(next);
    }

    public int numRemainingCells() {
        //this method returns the number of cells in the queue
        return queue.size();
    }
}
