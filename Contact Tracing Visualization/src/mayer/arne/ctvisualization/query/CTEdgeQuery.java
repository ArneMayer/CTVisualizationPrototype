package mayer.arne.ctvisualization.query;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import mayer.arne.ctvisualization.data.PartialContact;
import mayer.arne.ctvisualization.graph.CTEdge;
import mayer.arne.ctvisualization.util.Util;

// Represents a database query that gets all the contacts and saves them as graph edges.
public class CTEdgeQuery
{
	private HanaConnector connector = HanaConnector.getAthenConnection();

	// Fetches the contacts and returns them as a list of edges.
	public List<CTEdge> getEdges(/*Date startDate, Date endDate*/)
	{
		List<CTEdge> edges = new LinkedList<CTEdge>();
		java.sql.ResultSet result;
		
		try
		{
			String query = Util.readFile("sql/EdgeQuery.sql", StandardCharsets.UTF_8);
			
			// Sends a query to get all contacts to the database
			result = connector.executeQuery(query);
			
			// Embeds every contact into an edge
			while(result.next())
			{
				edges.add(new CTEdge(new PartialContact(result)));
			}
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			return null;
		}
		
		return edges;
	}
	
	/*
	public static void main(String[] args)
	{
		CTEdgeQuery query = new CTEdgeQuery();
		for(CTEdge edge : query.getEdges())
		{
			System.out.println(edge.contactInformation.toString());
		}
	}
	*/
}
