package graph;

public class Edge {

	private String id;
	private Vertex node1;
	private Vertex node2;
	private double weight = 0.0;

	
	public Edge(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public Vertex getVertex1() {
		return node1;
	}
	
	public Vertex getVertex2() {
		return node2;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setVertices(Vertex v1, Vertex v2) {
		node1 = v1;
		node2 = v2;
	}
	
	public void setWeight(double w) {
		weight = w;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Edge)) {
			return false;
		}
		Edge link = (Edge) o;
		return id.equals(link.getId()) && node1.equals(link.getVertex1()) && node2.equals(link.getVertex2());
	}

	public Edge copy() {
		Edge link = new Edge(getId());
		link.setVertices(getVertex1(), getVertex2());
		link.setWeight(getWeight());
		return link;
	}

}
