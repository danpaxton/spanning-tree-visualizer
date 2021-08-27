package structures;

// Edge class for a weighted un-directed graph.
public class Edge<T> {
    // Edge fields.
    private final IndexedObject<T> src;
    private final IndexedObject<T> dest;
    private final double weight;
    private boolean cycle;

    // Constructor, initializes src, dest, and weight fields with respective arguments. O(1).
    public Edge(IndexedObject<T> src, IndexedObject<T> dest, double weight) {
        if (src == null || dest == null) {
            throw new NullPointerException("Source and destination vertices must be non-null.");
        }
        if (src == dest || weight < 0) {
            throw new IllegalArgumentException("Edge must be between two different nodes and weight must be non-negative.");
        }
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        cycle = false;
    }
    // Getters and Setter. All O(1).
    public double getWeight() { return weight; }

    public IndexedObject<T> getSrc() { return src; }

    public IndexedObject<T> getDest() { return dest; }

    public boolean isCycle() {return cycle;}

    public void setCycle(boolean cycle) {
        this.cycle = cycle;}
}

