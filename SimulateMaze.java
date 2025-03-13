/*
 * file name:      SimulateMaze.java
 * author:         Alexander Zhelonkin
 * last modified:  11/27/23
 * 
 * How to run: java SimulateMaze
 */

import java.util.*; 

public class SimulateMaze {

    private static void runAlg(String algorithmName, AbstractMazeSearch searchAlgorithm) {
        // Initialize start and end cells
        int rows = searchAlgorithm.getMaze().getRows();
        int cols = searchAlgorithm.getMaze().getCols();
        Cell start = searchAlgorithm.getMaze().get(0, 0); // Start from the top-left corner
        Cell end = searchAlgorithm.getMaze().get(rows - 1, cols - 1); // Target at bottom-right corner
        searchAlgorithm.setStart(start);
        searchAlgorithm.setTarget(end);
        
        LinkedList<Cell> path = searchAlgorithm.search(start, end, true, 0);
        
        System.out.println("Search Method:  " + algorithmName);
        
        if (path != null && path.getLast() == end) {
            System.out.println("Finished");
                    System.out.println("Path length: " + path.size());
        } else {
            System.out.println("No solution found");
        }
        
        System.out.println("Number of cells explored: " + searchAlgorithm.exploredNum);
        System.out.println("-------------------------------------");
    }
    
    public static void main(String[] args) {
        //creates 3 identical mazes and tests search algorithms
        Maze maze = new Maze(15, 15, 0.1);
        Maze maze2 = new Maze(15, 15, 0.1);
        Maze maze3 = new Maze(15, 15, 0.1);

        for(Cell cell : maze){
            maze2.get(cell.getRow(), cell.getCol()).setType(maze.get(cell.getRow(), cell.getCol()).getType());
        }
        for(Cell cell : maze){
            maze3.get(cell.getRow(), cell.getCol()).setType(maze.get(cell.getRow(), cell.getCol()).getType());
        }
        AbstractMazeSearch breadthFirstSearch = new MazeBreadthFirstSearch(maze);
        AbstractMazeSearch depthFirstSearch = new MazeDepthFirstSearch(maze2);
        AbstractMazeSearch aStarSearch = new MazeAStarSearch(maze3);

        runAlg("Breadth First Search", breadthFirstSearch);
        runAlg("Depth First Search", depthFirstSearch);
        runAlg("A Star Search", aStarSearch);
    }
}
