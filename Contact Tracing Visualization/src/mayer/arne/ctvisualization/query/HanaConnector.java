package mayer.arne.ctvisualization.query;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import mayer.arne.ctvisualization.util.Util;

// Represents a connection to a SAP Hana database instance.
public class HanaConnector
{
	private static HanaConnector athenConnector;
	private static String athenHost = "athen.eaalab.hpi.uni-potsdam.de";
	private static String athenInstance = "00";
	
	private String host;
	private String instance;
	private String user;
	private String pwd;
	
	private Connection connection;
	private boolean isConnected = false;
	
	// Creates a new Hana connection to the specified host and instance with authentication.
	public HanaConnector(String host, String instance, String user, String pwd)
	{
		// Validates the parameters
		if(host == null)
			throw new IllegalArgumentException("host is null.");
		if(instance == null)
			throw new IllegalArgumentException("instance is null.");
		
		this.host = host;
		this.instance = instance;
		this.user = user;
		this.pwd = pwd;
		
		this.connection = null;
		
		try 
		{      
			System.out.println("Connecting to " + host + " on instance " + instance + "...");
			this.connection = DriverManager.getConnection("jdbc:sap://" + this.host + ":3" + this.instance + "15/?autocommit=false", this.user, this.pwd);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	      
	    if (connection != null) 
	    {
	    	this.isConnected = true;
    		System.out.println("Connection to HANA successful!");	
	   }
	}
	
	// Gets whether the connector is connected to the database.
	public boolean isConnected()
	{
		return isConnected;
	}
	
	// Sends a test query to the hana database that should return 'hello world'.
	public String getHelloWorld()
	{
		try 
    	{
			Statement stmt = this.connection.createStatement();
			ResultSet resultSet = stmt.executeQuery("Select 'hello world' from dummy");
			resultSet.next();
			return resultSet.getString(1);
    	} 
    	catch (SQLException e) 
    	{
    		e.printStackTrace();
    		return null;
    	}
	}
	
	// Sends a query to the database and returns the result.
	public ResultSet executeQuery(String query) throws SQLException
	{
		// Validates the parameters
		if(query == null)
			throw new IllegalArgumentException("query is null.");
		
		Statement stmt = this.connection.createStatement();
		ResultSet resultSet = stmt.executeQuery(query);
		return resultSet;
	}
	
	// Gets a connection to the HPI athen Hana database instance.
	public static HanaConnector getAthenConnection()
	{
		// Creates a connector if it does not exist yet
		if(HanaConnector.athenConnector == null)
		{
			String athenUser = null;
			String athenPwd = null;
			
			try
			{
				athenUser = Util.readFile("sql/user.txt", StandardCharsets.UTF_8);
				athenPwd = Util.readFile("sql/password.txt", StandardCharsets.UTF_8);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			
			HanaConnector.athenConnector = new HanaConnector(athenHost, athenInstance, athenUser, athenPwd);
		}
		
		return HanaConnector.athenConnector;
	}
}
