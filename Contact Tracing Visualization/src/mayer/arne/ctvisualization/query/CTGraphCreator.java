package mayer.arne.ctvisualization.query;

import mayer.arne.ctvisualization.graph.CTGraph;

// Represents a query for the complete contact tracing graph.
public class CTGraphCreator
{
	private CTNodeQuery nodeQuery;
	private CTEdgeQuery edgeQuery;
	
	// Creates a new graph creator
	public CTGraphCreator()
	{
		this.nodeQuery = new CTNodeQuery();
		this.edgeQuery = new CTEdgeQuery();
	}
	
	// Creates a new contact tracing graph by querying nodes and edges.
	public CTGraph getGraph()
	{
		return new CTGraph(this.nodeQuery.getNodes(), this.edgeQuery.getEdges());
	}
}
