package graph;

import java.util.PriorityQueue;
import java.util.Vector;

/**
 * 
 * @author PEARSHIP
 *
 */
public class ShortestPathAlgorithm {

	private ShortestPathAlgorithm() {
	}

	/**
	 * Dijkstra algorithm.
	 * 
	 * @param <Vertex>
	 * @param <Edge>
	 * @param <Graph>
	 * @param g
	 * @param src
	 * @param dst
	 * @return
	 */
	public static Graph dijkstra(Graph g, String src, String dst) {
		Graph g_copy = g.copy();
		g_copy.getVertex(src).setWeightedValue(0.0);
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		for (int i = 0; i < g_copy.getNumberOfVertices(); i++) {
			queue.add(g_copy.getVertex(src));
		}
		while (!queue.isEmpty()) {
			Vertex v = queue.remove();
			Vertex v_neighbor = null;
			for (int i = 0; i < g_copy.getNumberOfEdges(); i++) {
				Edge e = g_copy.getEdge(i);
				if (e.getVertex1().equals(v)) {
					v_neighbor = g_copy.getVertex(e.getVertex2().getId());
				}
				if (e.getVertex2().equals(v)) {
					v_neighbor = g_copy.getVertex(e.getVertex1().getId());
				}
				if (v_neighbor != null) {
					if (!v_neighbor.isDone()) {
						if (v_neighbor.getWeightedValue() > v.getWeightedValue() + e.getWeight()) {
							v_neighbor.setWeightedValue(v.getWeightedValue() + e.getWeight());
							v_neighbor.setPreviousVertexId(v.getId());
						}
					}
					v_neighbor = null;
				}
			}
			v.isDone(true);
			// Reset queue of nodes.
			while (!queue.isEmpty()) {
				v = queue.remove();
			}
			for (int i = 0; i < g_copy.getNumberOfVertices(); i++) {
				if (!g_copy.getVertex(i).isDone()) {
					queue.add(g_copy.getVertex(i));
				}
			}
		}
		/**
		 * Create the minimum distance route from source to destination.
		 */
		Graph path = new Graph();
		// Routing nodes.
		Vertex end = g_copy.getVertex(dst);
		path.addVertex(end);
		String prev_v_id = end.getPreviousVertexId();
		while (prev_v_id != null) {
			Vertex v = g_copy.getVertex(prev_v_id);
			path.addVertex(v);
			prev_v_id = v.getPreviousVertexId();
		}
		// Routing links.
		for (int i = 1; i < path.getNumberOfVertices(); i++) {
			Vertex v1 = path.getVertex(i - 1);
			Vertex v2 = path.getVertex(i);
			for (int j = 0; j < g_copy.getNumberOfEdges(); j++) {
				Edge e = g_copy.getEdge(j);
				if ((v1.getId().equals(e.getVertex1().getId()) && v2.getId().equals(e.getVertex2().getId()))
						|| (v2.getId().equals(e.getVertex1().getId()) && v1.getId().equals(e.getVertex2().getId()))) {
					path.addEdge(e);
				}
			}
		}
		path.reverse();
		return path;
	}

	/**
	 * Yen's K shortest paths routing algorithm.
	 * 
	 * @param <Vertex>
	 * @param <Edge>
	 * @param <Graph>
	 * @param g
	 * @param src
	 * @param dst
	 * @param knum
	 * @return
	 */
	public static Vector<Graph> kShortestPaths(Graph g, String src, String dst, int knum) {
		// Initialize A[knum] and B[] which are the heaps to store the potential k-th
		// shortest path.
		Vector<Graph> A = new Vector<Graph>();
		PriorityQueue<Graph> B = new PriorityQueue<Graph>();
		// Run dijkstra algorithm for the first shortest route.
		Graph g_copy = g.copy();
		A.add(ShortestPathAlgorithm.dijkstra(g_copy, src, dst));
		if (knum > 1) {
			for (int k = 1; k < knum; k++) {
				for (int i = 0; i < A.get(k - 1).getNumberOfEdges(); i++) {
					Graph g_buffer = g.copy();
					// Spur node from the k-shortest path
					Vertex spur_vertex = A.get(k - 1).getVertex(i);
					// Root path from the k-shortest path
					Graph root_path = A.get(k - 1).getPart(0, i);
					for (Graph path : A) {
						if (root_path.equals(path.getPart(0, i))) {
							// Remove the links that are part of the previous shortest paths which share the
							// same root path.
							g_buffer.removeEdge(path.getEdge(i).getId());
						}
					}
					for (Vertex root_path_vertex : root_path.getVertices()) {
						if (!root_path_vertex.equals(spur_vertex)) {
							g_buffer.removeVertex(root_path_vertex.getId());
							g_buffer.removeEdgesConnectedToVertex(root_path_vertex.getId());
						}
					}
					// Run dijktra from the spur node to the destination.
					Graph spur_path = ShortestPathAlgorithm.dijkstra(g_buffer, spur_vertex.getId(), dst);
					// Add the candidate path to the heap.
					if (spur_path.getNumberOfEdges() > 1) {
						B.add(root_path.connect(spur_path));
					}
				}
				if (B.isEmpty()) {
					break;
				} else {
					// Add the shortest path in the heap to A, and remove from the heap.
					A.add(B.remove());
				}
			}
		}
		return A;
	}
}
