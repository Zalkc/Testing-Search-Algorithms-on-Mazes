import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSearchExtension extends AbstractMazeSearchExtension {
    //global variable
    private PriorityQueue<CellExtension> priorityQueue;

    public AStarSearchExtension(MazeExtension maze) {
        //constructor
        super(maze);
        priorityQueue = new PriorityQueue<CellExtension>(new CellComparator());
    }

    public CellExtension findNextCell() {
        //this method returns the next cell
        return priorityQueue.poll();
    }

    public void addCell(CellExtension next) {
        //this method adds the next cell to the priority queue
        priorityQueue.offer(next);
    }

    public int numRemainingCells() {
        //this method returns the number of cells in the priority queue
        return priorityQueue.size();
    }

    private class CellComparator implements Comparator<CellExtension> {
        //this class compares cells
        public int compare(CellExtension cell1, CellExtension cell2) {
            int lengthCell1 = traceback(cell1).size();
            int lengthCell2 = traceback(cell2).size();
            int stepsCell1 = (Math.abs((getTarget().getRow()) - (cell1.getRow())) + Math.abs((getTarget().getCol()) - (cell1.getCol())));
            int stepsCell2 = (Math.abs((getTarget().getRow()) - (cell2.getRow())) + Math.abs((getTarget().getCol()) - (cell2.getCol())));
            double sum1 = lengthCell1 + stepsCell1;
            double sum2 = lengthCell2 + stepsCell2;
            return Double.compare(sum1, sum2);
        }
    }
}
