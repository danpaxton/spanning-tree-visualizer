package testing;

import org.junit.Test;
import structures.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class Tests {
    // test subset
    @Test
    public void testSubset() {
        Subset<Integer> subset1 = new Subset<>(new IndexedObject<>(null, 1), 0);
        Subset<Integer> subset2 = new Subset<>(new IndexedObject<>(null, 1), 0);
        assertEquals(subset1.getRoot().index(), subset2.getRoot().index());
        assertEquals(subset1.getRank(), subset2.getRank());
        subset2 = new Subset<>(new IndexedObject<>(null,2), 1);
        assertNotEquals(subset1.getRoot().index(), subset2.getRoot().index());
        assertNotEquals(subset1.getRank(), subset2.getRank());
    }
    // test union find
    @Test
    public void testUnionFind() {
        IndexedObject<Integer>[] a = new IndexedObject[5];
        for (int i = 0; i < 5; i++) {
            a[i] = new IndexedObject<>(null, i);
        }
        UnionFind<Integer> unionFind = new UnionFind(a);
        for (int i = 0; i < 4; i++) {
            // test find.
            assertNotSame(unionFind.find(a[i]), unionFind.find(a[i + 1]));
            unionFind.union(a[i], a[i + 1]);
            // test rank incr.
            assertEquals(1, unionFind.getSubset()[unionFind.find(a[i]).index()].getRank());
            // test union.
            assertSame(unionFind.find(a[i]), unionFind.find(a[i + 1]));
        }
    }
    // Test kruskal's algorithm.
    @Test
    public void testKruskal() {
        GraphMST<Integer> g = new GraphMST<>(4, true);
        // add vertices.
        g.addVertice(1);
        g.addVertice(2);
        g.addVertice(3);
        g.addVertice(4);
        // add edges.
        g.addEdge(g.getVertices()[0], g.getVertices()[1], 1);
        g.addEdge(g.getVertices()[1], g.getVertices()[2], 1);
        g.addEdge(g.getVertices()[2], g.getVertices()[3], 2);
        g.addEdge(g.getVertices()[3], g.getVertices()[1], 2);
        g.addEdge(g.getVertices()[0], g.getVertices()[2], 1);
        g.addEdge(g.getVertices()[0], g.getVertices()[3], 2);
        // Test that the third edge considered creates a cycle.
        ArrayList<Edge<Integer>> el = g.KruskalMST();
        assertTrue(el.get(2).isCycle());
    }
}
