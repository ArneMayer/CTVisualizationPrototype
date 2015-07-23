package mayer.arne.ctvisualization.visualization;

import java.awt.Color;

import javax.swing.BorderFactory;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.util.Animator;
import mayer.arne.ctvisualization.graph.CTEdge;
import mayer.arne.ctvisualization.graph.CTNode;

// Represents a creator for the visualization panel.
@SuppressWarnings("serial")
public class CTVisualizationPanel extends VisualizationViewer<CTNode, CTEdge>
{
	private int visualizationWidth;
	private int visualizationHeight;
	private CTVisualizationLayoutCreator layout;
	private DefaultModalGraphMouse<CTNode, CTEdge> graphMouse;
	private boolean showNames;
	
	// Creates a creator with configurable visualization size.
	public CTVisualizationPanel(int width, int height, CTVisualizationLayoutCreator layout)
	{
		super(layout.getLayout());
		
		// Validates the parameters
		if(width <= 0)
			throw new IllegalArgumentException("width is <= 0.");
		if(height <= 0)
			throw new IllegalArgumentException("height is <= 0.");
		
		this.visualizationWidth = width;
		this.visualizationHeight = height;
		this.layout = layout;
		
		this.showNames = true;
		
		// Sets the visual style of the visualization container
		this.setBounds(10, 10, visualizationWidth, visualizationHeight);
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Creates the mouse actions
		this.graphMouse = new DefaultModalGraphMouse<CTNode, CTEdge>();
		this.graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
		this.setGraphMouse(this.graphMouse); 
		
		// Sets the object that is responsible for rendering nodes
		this.getRenderer().setVertexRenderer(new CTNodeRenderer());
		
		// Sets how the nodes will be labeled
		Transformer<CTNode,String> vertexLabel = new Transformer<CTNode,String>() {
			 public String transform(CTNode node) {
				 if(showNames)
					 return node.getLabel();
				 else
					 return "";
			 }
		};
		this.getRenderContext().setVertexLabelTransformer(vertexLabel);
	}
	
	// Switches the visualization to a new layout with an animated transition.
	public void animateToLayout(CTVisualizationLayoutCreator newLayout)
	{
		// Validates the parameters
		if(newLayout == null)
			throw new IllegalArgumentException("newLayout is null");
		
		LayoutTransition<CTNode, CTEdge> lt = new LayoutTransition<CTNode, CTEdge>(this, this.layout.getLayout(), newLayout.getLayout());
		Animator animator = new Animator(lt);
		animator.start();
		//this.getRenderContext().getMultiLayerTransformer().setToIdentity();
	}

	// Enables the pick mode that allows the user to select and drag nodes.
	public void enablePickMode()
	{
		this.graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
	}

	// Enables the move mode that allows to user to move, resize, rotate and stretch the whole visualization.
	public void enableMoveMode()
	{
		this.graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	}

	// Sets whether names should be shown in the visualization
	public void setShowNames(boolean showNames)
	{
		this.showNames = showNames;
		this.repaint(100);
	}
}
