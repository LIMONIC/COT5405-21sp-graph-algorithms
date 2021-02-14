# Graph algorithms for large data processing

This is a course project for COT5405 Analysis of Algorithm in 21 spring.

- [Introduction](#Introduction)
- [Class Structure](#Class Structure)
    - [GraphOperation](#GraphOperation)
    - [GraphSimulator](#GraphSimulator)
    - [SimulatedTest](#SimulatedTest)
    - [GraphMake](#GraphMake)
    - [RealTest](#RealTest)
    - [other](#other)
- [Requirement](#Requirement)
- [Usage](#Usage)
- [License](#license)

## Introduction

In this project, several graph algorithms was implemented to construct optimal graphs, check connected components, find
cycles and find the shortest path from one node to all other nodes in the graph. The project can be mainly divided into
two parts:

1. In the first part, DFS algorithms are designed for finding connected components and cycles. Dijkstra's algorithm is
   implemented for finding the shortest paths between nodes.
2. In the second part, a collection of general graph functions are created. Those graphs are employed for testing the
   functions in part one.
3. In the third part, billions of movie rating data from Netflix are translated into graphs with different adjacency
   criteria. Methods that implemented in part 1 are used to analyze the graphs and to deliver insightful results.

The performance of the programs are instrumented by run-time and memory consumption.

## Class Structure

### GraphOperation
*connected_components():* using depth-First search to return the list of connected components.

*one_cycle():* using depth-First search to return a cycle, or an empty list.

*shortest_paths():* using Dijkstra's algorithm and returns a map of the shortest paths.
### GraphSimulator
*nCycle():* An n-cycle: The vertices are integers from 0 through n - 1. The vertices u and v are connected by an edge if u - v = ±1 or u - v = ± (n - 1). There is one connected component, every shortest path has length at most n=2, and there is a unique cycle of length n.

*completeGraph():* The vertices are integers from 0 through n-1. Every pair of distinct vertices forms an edge. There is one connected component, every shortest path has unit length, and there are many cycles.

*emptyGraph():* An empty graph on n vertices: The vertices are integers from 0 through n - 1. There are no edges. There are n connected components, no paths, and no cycles.

*heap():* A heap: The vertices are integers from 0 through n - 1. The neighbors of a vertex v are (v - 1)/2, 2v + 1, and 2v + 2, provided those numbers are in the range for vertices. There is one connected component, the paths are short, and there are no cycles.

*truncatedHeap():* A truncated heap: The vertices are integers from m through n - 1. The edge relationship is the same as for the heap. There are n - 1 - 2m edges, m+ 1 connected components, and no cycles. The paths, when they exist, are short.

*equivalenceModK():* Equivalence mod k: The vertices are integers from 0 to n - 1, where k <= n. The vertices u and v are connected by an edge if u - v is evenly divisible by k. There are k components, and each component is a complete graph.
### SimulatedTest
*connectedComponentExp():* Perform experiment of connected components on a selected graph.

*oneCycleExp():* Perform experiment of find cycle on a selected graph.

*shortestPathExp():* Perform experiment of the shortest path on a selected graph. The start point are randomly selected from graph key set.
### GraphMake
*readRatingFiles():* Read data file and format it into adjacency list.

*writeCustomerList():* Output a txt file named "customerList.txt" that contains all customer ID.

*writeGraph():* Output a txt file with given name that contains the structure of the graph. graph represented by adjacency list.

*oneMovieInCommon():* This method output a graph in which all vertices are connected when they at least watched one movie in common. Implement union-find to reduce tree height in order to avoid stack over flow in other DFS algorithms.

*superReviewer():* This method output a graph, in which all vertices are connected based on number of their comment. 4 tires are applied. Top 10% of users with the highest number of comments are considered as "Super reviewer", top 10% - 30% of users with the highest number of comments are considered as "Active Reviewer", top 30% - 60% of users with the highest number of comments are considered as "Regular Reviewer", last 40% of users with the lowest number of comments are considered as "Inactive Reviewer". The average number of comments per user is 209.

### RealTest

### other

* Customer:
* Movie:
* Rating:

## Requirement

## Usage
* ### simulated_test.java
```bush
> [Graph_size] [Method: 1 - 6]
```
1. N Cycle
2. Complete graph
3. Empty graph
4. Heap
5. Truncated heap
6. Equivalence mod K
* ### real_test.java

## Credit

## License

[MIT](https://choosealicense.com/licenses/mit/)