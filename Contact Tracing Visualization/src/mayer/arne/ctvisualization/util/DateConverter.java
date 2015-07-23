package mayer.arne.ctvisualization.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

// Represents a converter that turns date into strings and vice versa.
@SuppressWarnings("serial")
public class DateConverter extends AbstractFormatter {

    private String pattern = "yyyy-MM-dd";
    private SimpleDateFormat formatter = new SimpleDateFormat(pattern);

    // Turns a date string into a date object.
    @Override public Object stringToValue(String text) throws ParseException
    {
        return formatter.parseObject(text);
    }
    
    // Turns a date into a string.
    @Override public String valueToString(Object value) throws ParseException
    {
        if (value != null)
        {
            return formatter.format(((Calendar) value).getTime());
        }
        else
        {
        	return "";
        }
    }

}