package mayer.arne.ctvisualization.data;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.ResultSet;

// Represents a row from the CONTACT table.
public class Contact
{
	public int IDCONTACT;
	public int IDCASE;
	public int IDPERSON;
	public Date DATELASTCONTACT;
	public String RELATIONTOCASE;
	public int CONTACTPROXIMITY;
	public String CONTACTINDIRECT;
	
	// Creates a new contact from a query result set row.
	public Contact(ResultSet resultSet)
	{
		// Validates the parameters
		if(resultSet == null)
			throw new IllegalArgumentException("resultSet is null.");
		
		try
		{
			this.IDCONTACT = resultSet.getInt("IDCONTACT");
			this.IDCASE = resultSet.getInt("IDCASE");
			this.IDPERSON = resultSet.getInt("IDPERSON");
			this.DATELASTCONTACT = resultSet.getDate("DATELASTCONTACT");
			this.RELATIONTOCASE = resultSet.getString("RELATIONTOCASE");
			this.CONTACTPROXIMITY = resultSet.getInt("CONTACTPROXIMITY");
			this.CONTACTINDIRECT = resultSet.getString("CONTACTINDIRECT");
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
				", IDPERSON:" + this.IDPERSON + ", DATELASTCONTACT: " + this.DATELASTCONTACT + 
				", RELATIONTOCASE: " + this.RELATIONTOCASE + ", CONTACTPROXIMITY: " + this.CONTACTPROXIMITY + 
				", CONTACTINDIRECT: " + this.CONTACTINDIRECT + "}";
	}
}
