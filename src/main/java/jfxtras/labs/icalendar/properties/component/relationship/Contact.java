package jfxtras.labs.icalendar.properties.component.relationship;

import jfxtras.labs.icalendar.components.VEvent;
import jfxtras.labs.icalendar.components.VFreeBusy;
import jfxtras.labs.icalendar.components.VJournal;
import jfxtras.labs.icalendar.components.VTodo;
import jfxtras.labs.icalendar.properties.PropertyBaseAltText;

/**
 * CONTACT
 * RFC 5545 iCalendar 3.8.4.2. page 109
 * 
 * This property is used to represent contact information or
 * alternately a reference to contact information associated with the
 * calendar component.
 * 
 * Example:
 * CONTACT:Jim Dolittle\, ABC Industries\, +1-919-555-1234
 * 
 * @author David Bal
 * @see VEvent
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 */
public class Contact extends PropertyBaseAltText<Contact, String>
{   
    public Contact(CharSequence contentLine)
    {
        // null as argument for string converter causes default converter from ValueType to be used
        super(contentLine, null);
    }
    
    public Contact(Contact source)
    {
        super(source);
    }
}
