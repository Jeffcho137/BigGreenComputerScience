package cs10.ps4;

import java.util.*;


/**
 * Library for graph analysis
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * 
 */
public class GraphLib {
	/**
	 * Takes a random walk from a vertex, up to a given number of steps
	 * So a 0-step path only includes start, while a 1-step path includes start and one of its out-neighbors,
	 * and a 2-step path includes start, an out-neighbor, and one of the out-neighbor's out-neighbors
	 * Stops earlier if no step can be taken (i.e., reach a vertex with no out-edge)
	 * @param g		graph to walk on
	 * @param start	initial vertex (assumed to be in graph)
	 * @param steps	max number of steps
	 * @return		a list of vertices starting with start, each with an edge to the sequentially next in the list;
	 * 			    null if start isn't in graph
	 */
	public static <V,E> List<V> randomWalk(Graph<V,E> g, V start, int steps) {
		if (!g.hasVertex(start)) return null;
		
		List<V> path = new ArrayList<V>();
		path.add(start);
		
		for (int i = 0; i < steps; i++) {
			if(g.outDegree(path.get(path.size()-1)) != 0) {
				List<V> neighbors = new ArrayList<V>();
				for(V vertex : g.outNeighbors(path.get(path.size()-1))) neighbors.add(vertex);
				int index = (int) Math.random()*neighbors.size();
				path.add(neighbors.get(index));
			}
		}
		
		return path;
	}
	
	/**
	 * Orders vertices in decreasing order by their in-degree
	 * @param g		graph
	 * @return		list of vertices sorted by in-degree, decreasing (i.e., largest at index 0)
	 */
	public static <V,E> List<V> verticesByInDegree(Graph<V,E> g) {
		// TODO: your code here
		List<V> list = new ArrayList<V>();
		for(V vertex : g.vertices()) {
			list.add(vertex);
		}
		list.sort((v1, v2) -> g.inDegree(v2) - g.inDegree(v1));
		return list;
	}
}
