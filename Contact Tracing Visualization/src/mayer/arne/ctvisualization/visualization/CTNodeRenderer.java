package mayer.arne.ctvisualization.visualization;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import mayer.arne.ctvisualization.graph.CTEdge;
import mayer.arne.ctvisualization.graph.CTNode;

// Represents the node renderer that paints each node.
class CTNodeRenderer implements Renderer.Vertex<CTNode, CTEdge>
{
	// Pains the vertex at the correct position and with regards to the node properties.
    @Override public void paintVertex(RenderContext<CTNode, CTEdge> rc, Layout<CTNode, CTEdge> layout, CTNode node)
    {
    	GraphicsDecorator graphicsContext = rc.getGraphicsContext();
    	
    	// Transforms the node according to the layout
    	Point2D center = layout.transform(node);
    	
    	// Transforms the node according to the user transformations (moving, scaling, shearing)
    	center = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, center);
    	
    	// Determines the bounds of the node
    	double w = 20.0;
    	double h = 20.0;
    	double x = center.getX() - w / 2;
    	double y = center.getY() - h / 2;
    	
    	// People that are being visited are painted using a pie inside
    	// The size of the pie resembles the visit count
    	// The smallest angle is 20° (for visibility)
    	double angle = node.getTraceStatus() * 360.0;
    	if(angle < 25.0) angle = 25.0;
    	Shape innerShape = new Arc2D.Double(x, y, w, h, 90.0, -angle, Arc2D.PIE); 
    	
    	// Overdraw the edge
    	graphicsContext.setColor(Color.WHITE);
    	graphicsContext.fillOval((int)x, (int)y, (int)w, (int)h);
    	
    	// Cases are painted completely in red
    	if(node.isCase())
    	{
    		graphicsContext.setColor(Color.RED);
        	graphicsContext.fillOval((int)x, (int)y, (int)w, (int)h);
    	}
    	// People with at least one visit are painted with the pie
    	else if(node.getTraceStatus() > 0 && node.getTraceStatus() < 0.99)
    	{
    		// Pie with color according to symptomatic status
    		graphicsContext.setColor(this.getColorForNode(node));
    		graphicsContext.fill(innerShape);
    		
    		// Black border
    		graphicsContext.setColor(Color.BLACK);
        	graphicsContext.draw(innerShape);
    	}
    	// People with completed tracing can be drawn as filled circles without pie shape
    	else if(node.getTraceStatus() >= 0.99)
    	{
    		graphicsContext.setColor(this.getColorForNode(node));
        	graphicsContext.fillOval((int)x, (int)y, (int)w, (int)h);
    	}
    	
    	// Outer shape (circle) border
    	graphicsContext.setColor(Color.BLACK);
    	graphicsContext.drawOval((int)x, (int)y, (int)w, (int)h);
    	
    }

    // Gets the color for a node from node properties.
	private Color getColorForNode(CTNode node)
	{
		// Cases are painted in red
		 if(node.isCase())
		 {
			 return Color.RED;
		 }
		 else
		 {
			 // People that have not been visited yet are painted white
			 if(node.getTraceStatus() == 0)
				 return Color.WHITE;
			 // People that have no symptoms are painted green
			 else if(node.getSymptomStatus() == 0)
				 return Color.GREEN;
			 // People with symptoms are painted according to their symptomatic status
			 else
			 {
				 //Color color1 = new Color(0xFFFFCC);
				 Color color1 = Color.YELLOW;
				 Color color2 = new Color(0xFA3E00);
				 
				 int r1 = color1.getRed(); 
				 int g1 = color1.getGreen(); 
				 int b1 = color1.getBlue(); 
				 
				 int r2 = color2.getRed(); 
				 int g2 = color2.getGreen(); 
				 int b2 = color2.getBlue(); 
				 
				 double d = node.getSymptomStatus();
				 
				 // The color is interpolated between the two colors.
				 return new Color((int)((1-d)*r1 + d*r2), (int)((1-d)*g1 + d*g2), (int)((1-d)*b1 + d*b2));
			 }
		 }
	}
}