package mayer.arne.ctvisualization.data;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

// Represents a row from the PERSON table.
public class Person
{
	public int IDPERSON;
	public String LASTNAME;
	public String FIRSTNAME;
	public String MIDDLENAME;
	public String ETHNICITY;
	public String RELIGION;
	public Date DATEOFFIRSTREPORT;
	public int IDPERSONREFERENCE;
	public String TYPEPERSONREFERENCE;
	public String VITALSTATE;
	public Date DATEOFBIRTH;
	public int AGE;
	public Date DATEOFDEATH;
	public Date DATEOFBURIAL;
	public String SEX;
	public String MARITALSTATE;
	public String MOBILITY;
	public String PHONENUMBER1;
	public String PHONENUMBER1DESCRIPTION;
	public String PHONENUMBER2;
	public String PHONENUMBER2DESCRIPTION;
	public String PHONENUMBER3;
	public String PHONENUMBER3DESCRIPTION;
	public String CONFIRMED;
	
	// Creates a new person from a query result set row.
	public Person(ResultSet result) throws SQLException
	{
		// Validates the parameters
		if(result == null)
			throw new IllegalArgumentException("result is null.");
		
		this.IDPERSON = result.getInt("IDPERSON");
		this.LASTNAME = result.getString("LASTNAME");
		this.FIRSTNAME = result.getString("FIRSTNAME");
		this.MIDDLENAME = result.getString("MIDDLENAME");
		this.ETHNICITY = result.getString("ETHNICITY");
		this.RELIGION = result.getString("RELIGION");
		this.DATEOFFIRSTREPORT = result.getDate("DATEOFFIRSTREPORT");
		this.IDPERSONREFERENCE = result.getInt("IDPERSONREFERENCE");
		this.TYPEPERSONREFERENCE = result.getString("TYPEPERSONREFERENCE");
		this.VITALSTATE = result.getString("VITALSTATE");
		this.DATEOFBIRTH = result.getDate("DATEOFBIRTH");
		this.AGE = result.getInt("AGE");
		this.DATEOFDEATH = result.getDate("DATEOFDEATH");
		this.DATEOFBURIAL = result.getDate("DATEOFBURIAL");
		this.SEX = result.getString("SEX");
		this.MARITALSTATE = result.getString("MARITALSTATE");
		this.MOBILITY = result.getString("MOBILITY");
		this.PHONENUMBER1 = result.getString("PHONENUMBER1");
		this.PHONENUMBER1DESCRIPTION = result.getString("PHONENUMBER1DESCRIPTION");
		this.PHONENUMBER2 = result.getString("PHONENUMBER2");
		this.PHONENUMBER2DESCRIPTION = result.getString("PHONENUMBER2DESCRIPTION");
		this.PHONENUMBER3 = result.getString("PHONENUMBER3");
		this.PHONENUMBER3DESCRIPTION = result.getString("PHONENUMBER3DESCRIPTION");
		this.CONFIRMED = result.getString("CONFIRMED");
	}
	
	// Gets a string representation of the person in JSON format
	@Override public String toString()
	{
		return "{" +
				"IDPERSON: " + IDPERSON + "," +
				"LASTNAME: " + LASTNAME + "," + 
				"FIRSTNAME: " + FIRSTNAME + "," + 
				"MIDDLENAME: " + MIDDLENAME + "," + 
				"ETHNICITY: " + ETHNICITY + "," + 
				"RELIGION: " + RELIGION + "," + 
				"DATEOFFIRSTREPORT: " + DATEOFFIRSTREPORT + "," + 
				"IDPERSONREFERENCE: " + IDPERSONREFERENCE + "," + 
				"TYPEPERSONREFERENCE: " + TYPEPERSONREFERENCE + "," + 
				"VITALSTATE: " + VITALSTATE + "," + 
				"DATEOFBIRTH: " + DATEOFBIRTH + "," + 
				"AGE: " + AGE + "," + 
				"DATEOFDEATH: " + DATEOFDEATH + "," +
				"DATEOFBURIAL: " + DATEOFBURIAL + "," +
				"SEX: " + SEX + "," +
				"MARITALSTATE: " + MARITALSTATE + "," +
				"MOBILITY: " + MOBILITY + "," +
				"PHONENUMBER1: " + PHONENUMBER1 + "," +
				"PHONENUMBER1DESCRIPTION: " + PHONENUMBER1DESCRIPTION + "," +
				"PHONENUMBER2: " + PHONENUMBER1 + "," +
				"PHONENUMBER2DESCRIPTION: " + PHONENUMBER1DESCRIPTION + "," +
				"PHONENUMBER3: " + PHONENUMBER1 + "," +
				"CONFIRMED: " + CONFIRMED +
				"}";
	}
}
