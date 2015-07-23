package mayer.arne.ctvisualization.visualization;

import java.awt.Dimension;

import edu.uci.ics.jung.algorithms.layout.*;
import mayer.arne.ctvisualization.graph.CTEdge;
import mayer.arne.ctvisualization.graph.CTGraph;
import mayer.arne.ctvisualization.graph.CTNode;

// Represents a layout for the contact tracing visualization
public class CTVisualizationLayoutCreator
{
	private Layout<CTNode, CTEdge> layout;

	// Creates a new visualization layout in the defined size for the specified layout option and contact tracing graph.
	public CTVisualizationLayoutCreator(int width, int height, CTLayoutOption layoutOption, CTGraph graph)
	{
		// Validates the parameters
		if(width <= 0)
			throw new IllegalArgumentException("width is <= 0.");
		if(height <= 0)
			throw new IllegalArgumentException("height is <= 0.");
		if(layoutOption == null)
			throw new IllegalArgumentException("layoutOption is null.");
		if(graph == null)
			throw new IllegalArgumentException("graph is null.");
				
		// Creates a layout for node positioning
		if(layoutOption == CTLayoutOption.CircleLayout)
		{
			this.layout = new CircleLayout<CTNode, CTEdge>(graph);
		}
		else if (layoutOption == CTLayoutOption.FRLayout)
		{
			this.layout = new FRLayout<CTNode, CTEdge>(graph);
		}
		else if (layoutOption == CTLayoutOption.FRLayout2)
		{
			this.layout = new FRLayout2<CTNode, CTEdge>(graph);
		}
		else if (layoutOption == CTLayoutOption.DAGLayout)
		{
			this.layout = new DAGLayout<CTNode, CTEdge>(graph);
		}
		else if (layoutOption == CTLayoutOption.SpringLayout)
		{
			this.layout = new SpringLayout<CTNode, CTEdge>(graph);
		}
		else if (layoutOption == CTLayoutOption.StaticLayout)
		{
			this.layout = new StaticLayout<CTNode, CTEdge>(graph);
		}
		else if (layoutOption == CTLayoutOption.ISOMLayout)
		{
			this.layout = new ISOMLayout<CTNode, CTEdge>(graph);
		}
		else if (layoutOption == CTLayoutOption.KKLayout)
		{
			this.layout = new KKLayout<CTNode, CTEdge>(graph);
		}
		
		layout.setSize(new Dimension(width, height));
	}
	
	// Gets the layout object for the JUNG framework.
	public Layout<CTNode, CTEdge> getLayout()
	{
		return this.layout;
	}
}
