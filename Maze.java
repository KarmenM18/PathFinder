
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Maze {

    private Graph graph;
    private GraphNode entrance;
    private GraphNode exit;
    private int coins;  // amount of available coins at the start of the game

    private int scale = 0;
    private int width = 0;
    private int height = 0;

    // Maze Constructor: Read the file, construct the graph reprsenting the maze
    public Maze(String inputFile) throws MazeException {

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

            // Read and parse through first 4 lines of text file
            scale = Integer.parseInt(reader.readLine().trim());
            width = Integer.parseInt(reader.readLine().trim());
            height = Integer.parseInt(reader.readLine().trim());
            coins = Integer.parseInt(reader.readLine().trim());

            // Create a new empty graph
            graph = new Graph(width * height);

            // Coins required for a certain door
            int doorCoins = 0;

            // Store column that was last traversed
            int col = 0;
            
            // Iterate and loop through vertically (across row)
            int i = 0;
            
            // Process to maze representation using graph as underlying structure
            for (;;) {
                String line = reader.readLine();

                // Check if end of file is reached
                if (line == null) { 
                    reader.close();
                    return;
                }

                // Trim non-empty line of excess spaces
                else {
                    line = line.trim();
                }
    
                // Perform horizontal iteration of the maze
                for (int j = 0; j < line.length(); j++) {
                    
                    // Check the next character in the line in the text file
                    switch (line.charAt(j)) {

                        // Process the entrance of the maze
                        case 's':
                            entrance = graph.getNode(col);
                            ++col;
                            break;
                        
                        // Process the exit of the maze
                            case 'x':
                            exit = graph.getNode(col);
                            ++col;
                            break;

                        // Process if room is encountered 
                        case 'o':
                            ++col;
                            break;
                        
                        // Process if corridor is encountered
                        case 'c':
                            
                        // Check if line is 'RH' (horizontal represenation) or 'VW' line (vertical representation)
                            if (i % 2 == 0) {
                                graph.insertEdge(graph.getNode(col - 1), graph.getNode(col), 2, "corridor");
                            } else {
                                // Find the previous and next nodes for edges on 'VW' (vertical) line
                                int secondNode = j / 2 + col;
                                int firstNode = secondNode - width;
                                graph.insertEdge(graph.getNode(firstNode), graph.getNode(secondNode), 2, "corridor");
                            }
                            break;
                        
                        // If the character encountered in the line in the text file is a digit from 0-9
                        default:
                            if (Character.isDigit(line.charAt(j))) {
                                doorCoins = Character.getNumericValue(line.charAt(j));
                                if (i % 2 == 0) {
                                    graph.insertEdge(graph.getNode(col - 1), graph.getNode(col), doorCoins, "door");
                                } else {
                                    int secondNode = j / 2 + col;
                                    int firstNode = secondNode - width;
                                    graph.insertEdge(graph.getNode(firstNode), graph.getNode(secondNode), doorCoins,
                                            "door");
                                }
                            }
                    }
                }
                i++;
            }
        
        } catch (IOException | GraphException e) { 
            throw new MazeException("Error reading maze file.");
        }
    }

    public Graph getGraph() throws MazeException {
        if (graph == null) {
            throw new MazeException("The graph is null.");
        }
        return graph;
    }

    public Iterator solve() {
        
        // Check if maze not properly initialized
        if (graph == null || entrance == null || exit == null) {
            return null; 
        }

        // Create container to keep track of the path taken by program
        Stack<GraphNode> path = new Stack<>();
        
        // Create a container to keep track of nodes that have been discovered
        List<Integer> discoveredNodes = new ArrayList<>();
       
       // Keeps track of the nodes that have been visited 
        ArrayList<Integer> visited = new ArrayList<Integer>(); // Q: do i use isMarked() with this?

        // Start DFS (depth-first) traversal starting from the entrance
        dfs(entrance.getName(), path, discoveredNodes, visited, coins);

        // Check if a valid path to the exit is found
        if (path.isEmpty() || path.peek().getName() != exit.getName()) {
            return null; // No valid path found
        }

        return path.iterator();
    }

    // Helper method: performs a depth-first traversal of the graph
    private void dfs(int currentNode, Stack<GraphNode> path, List<Integer> discoveredNodes, ArrayList<Integer> visited,
            int remainingCoins) {

        try {
            // Iterate current node's incident edges
            Iterator<GraphEdge> edges;
            
            // Mark current node as visited
            visited.add(currentNode);
            
            // Push the current node to the path
            path.push(graph.getNode(currentNode));
            
            // If the current node is the exit, return
            if (currentNode == exit.getName()) {
                return;
            }

            edges = graph.incidentEdges(graph.getNode(currentNode));

            // Check to see whether the program can continue along the edge
            while (edges != null && edges.hasNext()) {
                int remCoins = remainingCoins;
                GraphEdge edge = edges.next();
                GraphNode neighbor = (edge.firstEndpoint().getName() == currentNode) ? edge.secondEndpoint()
                        : edge.firstEndpoint();

                int neighborName = neighbor.getName();

                // Check if the edge is a corridor or a door that can be opened
                if ((edge.getLabel().equals("corridor") || canOpenDoor(edge, remCoins))
                        && (!visited.contains(neighborName))) {

                    // Deduct the remaining amount of coins after passing through door
                    if (edge.getLabel().equals("door")) {
                        remCoins = remCoins - edge.getType();
                    }
                    // Recursively visit the neighbor
                    dfs(neighborName, path, discoveredNodes, visited, remCoins);

                    // Check if the exit is found
                    if (path.peek().getName() == exit.getName()) {
                        return;
                    }
                }
            }
        } catch (GraphException e) {
            e.printStackTrace();
        }

        // Pop the current node if no valid path is found
        path.pop();
    }

    // HELPER method - check if the door can be opened based on available coins
    // Return true if the door can be opened, false otherwise
    private boolean canOpenDoor(GraphEdge edge, int remainingCoins) {
        if (edge.getType() <= remainingCoins) {
            return true;
        }
        return false;
    }
}
