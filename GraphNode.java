//import java.util.ArrayList;

public class GraphNode {
    
    private int nodeName;
    private boolean marked = true;
    
    public GraphNode(int name) {
        this.nodeName = name;
        this.marked = false;   

    }

    public void mark(boolean mark) {
        this.marked = mark;
    }

    public boolean isMarked() {
        return marked;
    }

    public int getName() {
        return nodeName;
    }
}