import java.util.Queue;
import java.util.LinkedList;

public class MazeBreadthFirstSearch extends AbstractMazeSearch {
    //global variable
    private Queue<Cell> queue;

    public MazeBreadthFirstSearch(Maze maze) {
        //constructor
        super(maze);
        queue = new LinkedList<>();
    }

    public Cell findNextCell() {
        //this method returns the next cell
        return queue.poll();
    }

    public void addCell(Cell next) {
        //this method adds the next cell to the queue
        queue.offer(next);
    }

    public int numRemainingCells() {
        //this method returns the number of cells in the queue
        return queue.size();
    }
}
