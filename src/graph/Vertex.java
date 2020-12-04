package graph;

/**
 * 
 * @author PEARSHIP
 *
 */
public class Vertex implements Comparable<Vertex> {

	private String id;
	private double weighted_value = Double.POSITIVE_INFINITY;
	private String previous_vertex_id;
	private boolean done;

	
	public Vertex(String id) {
		this.id = id;
	}
	
	public int compareTo(Vertex n) {
		if (weighted_value > n.getWeightedValue()) {
			return 1;
		} else if (weighted_value < n.getWeightedValue()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public String getId() {
		return id;
	}
	
	public String getPreviousVertexId() {
		return previous_vertex_id;
	}
	
	public double getWeightedValue() {
		return weighted_value;
	}

	public boolean isDone() {
		return done;
	}
	
	public void setPreviousVertexId(String id) {
		previous_vertex_id = id;
	}

	
	public void setWeightedValue(double w) {
		weighted_value = w;
	}
	
	public void isDone(boolean done) {
		this.done = done;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Vertex)) {
			return false;
		}
		Vertex node = (Vertex) o;
		return id.equals(node.getId());
	}
	
	public Vertex copy() {
		Vertex node = new Vertex(getId());
	    node.setPreviousVertexId(getPreviousVertexId());
	    node.setWeightedValue(getWeightedValue());
	    node.isDone(isDone());
	    return node;
	}
	
}
