package mayer.arne.ctvisualization.visualization;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import mayer.arne.ctvisualization.graph.CTGraph;
import mayer.arne.ctvisualization.query.CTGraphCreator;
import mayer.arne.ctvisualization.query.HanaConnector;

// Represents the frame that displays the visualization and the graphical user interface.
@SuppressWarnings("serial")
public class VisualizationFrame extends JFrame
{
	private int visualizationWidth;
	private int visualizationHeight;
	
	private CTGraph ctGraph;
	private CTLayoutOption currentLayoutOption = CTLayoutOption.FRLayout;
	private CTVisualizationPanel visualizationPanel;
	private Map<CTLayoutOption, CTVisualizationLayoutCreator> layoutMap = new HashMap<CTLayoutOption, CTVisualizationLayoutCreator>();
	
	private JMenu mnLayout;
	private ButtonGroup layoutButtonGroup;
	
	//private Date startDate;
	//private Date endDate;
	
	public static String title = "Contact Tracing Visualization";
	
	public VisualizationFrame()
	{
		final VisualizationFrame thisFrame = this;
		
		// Gets the screen size
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		//int screenWidth = gd.getDisplayMode().getWidth();
		int screenHeight = gd.getDisplayMode().getHeight();
		
		// Determines the size of the frame and the visualization
		int frameHeight = (int) (screenHeight / 1.5);
		int frameWidth = (int)(frameHeight * 4.0 / 3.0);//(int) (screenWidth / 1.5);
		this.visualizationWidth = frameWidth - 30;
		this.visualizationHeight = frameHeight - 75;
		
		this.setTitle(title);
		this.setLocation(200, 100);
		this.setSize(frameWidth, frameHeight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Tests the connection
		this.testDatabaseConnection();
		
		// Creates the visualization
		this.ctGraph = new CTGraphCreator(/*this.startDate, this.endDate*/).getGraph();		
		this.visualizationPanel = new CTVisualizationPanel(visualizationWidth, visualizationHeight, this.getCurrentLayout());
		this.getContentPane().add(this.visualizationPanel);
		
		// Create the menu bar 
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Action");
		menuBar.add(mnFile);
		
		this.mnLayout = new JMenu("Layout");
		menuBar.add(mnLayout);
		this.layoutButtonGroup = new ButtonGroup();
		
		JMenu mnMode = new JMenu("Mode");
		menuBar.add(mnMode);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		// Create the 'File' menu items
		
		/*
		// Creates the 'set time span' button that allows the user to limit the time span of the data
		JMenuItem mntmSetTimeSpan = new JMenuItem("Set time span...");
		mnFile.add(mntmSetTimeSpan);
		mntmSetTimeSpan.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0){
				
				TimeSpanDialog timeSpanDialog = new TimeSpanDialog(thisFrame);
				if(!timeSpanDialog.wasCanceled)
				{
					// Read the entered dates
					thisFrame.startDate = timeSpanDialog.getStartDate();
					thisFrame.endDate = timeSpanDialog.getEndDate();
					thisFrame.refreshVisualization();
				}
			}
		});
		*/
		
		// Creates the refresh button that resets the data and visualization
		JMenuItem mntmRefresh = new JMenuItem("Refresh");
		mnFile.add(mntmRefresh);
		mntmRefresh.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0){
				thisFrame.refreshVisualization();
			}
		});
		
		// Creates the close button that terminates the application
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		mntmClose.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0){thisFrame.dispose();}
		});
		
		// Creates the 'Layout' menu items
		this.addLayoutButton(CTLayoutOption.FRLayout, "FR Layout", true);
		this.addLayoutButton(CTLayoutOption.FRLayout2, "FR Layout 2");
		this.addLayoutButton(CTLayoutOption.CircleLayout, "Circle Layout");
		this.addLayoutButton(CTLayoutOption.SpringLayout, "Spring Layout");
		this.addLayoutButton(CTLayoutOption.KKLayout, "KK Layout");
		this.addLayoutButton(CTLayoutOption.ISOMLayout, "ISOM Layout");
		//this.addLayoutButton(CTLayoutOption.BalloonLayout, "Balloon Layout");
		//this.addLayoutButton(CTLayoutOption.DAGLayout, "DAG Layout");
		//this.addLayoutButton(CTLayoutOption.StaticLayout, "Static Layout");
		
		
		// Creates the 'Mode' menu items
		
		// Creates the move mode button that makes the visualization movable and resizable
		JRadioButtonMenuItem moveModeButton = new JRadioButtonMenuItem("Move");
		moveModeButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0)
			{
				thisFrame.visualizationPanel.enableMoveMode();
			}
		});
		moveModeButton.setSelected(true);
		thisFrame.visualizationPanel.enableMoveMode();
		mnMode.add(moveModeButton);
		
		// Creates the pick mode button that makes single nodes selectable and movable
		JRadioButtonMenuItem pickModeButton = new JRadioButtonMenuItem("Pick");
		pickModeButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0)
			{
				thisFrame.visualizationPanel.enablePickMode();
			}
		});
		mnMode.add(pickModeButton);
		
		ButtonGroup modeButtonGroup = new ButtonGroup();
		modeButtonGroup.add(moveModeButton);
		modeButtonGroup.add(pickModeButton);
		
		// Creates the 'View' menu buttons
		
		// Creates the 'show names button' which can be used to toggle the display of person names
		JCheckBoxMenuItem showNamesButton = new JCheckBoxMenuItem("Show names");
		showNamesButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0)
			{
				thisFrame.visualizationPanel.setShowNames(showNamesButton.isSelected());
			}
		});
		mnView.add(showNamesButton);
		showNamesButton.setSelected(true);
		
		// Displays the frame
		this.setVisible(true);
			
	}
	
	// Creates a new layout button that can be specified to be initially selected.
	private void addLayoutButton(CTLayoutOption layoutOption, String label, boolean isSelected)
	{
		final VisualizationFrame thisFrame = this;
		JRadioButtonMenuItem layoutButton = new JRadioButtonMenuItem(label);
		layoutButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0)
			{
				thisFrame.switchToLayout(layoutOption);
			}
		});
		this.mnLayout.add(layoutButton);
		this.layoutButtonGroup.add(layoutButton);
		layoutButton.setSelected(isSelected);
	}
	
	// Creates a new layout button.
	private void addLayoutButton(CTLayoutOption layoutOption, String label)
	{
		this.addLayoutButton(layoutOption, label, false);
	}
	
	// Switches to a new graph layout.
	private void switchToLayout(CTLayoutOption newLayoutOption)
	{
		if(this.currentLayoutOption == newLayoutOption)
			return;
			
		this.currentLayoutOption = newLayoutOption;
		this.visualizationPanel.animateToLayout(this.getCurrentLayout());
	}
	
	// Gets the graph layout that is currently in use.
	private CTVisualizationLayoutCreator getCurrentLayout()
	{
		CTVisualizationLayoutCreator layout = layoutMap.get(this.currentLayoutOption);
		if(layout == null)
		{
			int w;
			int h;
			
			if(this.visualizationPanel == null)
			{
				w = this.visualizationWidth;
				h = this.visualizationHeight;
			}
			else
			{
				w = this.visualizationPanel.getWidth();
				h = this.visualizationPanel.getHeight();
			}
			
			layout = new CTVisualizationLayoutCreator(w, h, this.currentLayoutOption, this.ctGraph);
			this.layoutMap.put(this.currentLayoutOption, layout);
		}
		
		return layout;
	}
	
	// Refreshes the data, the graph, the layout and the visualization.
	private void refreshVisualization()
	{
		this.layoutMap.clear();
		this.ctGraph = new CTGraphCreator(/*this.startDate, this.endDate*/).getGraph();
		this.visualizationPanel.animateToLayout(this.getCurrentLayout());
	}
	
	// Tests whether a connection to the hana database can be established
	private void testDatabaseConnection()
	{
		if(!HanaConnector.getAthenConnection().isConnected())
		{
			JOptionPane.showMessageDialog(this, "A connection to the database could not be established.", "Connection Error", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}
	}
	
	// This is the entry point of the application.
	public static void main(String[] args)
	{
		// Creates a new visualization frame that shows the visualization
		@SuppressWarnings("unused")
		VisualizationFrame frame = new VisualizationFrame();
	}
}