package graph;

import java.util.Collections;
import java.util.Vector;

/**
 * 
 * @author PEARSHIP
 *
 */
public class Graph implements Comparable<Graph> {

	private Vector<Vertex> vertices;
	private Vector<Edge> edges;

	public Graph() {
		vertices = new Vector<Vertex>();
		edges = new Vector<Edge>();
	}

	public Graph copy() {
		Graph g = new Graph();
		Vector<Vertex> v_list = new Vector<Vertex>();
		Vector<Edge> e_list = new Vector<Edge>();
		for (int i = 0; i < vertices.size(); i++) {
			v_list.add(vertices.get(i).copy());
		}
		g.setVertices(v_list);
		for (int i = 0; i < edges.size(); i++) {
			e_list.add(getEdge(i).copy());
		}
		for (int i = 0; i < e_list.size(); i++) {
			e_list.get(i).setVertices(g.getVertex(e_list.get(i).getVertex1().getId()),
					g.getVertex(e_list.get(i).getVertex2().getId()));
		}
		g.setEdges(e_list);
		return g;
	}

	public Graph connect(Graph g) {
		Graph g_merged = copy();
		if (g_merged.getVertices().lastElement().equals(g.getVertices().firstElement())) {
			g_merged.removeVertex(g_merged.getVertices().lastElement().getId());
			if (g_merged.getNumberOfVertices() > 0) {
				g.getVertex(0).setPreviousVertexId(g_merged.getVertices().lastElement().getId());
				for (int i = 0; i < g.getNumberOfVertices(); i++) {
					g_merged.addVertex(g.getVertex(i));
				}
				for (int i = 0; i < g.getNumberOfEdges(); i++) {
					g_merged.addEdge(g.getEdge(i));
				}
				// Reset the weighted distances of merged graph.
				double wv = 0.0;
				g_merged.getVertex(0).setWeightedValue(wv);
				for (int i = 0; i < g_merged.getNumberOfEdges(); i++) {
					wv = wv + (g_merged.getEdge(i).getWeight());
					g_merged.getVertex(i + 1).setWeightedValue(wv);
				}
				return g_merged;
			} else {
				// object graph includes the basis graph.
				return g;
			}
		} else {
			System.err.println("Append error: Two graphs are not matched.");
			return null;
		}
	}

	public Graph getPart(int begin, int end) {
		if (begin < 0) {
			begin = 0;
		}
		if (end >= vertices.size()) {
			end = vertices.size() - 1;
		}
		if (begin >= 0 && end < vertices.size()) {
			Graph part = new Graph();
			int v_idx = begin;
			int e_idx = begin;
			while (v_idx <= end) {
				part.addVertex(vertices.get(v_idx));
				v_idx++;
			}
			while (e_idx <= end - 1) {
				part.addEdge(edges.get(e_idx));
				e_idx++;
			}
			return part;
		} else {
			return null;
		}
	}

	public boolean equals(Object o) {
		if (!(o instanceof Graph)) {
			return false;
		}
		Graph g = (Graph) o;
		if (vertices.size() == g.getNumberOfVertices() && edges.size() == g.getNumberOfEdges()) {
			int v_num = 0;
			int e_num = 0;
			for (int i = 0; i < vertices.size(); i++) {
				for (int j = 0; j < g.getNumberOfVertices(); j++) {
					if (vertices.get(i).equals(g.getVertex(j))) {
						v_num++;
					}
				}
			}
			for (int i = 0; i < edges.size(); i++) {
				for (int j = 0; j < g.getNumberOfEdges(); j++) {
					if (edges.get(i).equals(g.getEdge(j))) {
						e_num++;
					}
				}
			}
			return v_num == vertices.size() && e_num == edges.size();
		} else {
			return false;
		}
	}
	
	public int compareTo(Graph g) {
		if (vertices.lastElement().getWeightedValue() > g.getVertices().lastElement().getWeightedValue()) {
			return 1;
		} else if (vertices.lastElement().getWeightedValue() < g.getVertices().lastElement().getWeightedValue()) {
			return -1;
		} else {
			return 0;
		}
	}

	public void setVertices(Vector<Vertex> v) {
		vertices.addAll(v);
	}

	public void setEdges(Vector<Edge> e) {
		edges.addAll(e);
	}

	public void clear() {
		vertices.clear();
		edges.clear();
	}

	public void reverse() {
		Collections.reverse(vertices);
		Collections.reverse(edges);
	}

	public void addVertex(Vertex n) {
		vertices.add(n);
	}

	public void addEdge(Edge l) {
		edges.add(l);
	}

	public Vector<Vertex> getVertices() {
		return vertices;
	}

	public Vector<Edge> getEdges() {
		return edges;
	}

	public Vertex getVertex(int i) {
		return vertices.get(i);
	}

	public Vertex getVertex(String id) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getId().equals(id)) {
				return vertices.get(i);
			}
		}
		return null;
	}

	public Edge getEdge(int i) {
		return edges.get(i);
	}

	public Edge getEdge(String id) {
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getId().equals(id)) {
				return edges.get(i);
			}
		}
		return null;
	}

	public int getNumberOfVertices() {
		return vertices.size();
	}

	public int getNumberOfEdges() {
		return edges.size();
	}

	public boolean containsVertex(String id) {
		int i = 0;
		while (true) {
			if (i == vertices.size()) {
				return false;
			}
			if (getVertex(i).getId().equals(id)) {
				return true;
			}
			i++;
		}
	}

	public boolean containsEdge(String id) {
		int i = 0;
		while (true) {
			if (i == edges.size()) {
				return false;
			}
			if (edges.get(i).getId().equals(id)) {
				return true;
			}
			i++;
		}
	}

	public void removeVertex(String id) {
		int i = 0;
		while (true) {
			if (i == vertices.size()) {
				return;
			}
			if (vertices.get(i).getId().equals(id)) {
				vertices.remove(i);
				return;
			}
			i++;
		}
	}

	public void removeEdge(String id) {
		int i = 0;
		while (true) {
			if (i == edges.size()) {
				return;
			}
			if (edges.get(i).getId().equals(id)) {
				edges.remove(i);
				return;
			}
			i++;
		}
	}

	/**
	 * 
	 */
	public void removeEdgesConnectedToVertex(String id) {
		Vector<String> remove_ids = new Vector<String>();
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getVertex1().getId().equals(id) || edges.get(i).getVertex2().getId().equals(id)) {
				remove_ids.add(edges.get(i).getId());
			}
		}
		for (int i = 0; i < remove_ids.size(); i++) {
			removeEdge(remove_ids.get(i));
		}
	}

	/**
	 * This function displays a graph path.
	 */
	public void dump() {
		String str = "V = {";
		for (int i = 0; i < vertices.size(); i++) {
			str += vertices.get(i).getId();
			if (i < vertices.size() - 1) {
				str += ", ";
			}
		}
		str += "}\nE = {";
		for (int i = 0; i < edges.size(); i++) {
			str += edges.get(i).getId();
			if (i < edges.size() - 1) {
				str += ", ";
			}
		}
		str += "}";
		System.out.println(str);
	}

}
