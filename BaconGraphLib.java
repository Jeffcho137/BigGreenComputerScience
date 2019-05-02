package cs10.ps4;

import java.util.*;

public class BaconGraphLib<V> {
	/**
	 * returns path tree leading to source
	 * @param universe
	 * @param source
	 * @return path tree
	 * @throws Exception
	 */
	public static <V,E> Graph<V,E> bfs(Graph<V,E> universe, V source) throws Exception{
		//Variables
		Graph<V,E> output = new AdjacencyMapGraph<V,E>();
		SLLQueue<V> queue = new SLLQueue<V>();
		Map<V, HashSet<V>> paths = new HashMap<V, HashSet<V>>();

		//add starting point to queue and output
		queue.enqueue(source);
		output.insertVertex(source);

		while(!queue.isEmpty()) {
			V v = queue.dequeue();
			for(V neighbor : universe.outNeighbors(v)) {
				if(!output.hasVertex(neighbor)) {
					//add neighbors to queue, output, and record the path relation
					if(!paths.containsKey(v))  paths.put(v,new HashSet<V>());
					paths.get(v).add(neighbor);
					queue.enqueue(neighbor);
					output.insertVertex(neighbor);
				}
			}
		}

		for(V v : paths.keySet()) {
			for(V i : paths.get(v)) {
				//insert directed edges to keep track of paths
				output.insertDirected(i, v, null);
			}
		}
		return output;
	} 
	/**
	 * gets a path from a vertex to the center given a tree
	 * @param tree
	 * @param v
	 * @return outlist if tree has vertex v, otherwise null
	 */
	public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
		//Variables
		List<V> outList = new ArrayList<V>();
		
		//backtracks steps to reach root
		if(tree.hasVertex(v)) {
			V current = v;
			while(tree.outDegree(current) != 0) {
				outList.add(current);
				current = tree.outNeighbors(current).iterator().next();
			}
			outList.add(current);
			return outList;
		}
		return null;
	}

	/**
	 * Goes through the vertices in the tree, if v is not in the subtree, it's missing (add to set)
	 * @param graph
	 * @param subgraph
	 * @return missing - set of missing vertices
	 */
	public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
		//Variables
		Set<V> missing = new HashSet<V>();
		
		//checks if in graph, if no add to missing
		for(V v : graph.vertices()) {
			if(!subgraph.hasVertex(v)) {
				missing.add(v);
			}
		}
		return missing;
	}

	/**
	 * Recursively finds the average distance of a shortest path tree.
	 * addDist adds the distances to an array list
	 * iterates through and adds them up
	 * divides by size of array list to get average distance
	 * @param tree
	 * @param root
	 * @return
	 */
	public static <V,E> double averageSeparation(Graph<V,E> tree, V root) {
		//Variables
		List<Double> distances = new ArrayList<Double>();
		
		addDist(tree, distances, root, 1);
		double total = 0;
		for(Double dist : distances) total += dist; //add up total distance
		return total/distances.size();	
	}

	public static <V,E> void addDist(Graph<V,E> tree, List<Double> list, V vertex, double dist) {
		for(V v : tree.inNeighbors(vertex)) {
			list.add(dist);
			addDist(tree, list, v, dist + 1);
		}
	}	

	public static void main(String[] args) throws Exception {
		Graph<String, String> graph = new AdjacencyMapGraph<String, String>();
		graph.insertVertex("Kevin Bacon");
		graph.insertVertex("Bob");
		graph.insertVertex("Alice");
		graph.insertVertex("Charlie");

		graph.insertUndirected("Kevin Bacon", "Bob", "A Movie");
		graph.insertUndirected("Kevin Bacon", "Alice", "A Movie");
		graph.insertUndirected("Kevin Bacon", "Alice", "E Movie");
		graph.insertUndirected("Alice", "Bob", "A Movie");
		graph.insertUndirected("Alice", "Charlie", "D Movie");
		graph.insertUndirected("Bob", "Charlie", "C Movie");

		Graph<String, String> bfs = bfs(graph, "Charlie");
		System.out.println(bfs);
	}
}
