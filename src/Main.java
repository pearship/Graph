import java.util.Vector;

import graph.Graph;
import graph.Edge;
import graph.Vertex;
import graph.ShortestPathAlgorithm;

public class Main {

	public static void main(String[] args) {

		Vertex n1 = new Vertex("1");
		Vertex n2 = new Vertex("2");
		Vertex n3 = new Vertex("3");
		Vertex n4 = new Vertex("4");
		Vertex n5 = new Vertex("5");
		Vertex n6 = new Vertex("6");

		Edge l1 = new Edge("1-2");
		Edge l2 = new Edge("1-3");
		Edge l3 = new Edge("1-6");
		Edge l4 = new Edge("2-3");
		Edge l5 = new Edge("2-4");
		Edge l6 = new Edge("3-4");
		Edge l7 = new Edge("3-6");
		Edge l8 = new Edge("4-5");
		Edge l9 = new Edge("5-6");

		l1.setVertices(n1, n2);
		l2.setVertices(n1, n3);
		l3.setVertices(n1, n6);
		l4.setVertices(n2, n3);
		l5.setVertices(n2, n4);
		l6.setVertices(n3, n4);
		l7.setVertices(n3, n6);
		l8.setVertices(n4, n5);
		l9.setVertices(n5, n6);

		l1.setWeight(7);
		l2.setWeight(9);
		l3.setWeight(14);
		l4.setWeight(10);
		l5.setWeight(15);
		l6.setWeight(11);
		l7.setWeight(2);
		l8.setWeight(6);
		l9.setWeight(9);

		Graph g = new Graph();
		g.addVertex(n1);
		g.addVertex(n2);
		g.addVertex(n3);
		g.addVertex(n4);
		g.addVertex(n5);
		g.addVertex(n6);
		g.addEdge(l1);
		g.addEdge(l2);
		g.addEdge(l3);
		g.addEdge(l4);
		g.addEdge(l5);
		g.addEdge(l6);
		g.addEdge(l7);
		g.addEdge(l8);
		g.addEdge(l9);

		Graph p = ShortestPathAlgorithm.dijkstra(g, "1", "5");
		p.dump();
		Vector<Graph> plist = ShortestPathAlgorithm.kShortestPaths(g, "1", "5", 3);
		for (int i = 0; i < plist.size(); i++) {
			plist.get(i).dump();			
		}
	}


}
