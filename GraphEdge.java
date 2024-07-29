public class GraphEdge {

    private GraphNode start;    // starting GraphNode of edge
    private GraphNode end;      // ending GraphNode of edge
    private int edgeType;       // type of edge (based on corridor, door or wall)
    private String edgeLabel;   // name of edge (corridor or door)

    
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.start = u;
        this.end = v;
        this.edgeType = type;
        this.edgeLabel = label;

    }

    // PURPOSE: gets the first endpoint of edge
    public GraphNode firstEndpoint() {
        return start;
    }

    // PURPOSE: gets the second endpoint of edge
    public GraphNode secondEndpoint() {
        return end;
    }

    // PURPOSE: gets the type of the givendge
    public int getType() {
        return edgeType;
    }

    // PURPOSE: sets the type of the edge
    public void setType(int newType) {
        edgeType = newType;
    }

    // PURPOSE: gets the label of the edge
    public String getLabel() {
        return edgeLabel;
    }

    // PURPOSE: sets the label of the edge
    public void setLabel(String newLabel) {
        edgeLabel = newLabel;
    }

}