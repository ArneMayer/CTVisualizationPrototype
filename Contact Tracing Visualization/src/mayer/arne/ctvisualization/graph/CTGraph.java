package mayer.arne.ctvisualization.graph;

import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.DirectedSparseGraph;

// Represents the contact tracing graph structure that contains nodes and edges.
@SuppressWarnings("serial")
public class CTGraph extends DirectedSparseGraph<CTNode, CTEdge>
{
	public Map<Integer, CTNode> nodes;
	public List<CTEdge> edges;
	
	// Creates a new contact tracing graph with data within the specified time constraint.
	public CTGraph(Map<Integer, CTNode> nodes, List<CTEdge> edges)
	{
		// Validates the parameters
		if(nodes == null)
			throw new IllegalArgumentException("nodes is null.");
		if(edges == null)
			throw new IllegalArgumentException("edges is null.");
		
		this.nodes = nodes;
		this.edges = edges;
		
		// Adds the nodes to the graph
		for(CTNode node : nodes.values())
		{
			super.addVertex(node);
		}
		
		// Adds the edges to the graph
		for(CTEdge edge : edges)
		{
			CTNode startNode = nodes.get(edge.contact.IDCASE);
			CTNode endNode = nodes.get(edge.contact.IDPERSON);
			
			if(startNode != null && endNode != null)
				super.addEdge(edge, startNode, endNode);
		}
	}
}