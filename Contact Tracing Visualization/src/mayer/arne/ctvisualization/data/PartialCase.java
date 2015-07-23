package mayer.arne.ctvisualization.data;

import java.sql.SQLException;
import java.sql.ResultSet;

// Represents a row from the CASE table.
public class PartialCase
{
	public int IDCASE;
	public int IDPERSON;
	
	// Creates a new case from a the query result set row.
	public PartialCase(ResultSet resultSet)
	{
		// Validates the parameters
		if(resultSet == null)
			throw new IllegalArgumentException("resultSet is null.");
		
		try
		{
			this.IDCASE = resultSet.getInt("IDCASE");
			this.IDPERSON = resultSet.getInt("IDPERSON");
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}
	
	// Gets a string representation of the case in the JSON format.
	@Override public String toString()
	{
		return "{" +
				"IDCASE: " + IDCASE + "," + 
				"IDPERSON: " + IDPERSON + "," + 
				"}";
	}
}
