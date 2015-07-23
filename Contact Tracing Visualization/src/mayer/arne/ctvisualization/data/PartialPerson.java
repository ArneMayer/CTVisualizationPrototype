package mayer.arne.ctvisualization.data;

import java.sql.ResultSet;
import java.sql.SQLException;

// Represents a row from the PERSON table.
public class PartialPerson
{
	public int IDPERSON;
	public String LASTNAME;
	public String FIRSTNAME;
	public String MIDDLENAME;
	
	// Creates a new person from a query result set row.
	public PartialPerson(ResultSet result) throws SQLException
	{
		// Validates the parameters
		if(result == null)
			throw new IllegalArgumentException("result is null.");
		
		this.IDPERSON = result.getInt("IDPERSON");
		this.LASTNAME = result.getString("LASTNAME");
		this.FIRSTNAME = result.getString("FIRSTNAME");
		this.MIDDLENAME = result.getString("MIDDLENAME");
	}
	
	// Gets a string representation of the person in JSON format
	@Override public String toString()
	{
		return "{" +
				"IDPERSON: " + IDPERSON + "," +
				"LASTNAME: " + LASTNAME + "," + 
				"FIRSTNAME: " + FIRSTNAME + "," + 
				"MIDDLENAME: " + MIDDLENAME + "," + 
				"}";
	}
}
