package mayer.arne.ctvisualization.visualization;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import mayer.arne.ctvisualization.util.DateConverter;

// Represents the set time dialog where the user can restrict the data to a specific time span.
@SuppressWarnings("serial")
public class TimeSpanDialog extends JDialog
{
	public boolean wasCanceled = true;
	public UtilDateModel startModel;
	public UtilDateModel endModel;
	
	public TimeSpanDialog(JFrame parent)
	{
		super(parent);	
		
		// This dialog needs to be closed before the parent can be used again
		this.setTitle("Time Constraint");
		this.setModal(true);
		this.setSize(400, 200);
		this.setResizable(false);
		
		// Places the dialog in the center of the parent frame
		this.setLocation(parent.getLocation().x + parent.getWidth() / 2 - this.getWidth() / 2, 
				parent.getLocation().y + parent.getHeight() / 2 - this.getHeight() / 2);
		
		// Results in absolute positioning
		this.setLayout(null);
		
		TimeSpanDialog thisDialog = this;
		
		this.startModel = new UtilDateModel();
		this.endModel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JLabel startDateLabel = new JLabel("Start date: ");
		startDateLabel.setBounds(10, 10, 80, 20);
		this.add(startDateLabel);
		JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, p);
		JDatePickerImpl startDatePicker = new JDatePickerImpl(startDatePanel, new DateConverter());
		startDatePanel.setSize(100, 20);
		startDatePicker.setBounds(80, 10, 130, 30);
		this.add(startDatePicker);
		
		JLabel endDateLabel = new JLabel("End date: ");
		endDateLabel.setBounds(10, 50, 80, 20);
		this.add(endDateLabel);
		JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p);
		JDatePickerImpl endDatePicker = new JDatePickerImpl(endDatePanel, new DateConverter());
		endDatePanel.setSize(100, 20);
		endDatePicker.setBounds(80, 50, 130, 30);
		this.add(endDatePicker);
		
		// Creates the ok button
		JButton okButton = new JButton("Ok");
		okButton.setBounds(230, 140, 60, 20);
		okButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0)
			{
				thisDialog.wasCanceled = false;
				thisDialog.dispose();
			}
		});
		this.add(okButton);
		
		// Creates the cancel button
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(300, 140, 80, 20);
		cancelButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0)
			{
				thisDialog.wasCanceled = true;
				thisDialog.dispose();
			}
		});
		this.add(cancelButton);
		
		this.setVisible(true);
	}
	
	// Gets the entered start date.
	public java.sql.Date getStartDate()
	{
		if(this.startModel.getValue() == null)
			return null;
		
		return new java.sql.Date(this.startModel.getValue().getTime());
	}
	
	// Gets the entered end date.
	public java.sql.Date getEndDate()
	{
		if(this.endModel.getValue() == null)
			return null;
		
		return new java.sql.Date(this.endModel.getValue().getTime());
	}
}

























