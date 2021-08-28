# Spanning Tree Visualizer
**The Problem:**

Suppose we are given a set of vertices V = {*v_1, v_2, ..., v_n*} and the goal is to make a network on top of them. The network should be connected such that there 
is a path between every pair of nodes while also keeping the cost of the network as cheap (or expensive) as possible.

Specific to this project, every vertice is a point with an asscociated x and y coordinate and for each pair of vertices there is a link between them associated
with a postive edge cost defined by the euclidean distance between the two points, the formula ((x2 - x1)^2+(y2 - y1)^2)^.5. The problem now presents itself
as such, given all edges E representing all possible links that can be made in the full graph G = (V, E) find a subset of edges T such that the graph G = (V, T) is 
connected with total distance minimzed (or maximized). For the purposes of this project the full graph G is assumed to be connected as if it was not the
case there would be no possible solution.

**Kruskal's Algorithm:**

The graph G = (V,E) has been implemented using an edge list as a priority queue. As possible edges are added to the graph the priority queue will sort the edge list by distance allowing the poll() opertion to always remove the minimum (or maximum) distance edge from the top of the queue. This works well with Kruskal's algorthim as it eliminates the need for an initial sort of the edges at the start of the algorithm. The trade up is purley for less code as it would not offer any runtime benefits, a sort would run in O(ElogE) time and the poll() operation runs in O(logE) time with at most E calls to it, highlighting the unavoidble runtime of Kruskal in O(ElogE) time. During the iteration of the sorted edges, if the current edge would result in a cycle forming in the graph G = (V, T) ignore it, otherwise this edge belongs in the minimum(or maximum) spanning tree so it is included in the graph G = (V,T). This is the simplicity of Kruskal's algorithm. For this project a boolean value is kept indicating whether an edge creates a cycle, this is for visualization purposes so these edges can be shown in different colors from non-cycle edges.

**Union-Find (Disjont-Set) Data Structure:**



