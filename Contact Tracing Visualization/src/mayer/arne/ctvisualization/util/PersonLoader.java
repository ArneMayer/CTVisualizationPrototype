package mayer.arne.ctvisualization.util;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import mayer.arne.ctvisualization.data.Person;
import mayer.arne.ctvisualization.query.HanaConnector;


public class PersonLoader
{
	private HanaConnector connector = HanaConnector.getAthenConnection();

	public List<Person> getPersons()
	{
		List<Person> persons = new LinkedList<Person>();
		java.sql.ResultSet result;
		
		try
		{
			result = connector.executeQuery("SELECT * FROM SORMASN.PERSON");
			while(result.next())
			{
				persons.add(new Person(result));
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return persons;
	}
}
