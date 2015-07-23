package mayer.arne.ctvisualization.data;

import java.sql.SQLException;
import java.sql.ResultSet;

// Represents a row from the CONTACT table.
public class PartialContact
{
	public int IDCONTACT;
	public int IDCASE;
	public int IDPERSON;
	
	// Creates a new contact from a query result set row.
	public PartialContact(ResultSet resultSet)
	{
		// Validates the parameters
		if(resultSet == null)
			throw new IllegalArgumentException("resultSet is null.");
		
		try
		{
			this.IDCONTACT = resultSet.getInt("IDCONTACT");
			this.IDCASE = resultSet.getInt("IDCASE");
			this.IDPERSON = resultSet.getInt("IDPERSON");
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
	}
	
	// Gets a string representation of this contact in JSON format.
	@Override public String toString()
	{
		return "{IDCONTACT: " + this.IDCONTACT + ", IDCASE: " + this.IDCASE + 
				", IDPERSON:" + this.IDPERSON + "}";
	}
}
