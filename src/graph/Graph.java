package graph;

import java.util.Collections;
import java.util.Vector;

/**
 * 
 * @author PEARSHIP
 *
 */
public class Graph {

	private Vector<Vertex> nodes;
	private Vector<Edge> links;

	public Graph() {
		nodes = new Vector<Vertex>();
		links = new Vector<Edge>();
	}

	public Graph copy() {
		Graph g = new Graph();
		Vector<Vertex> v_list = new Vector<Vertex>();
		Vector<Edge> e_list = new Vector<Edge>();
		for (int i = 0; i < nodes.size(); i++) {
			v_list.add(nodes.get(i).copy());
		}
		g.setVertices(v_list);
		for (int i = 0; i < links.size(); i++) {
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
		if (end >= nodes.size()) {
			end = nodes.size() - 1;
		}
		if (begin >= 0 && end < nodes.size()) {
			Graph part = new Graph();
			int v_idx = begin;
			int e_idx = begin;
			while (v_idx <= end) {
				part.addVertex(nodes.get(v_idx));
				v_idx++;
			}
			while (e_idx <= end - 1) {
				part.addEdge(links.get(e_idx));
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
		if (nodes.size() == g.getNumberOfVertices() && links.size() == g.getNumberOfEdges()) {
			int v_num = 0;
			int e_num = 0;
			for (int i = 0; i < nodes.size(); i++) {
				for (int j = 0; j < g.getNumberOfVertices(); j++) {
					if (nodes.get(i).equals(g.getVertex(j))) {
						v_num++;
					}
				}
			}
			for (int i = 0; i < links.size(); i++) {
				for (int j = 0; j < g.getNumberOfEdges(); j++) {
					if (links.get(i).equals(g.getEdge(j))) {
						e_num++;
					}
				}
			}
			return v_num == nodes.size() && e_num == links.size();
		} else {
			return false;
		}
	}

	public void setVertices(Vector<Vertex> v) {
		nodes.addAll(v);
	}

	public void setEdges(Vector<Edge> e) {
		links.addAll(e);
	}

	public void clear() {
		nodes.clear();
		links.clear();
	}

	public void reverse() {
		Collections.reverse(nodes);
		Collections.reverse(links);
	}

	public void addVertex(Vertex n) {
		nodes.add(n);
	}

	public void addEdge(Edge l) {
		links.add(l);
	}

	public Vector<Vertex> getVertices() {
		return nodes;
	}

	public Vector<Edge> getEdges() {
		return links;
	}

	public Vertex getVertex(int i) {
		return nodes.get(i);
	}

	public Vertex getVertex(String id) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getId().equals(id)) {
				return nodes.get(i);
			}
		}
		return null;
	}

	public Edge getEdge(int i) {
		return links.get(i);
	}

	public Edge getEdge(String id) {
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getId().equals(id)) {
				return links.get(i);
			}
		}
		return null;
	}

	public int getNumberOfVertices() {
		return nodes.size();
	}

	public int getNumberOfEdges() {
		return links.size();
	}

	public boolean containsVertex(String id) {
		int i = 0;
		while (true) {
			if (i == nodes.size()) {
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
			if (i == links.size()) {
				return false;
			}
			if (links.get(i).getId().equals(id)) {
				return true;
			}
			i++;
		}
	}

	public void removeVertex(String id) {
		int i = 0;
		while (true) {
			if (i == nodes.size()) {
				return;
			}
			if (nodes.get(i).getId().equals(id)) {
				nodes.remove(i);
				return;
			}
			i++;
		}
	}

	public void removeEdge(String id) {
		int i = 0;
		while (true) {
			if (i == links.size()) {
				return;
			}
			if (links.get(i).getId().equals(id)) {
				links.remove(i);
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
		for (int i = 0; i < links.size(); i++) {
			if (links.get(i).getVertex1().getId().equals(id) || links.get(i).getVertex2().getId().equals(id)) {
				remove_ids.add(links.get(i).getId());
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
		for (int i = 0; i < nodes.size(); i++) {
			str += nodes.get(i).getId();
			if (i < nodes.size() - 1) {
				str += ", ";
			}
		}
		str += "}\nE = {";
		for (int i = 0; i < links.size(); i++) {
			str += links.get(i).getId();
			if (i < links.size() - 1) {
				str += ", ";
			}
		}
		str += "}";
		System.out.println(str);
	}

}
