package mayer.arne.ctvisualization.util;
import java.sql.SQLException;

import mayer.arne.ctvisualization.query.HanaConnector;


public class SormasPrinter
{
	private HanaConnector connector = HanaConnector.getAthenConnection();
	
	public void printPersons()
	{
		java.sql.ResultSet result;
		
		try
		{
			result = connector.executeQuery("SELECT * FROM SORMASN.PERSON");
			while(result.next())
			{
				for(int i = 1; i < 26; i++)
				{
					System.out.print(result.getString(i) + " |");
				}
				System.out.println();
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
