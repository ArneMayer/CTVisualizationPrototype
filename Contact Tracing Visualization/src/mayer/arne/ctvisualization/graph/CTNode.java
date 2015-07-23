package mayer.arne.ctvisualization.graph;

import mayer.arne.ctvisualization.data.PartialCase;
import mayer.arne.ctvisualization.data.PartialPerson;

// Represents a node in the contact tracing graph that represents a case or a person.
public class CTNode
{
	public PartialPerson person;
	public PartialCase caseInfo;
	private int visitCount;
	private int symptomCount;
	
	// Creates a new contact tracing node
  	public CTNode(PartialPerson person, PartialCase caseInfo, int visitCount, int symptomCount)
	{
  		// Validates the parameters
  		if(person == null)
  			throw new IllegalArgumentException("personInfo is null");
  		
		this.person = person;
		this.caseInfo = caseInfo;
		this.visitCount = visitCount;
		this.symptomCount = symptomCount;
	}
  	
  	// Gets whether this person is already a case
  	public boolean isCase()
  	{
  		return (this.caseInfo != null && this.caseInfo.IDCASE > 0);
  	}

  	// Gets the label text that is displayed for this node in the visualization
	public String getLabel()
	{
		if(this.person == null || (this.person.FIRSTNAME == null && this.person.MIDDLENAME == null && this.person.LASTNAME == null))
		{
			return "Unknown Person";
		}
		else
		{
			// Constructs a label from the persons name
			String label = "";
			if(this.person.FIRSTNAME != null) label += this.person.FIRSTNAME;
			if(this.person.MIDDLENAME != null) label += " " + this.person.MIDDLENAME;
			if(this.person.LASTNAME != null) label += " " + this.person.LASTNAME;
			return label;
		}
	}
	
	// Gets the trace status of the person, which is the ratio of the visit count to the complete number of visits to be made.
	public double getTraceStatus()
	{
		double status = this.visitCount / 21.0;
		if(status < 0.0) status = 0.0;
		if(status > 1.0) status = 1.0;
		
		return status;
	}
	
	// Gets the symptomatic status of the person, which is the ratio of symptom count to the complete number of possible symptoms.
	public double getSymptomStatus()
	{
		double status = this.symptomCount / 9.0;
		if(status < 0.0) status = 0.0;
		if(status > 1.0) status = 1.0;
		
		return status;
	}
}
