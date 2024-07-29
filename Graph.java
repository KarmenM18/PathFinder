
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Graph implements GraphADT {

    private int numNodes;
    private int[][] adjMatrix; // 2D adjacency matrix, stores edge info btw nodes
    private GraphNode[] nodes; // The nodes in the graph
    private ArrayList<GraphEdge> edges;

    public Graph(int n) {
        this.numNodes = n;
        this.adjMatrix = new int[numNodes + 1][numNodes + 1];
        this.nodes = new GraphNode[numNodes];
        this.edges = new ArrayList<GraphEdge>();

        // Initialize graph nodes from 0 -> n-1
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new GraphNode(i);
        }

    }

    public void insertEdge(GraphNode u, GraphNode v, int edgeType, String label) throws GraphException {

        GraphEdge newEdge = new GraphEdge(u, v, edgeType, label);
        
        // Check if there is already an edge connecting the given nodes
        if (edges.indexOf(newEdge) > 0) {
            throw new GraphException("Edge already exists between these nodes.");
        }

        // Check to see if an invalid edge with incorrect label is trying to be added
        else if (!label.equals("corridor") && !label.equals("door")) {
            throw new GraphException("Invalid edge is trying to be added.");
        }

        // Check if node doesn't exist in the graph
        else if ((u.getName() > numNodes || v.getName() > numNodes)) {
            throw new GraphException("Node does not exist in the graph.");
        }

        else {
            edges.add(new GraphEdge(u, v, edgeType, label));

            // Sets the value in matrix coordinate position to be '2' if edge is a corridor and '1' if edge is a door
            if (label.equals("corridor")) {
                adjMatrix[u.getName()][v.getName()] = 2;
                adjMatrix[v.getName()][u.getName()] = 2;

            } else if (label.equals("door")) {
                adjMatrix[u.getName()][v.getName()] = 1;
                adjMatrix[v.getName()][u.getName()] = 1;
            }

        }

    }


    public GraphNode getNode(int name) throws GraphException {
        if (name < numNodes) {
            return nodes[name];
        } else {
            throw new GraphException("No node with this name exists.");
        }

    }


    // Check all the edges that are incident (connected to) GraphNode u
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {

        // If GraphNode u is not a node in the graph
        if (u.getName() < 0 || u.getName() > numNodes) {
            throw new GraphException("No node with this name exists.");
        }

        // Keep track of incident edges
        List<GraphEdge> incidentEdges = new ArrayList<>();
        Iterator<GraphEdge> iterator = edges.iterator();

        // Traverse and visit each edge in the graph
        while (iterator.hasNext()) {
            GraphEdge edge = iterator.next();

            // Check if the current edge has the node u attached to it, add it to list of
            // incident edges
            if (edge.firstEndpoint() == u || edge.secondEndpoint() == u) {
                incidentEdges.add(edge);
            }
        }

        return incidentEdges.iterator();
    }

    // Returns the edge connecting nodes u and v, otherwise an exception is thrown
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {

        GraphEdge val = null;

        // Check if node doesn't exist in the graph
        if ((u.getName() > numNodes || v.getName() > numNodes)
                || (nodes[u.getName()] == null || nodes[v.getName()] == null)) {
            throw new GraphException("Node does not exist in the graph.");
        }

        // If node does exist in the graph
        else {
            for (int i = 0; i < edges.size(); i++) {
                GraphEdge currEdge = edges.get(i);

                // Check if either of the given nodes, in either order, are the endpoints of the edge
                if ((currEdge.firstEndpoint() == u && currEdge.secondEndpoint() == v) || (currEdge.firstEndpoint() == v && currEdge.secondEndpoint() == u)) {
                    val = currEdge;
                    i = edges.size();
                }
            }

        }

        // Check and throw exception if no edge is found between nodes
        if (val == null) {
            throw new GraphException("There is no edge between these nodes.");
        } else {
            return val;
        }

    }


    // Checks if the given nodes u and v are adjacent - returns true if adjacent, false if not, and throws error if nodes don't exist
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        
        // Throw exception if nodes u or v are not in the graph
        if (u.getName() < 0 || u.getName() > numNodes || v.getName() < 0 || v.getName() > numNodes) {
            throw new GraphException("At least one of the nodes doesn't exist in the graph");
        }

        else {
            try {
                getEdge(u, v);  
                return true;
            } catch (GraphException e) {
                return false;
            }
        }
    }

}
