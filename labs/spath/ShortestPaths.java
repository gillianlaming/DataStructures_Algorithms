package spath;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import heaps.Decreaser;
import heaps.MinHeap;
import spath.graphs.DirectedGraph;
import spath.graphs.Edge;
import spath.graphs.Vertex;
import timing.Ticker;
import spath.VertexAndDist;


// SHORTESTPATHS.JAVA
// Compute shortest paths in a graph.
//
// Your constructor should compute the actual shortest paths and
// maintain all the information needed to reconstruct them.  The
// returnPath() function should use this information to return the
// appropriate path of edge ID's from the start to the given end.
//
// Note that the start and end ID's should be mapped to vertices using
// the graph's get() function.
//
// You can ignore the input and startTime arguments to the constructor
// unless you are doing the extra credit.
//
public class ShortestPaths {
	private final static Integer inf = Integer.MAX_VALUE;
	private HashMap<Vertex, Decreaser<VertexAndDist>> map;
	private HashMap<Vertex, Edge> toEdge;
	private Map<Edge, Integer> weights;
	private Vertex startVertex;
	private final DirectedGraph g;


	//
	// constructor
	//
	public ShortestPaths(DirectedGraph g, Vertex startVertex, Map<Edge,Integer> weights) {

		this.map         = new HashMap<Vertex, Decreaser<VertexAndDist>>();
		this.toEdge      = new HashMap<Vertex, Edge>();
		this.weights     = weights;
		this.startVertex = startVertex;
		this.g           = g;
	}

	//
	// this method does all the real work
	//
	public void run() {
		Ticker ticker = new Ticker();
		MinHeap<VertexAndDist> pq = new MinHeap<VertexAndDist>(30000, ticker);

		//
		// Initially all vertices are placed in the heap
		//   infinitdely far away from the start vertex
		//

		for (Vertex v : g.vertices()) {
			toEdge.put(v, null);
			VertexAndDist a = new VertexAndDist(v, inf); //maybe use this to store new values? 
			Decreaser<VertexAndDist> d = pq.insert(a);
			map.put(v, d);
		}
		//
		// Now we decrease the start node's distance from
		//   itself to 0.
		// Note how we look up the decreaser using the map...
		// 
		Decreaser<VertexAndDist> startVertDist = map.get(startVertex);
		//
		// and then decrease it using the Decreaser handle
		//
		startVertDist.decrease(startVertDist.getValue().sameVertexNewDistance(0)); //how to decrease object from start 
		//
		// OK you take it from here
		// Extract nodes from the pq heap
		//   and act upon them as instructed in class and the text.
		//

		
		while (!pq.isEmpty()) {
			VertexAndDist a = pq.extractMin();
			Vertex v = a.getVertex();
			for (Edge e : v.edgesFrom()) {
				Decreaser<VertexAndDist> newVertexDist = map.get(e.to); 
				int weight = weights.get(e);
				int dist = a.getDistance();
				int total = weight + dist;
				if (total < newVertexDist.getValue().getDistance()) {
					newVertexDist.decrease(newVertexDist.getValue().sameVertexNewDistance(total));
					toEdge.put(newVertexDist.getValue().getVertex(), e);
				}
			}
			
		}
		
		
		
		
		
		//this is just for the start vertex
//		pq.extractMin(); //extract the min
//		Edge start = new Edge(startVertex, startVertex); //creating the edge
//		toEdge.put(startVertex, start); //putting the edge on the map //but this isnt really an edge
//		weights.put(start, pq.extractMin().getDistance()); //adding the weight
//		//update the node???
//
//
//		//relax edges
//		for (Vertex v: g.vertices()) {
//
//			//see how many edges v has
//			//find distance and insert that into the heap
//
//			for (Edge edge: v.edgesFrom()) {
//				Decreaser<VertexAndDist> newVertexDist = map.get(v); 
//				newVertexDist.decrease(newVertexDist.getValue()); //decreased
//				map.put(v, newVertexDist); //idk if i need to do this...is the map already made??
//				//weights.put(edge, newVertexDist.getValue().getDistance()); //is this step necessary? //do u only do this for the shortest path?
//
//			}
//			//extract min and update node for all edges
//			for (Edge edge: v.edgesFrom()) { 			//this loop will run the #of edges times
//				Edge e = new Edge(v, pq.extractMin().getVertex());
//				weights.put(e, pq.extractMin().getDistance());
//			}
//			
//



			//			pq.extractMin().getDistance();
			//			Decreaser<VertexAndDist> newVertex = map.get(weights);
			//			newVertex.decrease(newVertex.getValue());
			//			pq.extractMin();

		}

	


	/**
	 * Return a List of Edges forming a shortest path from the
	 *    startVertex to the specified endVertex.  Do this by tracing
	 *    backwards from the endVertex, using the map you maintain
	 *    during the shortest path algorithm that indicates which
	 *    Edge is used to reach a Vertex on a shortest path from the
	 *    startVertex.
	 * @param endVertex 
	 * @return
	 */
	public LinkedList<Edge> returnPath(Vertex endVertex) {
		LinkedList<Edge> path = new LinkedList<Edge>();
		
		path.addFirst(toEdge.get(endVertex));
		Edge e = toEdge.get(endVertex);
		Vertex v = e.from;
		while (v != startVertex){
			e = toEdge.get(v);
			v = e.from;
			path.addFirst(e);
		}
			
//		
//		//endVertex.edgesTo()
//		//figure out the quickest way from start vertex to end vertex
//		for (Vertex v : endVertex) { //make endVertex iterable
//			v.
//		}

		//path.add(e); //add shortest edge


		return path;
	}

	/**
	 * Return the length of all shortest paths.  This method
	 *    is completed for you, using your solution to returnPath.
	 * @param endVertex
	 * @return
	 */
	public int returnValue(Vertex endVertex) {
		LinkedList<Edge> path = returnPath(endVertex);
		int pathValue = 0;
		for(Edge e : path) {
			pathValue += weights.get(e);
		}

		return pathValue;

	}
}
