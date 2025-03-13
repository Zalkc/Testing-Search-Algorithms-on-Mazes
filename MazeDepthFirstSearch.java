import java.util.Stack;

public class MazeDepthFirstSearch extends AbstractMazeSearch {
    private Stack<Cell> stack;

    public MazeDepthFirstSearch(Maze maze) {
        //constructor
        super(maze);
        stack = new Stack<>();
    }

    public Cell findNextCell() {
        //this method returns the next cell
        if(stack.isEmpty()) return null;
        return stack.pop();
    }

    public void addCell(Cell next) {
        //this method adds the next cell to the stack
        stack.add(next);
    }

    public int numRemainingCells() {
        //this method returns the number of cells in the stack
        return stack.size();
    }
}
