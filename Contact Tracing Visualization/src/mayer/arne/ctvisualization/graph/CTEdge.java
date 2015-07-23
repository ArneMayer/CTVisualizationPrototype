package mayer.arne.ctvisualization.graph;

import mayer.arne.ctvisualization.data.PartialContact;

// Represents an edge in the contact tracing graph that represents a contact.
public class CTEdge
{
	public PartialContact contact;
	
	// Creates a new edge from a contact
	public CTEdge(PartialContact contact)
	{
		// Validates the parameters
		if(contact == null)
			throw new IllegalArgumentException("contact is null.");
		
		this.contact = contact;
	}
}
