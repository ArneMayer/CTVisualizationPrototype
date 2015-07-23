package mayer.arne.ctvisualization.data;

import java.sql.SQLException;
import java.sql.ResultSet;

// Represents a row from the CASE table.
public class Case
{
	public int IDCASE;
	public int IDPERSON;
	public String CASECLASSIFICATION;
	public int CASENUMBER;
	public String CASESTATUS;
	public String DISEASE;
	
	// Creates a new case from a the query result set row.
	public Case(ResultSet resultSet)
	{
		// Validates the parameters
		if(resultSet == null)
			throw new IllegalArgumentException("resultSet is null.");
		
		try
		{
			this.IDCASE = resultSet.getInt("IDCASE");
			this.IDPERSON = resultSet.getInt("IDPERSON");
			this.CASECLASSIFICATION = resultSet.getString("CASECLASSIFICATION");
			this.CASENUMBER = resultSet.getInt("CASENUMBER");
			this.CASESTATUS = resultSet.getString("CASESTATUS");
			this.DISEASE = resultSet.getString("DISEASE");
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
				"CASECLASSIFICATION: " + CASECLASSIFICATION + "," +
				"CASENUMBER: " + CASENUMBER + "," +
				"CASESTATUS: " + CASESTATUS + "," +
				"DISEASE: " + DISEASE + 
				"}";
	}
}
