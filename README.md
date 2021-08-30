# Spanning Tree Visualizer 👀
**The Problem:**

  Suppose we are given a set of vertices V = { *v<sup>1</sup>, v<sup>2</sup>, ..., v<sup>n</sup>* } and the goal is to make a network on top of them. The network should be connected such that there 
is a path between every pair of nodes while also keeping the cost of the network as cheap (or expensive) as possible.

Specific to this project, every vertice is a point with an asscociated x and y coordinate and for each pair of vertices there is a link between them associated
with a postive edge cost defined by the euclidean distance between the two points, the formula ((x2 - x1)<sup>2</sup> + (y2 - y1)<sup>2</sup>)<sup>.5</sup>. The problem now presents itself
as such, given all edges E representing all possible links that can be made in the full graph G = (V, E) find a subset of edges T such that the graph G = (V, T) is 
connected with total distance minimzed (or maximized). For the purposes of this project the full graph G is assumed to be connected as if it was not the
case there would be no possible solution.

**Kruskal's Algorithm:**

  The graph G = (V,E) has been implemented using an edge list as a priority queue. As possible edges are added to the graph the priority queue will sort the edge list by distance allowing the poll() opertion to always remove the minimum (or maximum) distance edge from the top of the queue. This works well with Kruskal's algorthim as it eliminates the need for an initial sort of the edges at the start of the algorithm. The trade up is purley for less code as it would not offer any runtime benefits, a sort would run in O(Elog(E)) time and the poll() operation runs in O(log(E)) time with at most E calls to it, highlighting the unavoidble runtime of Kruskal in O(Elog(E)) time. During the iteration of the sorted edges, if the current edge would result in a cycle forming in the graph G = (V, T) ignore it, otherwise this edge belongs in the minimum(or maximum) spanning tree so it is included in the graph G = (V,T). This is the simplicity of Kruskal's algorithm. For the purposes of this project a boolean value is kept indicating whether an edge creates a cycle, this is for visualization purposes so these edges can be shown in different colors from non-cycle edges.

**Union-Find (Disjont-Set) Data Structure:**

  This data structure maintains disjoint subsets of V in the graph G =(V,E) and supports 3 main functions. The contructor UnionFind(Vertices v) takes all vertices of the graph and initializes each vertice to it's own subset (parent pointer is itself). Subsets are represented by a root vertice with parent pointers from children vertices up to the root. The find(vertice v) operation, given a vertice v return the subset containing v by following parent pointers until the root is found. This implementation of the find() operation utilizes path compression, after the subset (root) of v is found assign all parent pointers on the path from v to root directly to the root. Path compression allows the operation to acheive an armortised runtime in O(α(V)) time bounded by the extremely slow growing inverse ackermann function. The union(vertice a, vertice b) operation takes two vertices and using the find() opertion finds which subsets each vertice belongs to and merges them into one subset. The runtime of the union() operation is bounded by calls to the find() operation, thus O(α(V)) time. Alternatively, the runtime of Kruskal is in O(E(log(E)) + E(α(V))) time as each iteration during the iteration of all edges contains calls to the find() and possibly union() operations. The inverse ackermann function α(V) has a much slower asymptotic growth than log(E) so the runtime is still dominated by at most E calls to the O(log(E)) time poll() opertion, thus the runtime remains in O(E(log(E)) time as stated before.

**Graphical User Interface:**

  The GUI was built using Java awt for graphics and Java swing for the frame. Animation is achieved using a timer that will execute drawings during each iteration of the timer. The lines to be drawn are stored in a list, each iteration of the timer will repaint and redraw each line while the size continues to increase as more lines are added. There are two types of lines red and gray, red lines are the edges that belong to the minimum (or maximum) spanning tree and gray lines are the edges considered by Kruskal's algorithm but are not included as they would create a cycle. A load symbol will appear in the bottom right of the screen until the animation finishes. Finally, the background component will change colors signalling that the animation has finished.
  
**Problems and Future Changes:**

  A problem encountered was the issue of non-distinct edge costs in the graph. It would seem to have an effect on the outcome of the tree as considering edges in different orders could produce a different spanning tree. While this issue seems like it would pose a problem, the final solution remains valid if the issue is ignored all together. For example, assume the priority queue storing all edges sorted by weight would remove two edges consecutively that have the same edge weight. Assume it adds the first one to the tree and the second one would not be added as the first one covered the vertice the second edge would connect and adding it would create a cycle. Whether or not the first one or second one was added to the tree the total distance would still be minimized because both considered edges are the exact same weight, either would result in a valid minimum spanning tree.
  
A future change I would like to implement is eliminating the assumption that the full graph G = (V, E) is connected. I would like to add a feature that allows the minimum (or maximum) spanning tree to be produced for one component of the graph until no more vertices can be reached and then start the algorithm again on a different component of the graph that is disjoint from the first component and produce another minimum (or maximum) spanning tree for that component.

# Usage # 
Functions of the buttons that control the GUI.

**Generate:**  Make a new set of vertices with the amount specifed by the slider at the bottom of the display.

**Run Kruskal's ALgorithm:**  Start Kruskal's algortithm with the delay amount in milliseconds specified by the slider at the bottom of the display.

**Max or Min Spanning Tree:**  Determines the direction edges will be sorted by weight. Change won't be active unless hit 'Apply'.

**Anti-Aliasing:**  Smooths out all graphics in the display at the cost of a slower runtime.

# Installation #
Download zip or clone the project to the directory of choice.

Install Java jdk 16.
```console
~$ sudo apt install openjdk-16-jdk
```
Navigate to root directory
```console
~/Downloads/$ cd spanning_tree_visualizer-main
```
Navigate to src folder
```console
~/Downloads/spanning_tree_visualizer-main/$ cd src
```
Compile all required java files
```console
~/Downloads/spanning_tree_visualizer-main/src/$ javac structures/*.java
~/Downloads/spanning_tree_visualizer-main/src/$ javac GUI/*.java
```
Run Main.java
```console
~/Downloads/spanning_tree_visualizer-main/src/$ java GUI/Main.java
```
