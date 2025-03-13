import java.util.Stack;

public class DepthFirstSearchExtension extends AbstractMazeSearchExtension {
    private Stack<CellExtension> stack;

    public DepthFirstSearchExtension(MazeExtension maze) {
        //constructor
        super(maze);
        stack = new Stack<CellExtension>();
    }

    public CellExtension findNextCell() {
        //this method returns the next cell
        if(stack.isEmpty()) return null;
        return stack.pop();
    }

    public void addCell(CellExtension next) {
        //this method adds the next cell to the stack
        stack.add(next);
    }

    public int numRemainingCells() {
        //this method returns the number of cells in the stack
        return stack.size();
    }
}
