package structures;

// Union-Find implementation with an array of subset pointer nodes.
public class UnionFind<T> {
    // Subset nodes.
    private final Subset<T>[] subset;
    // Constructor, initializes subset array from size argument. O(V), V is the number of vertices.
    public UnionFind(IndexedObject<T>[] vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("Argument must be non-null");
        }
        subset = new Subset[vertices.length];
        // Sets each subset root to self and rank to 0.
        for (int i = 0; i < vertices.length; i++) {
            subset[i] = new Subset<>(vertices[i], 0);
        }
    }
    /* Finds which subset a node k belongs to. By using path compression this operation achieves an amortized
     runtime O(α(V)), α is the inverse ackermann function.
    */
    public IndexedObject<T> find(IndexedObject<T> k) {
        if ( k == null || k.index() < 0) {
            throw new IllegalArgumentException("Argument node must be non-null and node index must be greater than 0.");
        }
        // Initially set curr to argument node.
        IndexedObject<T> currRoot = k;
        // Find subset root. After iteration currRoot is the subset root.
        while (currRoot != subset[currRoot.index()].getRoot()) {
            // assign currRoot to root pointer.
            currRoot = subset[currRoot.index()].getRoot();
        }
        // Iterative path compression.
        // Starting from argument node, assign root pointer directly to subset root.
        while (k.index() != currRoot.index()) {
            IndexedObject<T> next = subset[k.index()].getRoot();
            subset[k.index()].setRoot(currRoot);
            k = next;
        }
        // currRoot is now the subset root.
        return currRoot;
    }
    /* Union by rank, merges two disjoint subsets a and b by rank comparison. Only operations not O(1) are calls to find(),
        giving an amortized runtime of O(α(V)), α is the inverse ackermann function.
    */
    public void union(IndexedObject<T> a, IndexedObject<T> b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Argument nodes must be non-null.");
        }
        // Find which subsets nodes a and b belong to.
        IndexedObject<T> subA = find(a);
        IndexedObject<T> subB = find(b);
        // If subsets share the same rank merge b to a.
        if (subset[subA.index()].getRank() == subset[subB.index()].getRank()) {
            subset[subB.index()].setRoot(subA);
            // Increment rank of subset a.
            subset[subA.index()].setRank(subset[subA.index()].getRank() + 1);
        }
        // If subset a has rank less than subset b merge a into b.
        else if (subset[subA.index()].getRank() < subset[subB.index()].getRank()) {
            subset[subA.index()].setRoot(subB);
        }
        // Otherwise, subset b has rank less than a. Merge b into a.
        else {
            subset[subB.index()].setRoot(subA);
        }
    }
    // Getter, O(1).
    public Subset<T>[] getSubset() {
        return subset;
    }
}

