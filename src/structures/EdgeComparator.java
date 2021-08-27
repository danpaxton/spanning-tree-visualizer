package structures;

import java.util.Comparator;
// Comparator class used for sorting by edge weights.
public class EdgeComparator<T> implements Comparator<Edge<T>> {
    // Compares edge weights between to edge objects. O(1).
    public int compare(Edge o1, Edge o2) {
        if (o1 == null || o2 == null) {
            throw new NullPointerException("Comparing edges must be not be null.");
        }
        int val;
        double diff = o1.getWeight() - o2.getWeight();
        if (diff > 0) {
            val = 1;
        }
        else if (diff == 0) {
            val = 0;
        } else {
            val = -1;
        }
        return val;
    }
    // Reverses standard EdgeComparator. O(1).
    public Comparator<Edge<T>> reversed() {
        return Comparator.super.reversed();
    }
}


