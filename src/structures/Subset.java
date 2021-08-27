package structures;

// Subset class with rank value and root pointers.
public class Subset<T> {
    // Subset fields.
    private IndexedObject<T> root;
    private int rank;
    // Constructor, initializes root and rank fields with respective arguments. O(1).
    public Subset(IndexedObject<T> root, int rank) {
        if (root == null || rank < 0) {
            throw new IllegalArgumentException("Root must not be null and rank must non negative.");
        }
        this.root = root;
        this.rank = rank;
    }
    // Setters and getters. All O(1).
    public IndexedObject<T> getRoot() {
        return root;
    }

    public void setRoot(IndexedObject<T> root) {
        if (root == null) {
            throw new NullPointerException("Root must not be null.");
        }
        this.root = root;
    }

    public int getRank() {return rank;}

    public void setRank(int rank) {
        if (rank < 0) {
            throw new IllegalArgumentException("Rank must be non-negative.");
        }
        this.rank = rank;
    }
}

