# Spanning Tree Visualizer
**The Problem**:

Suppose we are given a set of vertices V = {*v_1, v_2, ..., v_n*} and the goal is to make a network on top of them. The network should be connected such that there 
is a path between every pair of nodes while also keeping the cost of the network as cheap (or expensive) as possible.

Specific to this project, every vertice is a point with an asscociated x and y coordinate and for each pair of vertices ther may be a link between them associated
with a postive edge cost defined by the euclidean distance between the two points, the formuala ((x2 - x1)^2 + (y2 - y1)^2)^.5. The problem now presents itself
as such, given all edges E representing all possible links that can be made in the full graph G = (V, E) find a subset of edges T such that the graph G = (V, T) is 
connected with total distance minimzed (or maximized). For the purposes of this project the full graph G is also assumed to be connected as if it was not the
case there would be no possible solution.
