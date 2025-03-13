/*
 * file name:      SimulateMazeExtension.java
 * author:         Alexander Zhelonkin
 * last modified:  11/27/23
 * 
 * How to run: java SimulateMazeExtension
 */

import java.util.*; 

public class SimulateMazeExtension {

    private static void runAlg(String algorithmName, AbstractMazeSearchExtension searchAlgorithm) {
        // Initialize start and end cells
        int rows = searchAlgorithm.getMaze().getRows();
        int cols = searchAlgorithm.getMaze().getCols();
        CellExtension start = searchAlgorithm.getMaze().get(0, 0); // Start from the top-left corner
        CellExtension end = searchAlgorithm.getMaze().get(rows - 1, cols - 1); // Target at bottom-right corner
        searchAlgorithm.setStart(start);
        searchAlgorithm.setTarget(end);
        
        LinkedList<CellExtension> path = searchAlgorithm.search(start, end, true, 0);
        
        System.out.println("Search Method:  " + algorithmName);
        
        if (path != null && path.getLast() == end) {
            System.out.println("Finished");
                    System.out.println("Path length: " + path.size());
                    System.out.println("Time taken: " + searchAlgorithm.getTimePath(path));
        } else {
            System.out.println("No solution found");
        }
        
        System.out.println("Number of cells explored: " + searchAlgorithm.exploredNum );
        System.out.println("-------------------------------------");
    }
    
    public static void main(String[] args) {
        //creates 3 identical mazes and tests search algorithms
        MazeExtension maze1 = new MazeExtension(15, 15, 0.1);
        MazeExtension maze2 = new MazeExtension(15, 15, 0.1);
        MazeExtension maze3 = new MazeExtension(15, 15, 0.1);

        Random rand = new Random();
        int numWeb = rand.nextInt(5) + 5;
        int numBoost = rand.nextInt(5) + 3;
        for(int i=0; i < numWeb; i++){
            maze1.get(rand.nextInt(15), rand.nextInt(15)).setType(CellExtensionType.WEB);
        }
        for(int i=0; i < numBoost; i++){
            maze1.get(rand.nextInt(15), rand.nextInt(15)).setType(CellExtensionType.BOOST);
        }
        for(CellExtension cell : maze1){
            maze2.get(cell.getRow(), cell.getCol()).setType(maze1.get(cell.getRow(), cell.getCol()).getType());
        }
        for(CellExtension cell : maze1){
            maze3.get(cell.getRow(), cell.getCol()).setType(maze1.get(cell.getRow(), cell.getCol()).getType());
        }
        AbstractMazeSearchExtension breadthFirstSearch = new BreadthFirstSearchExtension(maze1);
        AbstractMazeSearchExtension depthFirstSearch = new DepthFirstSearchExtension(maze2);
        AbstractMazeSearchExtension aStarSearch = new AStarSearchExtension(maze3);

        runAlg("Breadth First Search", breadthFirstSearch);
        runAlg("Depth First Search", depthFirstSearch);
        runAlg("A* Search", aStarSearch);
    }
}
