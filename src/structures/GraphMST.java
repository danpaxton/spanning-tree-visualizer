package structures;

import java.util.ArrayList;
import java.util.PriorityQueue;


// Graph implemented with an edge list using a priority queue. Edges 'E' and Vertices 'V'.
public class GraphMST<T> {
    // Number of vertices.
    private final int V;
    // Index value.
    private int index;
    // Maximum number of edges;
    int maxEdges;
    // List of all vertices.
    private final IndexedObject<T>[] vertices;
    // Priority queue implementation for edge list.
    private PriorityQueue<Edge<T>> edgeList;
    // Constructor, initializes vertices and sets comparator to sort edges off weight either increasing or decreasing. O(1).
    public GraphMST(int size, boolean rev) {
        if (size <= 0) {
            throw new IllegalArgumentException("Graph size must be greater than 0.");
        }
        V = size;
        this.vertices = new IndexedObject[size];
        index = 0;
        // Maximum number of edges in an undirected graph.
        maxEdges = (size * (size - 1)) / 2;
        treeOrientation(rev);
    }
    // Add new IndexedObject to vertices[]. O(1).
    public void addVertice(T v) {
        if (v == null) {
            throw new NullPointerException("Vertex must not be null.");
        }
        vertices[index] = new IndexedObject<>(v, index);
        index++;
    }
    // Add edge to list of edges. O(log(E)).
    public void addEdge(IndexedObject<T> src, IndexedObject<T> dest, double weight) {
        Edge<T> edge = new Edge<>(src, dest, weight);
        edgeList.add(edge);
    }
    /* Kruskal's algorithm. Utilizes the UnionFind data structure to find a minimum spanning tree(or maximum).
    The find() and union() operations are near constant in O(α(V)) time, bounded by the inverse Ackermann function 'α'.
    The priority queue poll() operation runtime is O(log(E)) time. Inverse Ackermann has a slower asymptotic growth than log,
    thus a worst-case runtime of O(E log(E)).This implementation adds all edges considered, edges that create a cycle are marked.
     */
    public ArrayList<Edge<T>> KruskalMST() {
        // Initialize union find data structure.
        UnionFind<T> subsets = new UnionFind<>(vertices);
        // Minimum spanning tree must be of size (total # of vertices) - 1.
        ArrayList<Edge<T>> MST = new ArrayList<>();
        for (int i = 0; i < V - 1; i++) {
            // Remove next edge sorted by weight. O(log(E)).
            Edge<T> currEdge = edgeList.poll();
            if (currEdge == null) {
                throw new NullPointerException("Current edge must be not be null.");
            }
            // Find which subsets src and dest of current edge belong to. O(α(V)).
            IndexedObject<T> a = subsets.find(currEdge.getSrc());
            IndexedObject<T> b = subsets.find(currEdge.getDest());
            if (a != b) {
                // Removed edge connects two different sets, combine set.
                subsets.union(a, b);
            } else {
                // Cycle detected, ignore current edge.
                currEdge.setCycle(true);
                i--;
            }
            // Add edge to MST.
            MST.add(currEdge);
        }
        return MST;
    }
    // Return vertices[]. O(1).
    public IndexedObject<T>[] getVertices() {
        return vertices;
    }
    // Creates edge list with a priority queue sorted by weight, increasing or decreasing. O(1).
    public void treeOrientation(boolean rev) {
        edgeList = new PriorityQueue<>(maxEdges,
                (!rev) ? (new EdgeComparator<T>().reversed()) : (new EdgeComparator<>()));
    }
}

