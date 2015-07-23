package mayer.arne.ctvisualization.query;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mayer.arne.ctvisualization.data.PartialCase;
import mayer.arne.ctvisualization.data.PartialPerson;
import mayer.arne.ctvisualization.graph.CTNode;
import mayer.arne.ctvisualization.util.Util;

// Represents a database query that gets all relevant persons and if possible their case information
public class CTNodeQuery
{
	private HanaConnector connector = HanaConnector.getAthenConnection();

	/*
		Gets all the persons with their possible case information as well as their trace status and symptomatic status and saves them as a list of nodes.
		Only those persons will be returned that are either a case or have had contact to a case.
		Persons that are not a case and did not have contact to one are not subject to the contact tracing process
		because they do not need to be monitored and are therefore left out.
	*/
	public Map<Integer, CTNode> getNodes(/*Date startDate, Date endDate*/)
	{
		Map<Integer, CTNode> nodes = new HashMap<Integer, CTNode>();
		java.sql.ResultSet result;
		
		try
		{
			String query = Util.readFile("sql/NodeQuery.sql", StandardCharsets.UTF_8);
			
			// Get all persons with their case information that are either a case or had contact to one.
			// Additionally the number of visits and the number of symptoms in the latest visit is returned
			result = connector.executeQuery(query);
			while(result.next())
			{
				PartialCase caseInfo = null;
				
				// An id of 0 indicates that no case information is available
				if(result.getInt("IDCASE") != 0)
				{
					caseInfo = new PartialCase(result);
				}
				PartialPerson personInfo = new PartialPerson(result);
				int visitCount = result.getInt("VISITCOUNT");
				int symptomCount = result.getInt("SYMPTOMCOUNT");
				
				// Save the node mapped to the person id for fast read operations
				nodes.put(personInfo.IDPERSON, new CTNode(personInfo, caseInfo, visitCount, symptomCount));
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
		
		return nodes;
	}
	
	/*
	public static void main(String[] args)
	{
		CTNodeQuery query = new CTNodeQuery();
		for(CTNode node : query.getNodes().values())
		{
			System.out.println(node.caseInformation);
			System.out.println(node.personInformation);
		}
	}
	*/
}
