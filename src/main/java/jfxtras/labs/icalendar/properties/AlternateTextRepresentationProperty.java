package jfxtras.labs.icalendar.properties;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jfxtras.labs.icalendar.parameters.AlternateTextRepresentation;
import jfxtras.labs.icalendar.parameters.ParameterEnum;
import jfxtras.labs.icalendar.properties.component.descriptive.Comment;
import jfxtras.labs.icalendar.properties.component.descriptive.Description;
import jfxtras.labs.icalendar.properties.component.descriptive.Location;
import jfxtras.labs.icalendar.properties.component.descriptive.Resources;
import jfxtras.labs.icalendar.properties.component.descriptive.Summary;
import jfxtras.labs.icalendar.properties.component.relationship.Contact;

/**
 * Property with language, alternate text display, and a text-based value
 *  
 * @param <T>
 * 
 * concrete subclasses
 * @see Comment
 * @see Contact
 * @see Description
 * @see Location
 * @see Resources
 * @see Summary
 */
public abstract class AlternateTextRepresentationProperty<T,U> extends LanguageProperty<T,U>
{
    /**
     * ALTREP : Alternate Text Representation
     * To specify an alternate text representation for the property value.
     * 
     * Example:
     * DESCRIPTION;ALTREP="CID:part3.msg.970415T083000@example.com":
     *  Project XYZ Review Meeting will include the following agenda
     *   items: (a) Market Overview\, (b) Finances\, (c) Project Man
     *  agement
     *
     *The "ALTREP" property parameter value might point to a "text/html"
     *content portion.
     *
     * Content-Type:text/html
     * Content-Id:<part3.msg.970415T083000@example.com>
     *
     * <html>
     *   <head>
     *    <title></title>
     *   </head>
     *   <body>
     *     <p>
     *       <b>Project XYZ Review Meeting</b> will include
     *       the following agenda items:
     *       <ol>
     *         <li>Market Overview</li>
     *         <li>Finances</li>
     *         <li>Project Management</li>
     *       </ol>
     *     </p>
     *   </body>
     * </html>
     */
    public AlternateTextRepresentation getAlternateTextRepresentation() { return (alternateTextRepresentation == null) ? null : alternateTextRepresentation.get(); }
    public ObjectProperty<AlternateTextRepresentation> alternateTextRepresentationProperty()
    {
        if (alternateTextRepresentation == null)
        {
            alternateTextRepresentation = new SimpleObjectProperty<>(this, ParameterEnum.ALTERNATE_TEXT_REPRESENTATION.toString());
        }
        return alternateTextRepresentation;
    }
    private ObjectProperty<AlternateTextRepresentation> alternateTextRepresentation;
    public void setAlternateTextRepresentation(AlternateTextRepresentation alternateTextRepresentation)
    {
        if (alternateTextRepresentation != null)
        {
            alternateTextRepresentationProperty().set(alternateTextRepresentation);
        }
    }
    public T withAlternateTextRepresentation(AlternateTextRepresentation altrep) { setAlternateTextRepresentation(altrep); return (T) this; }
    public T withAlternateTextRepresentation(String content) { ParameterEnum.ALTERNATE_TEXT_REPRESENTATION.parse(this, content); return (T) this; }
    
    /*
     * CONSTRUCTORS
     */    
    protected AlternateTextRepresentationProperty(CharSequence propertyString)
    {
        super(propertyString);
//        setValue((U) getPropertyValueString());
    }

    // copy constructor
    public AlternateTextRepresentationProperty(AlternateTextRepresentationProperty<T,U> property)
    {
        super(property);
        if (getAlternateTextRepresentation() != null)
        {
            setAlternateTextRepresentation(property.getAlternateTextRepresentation());
        }
    }

    public AlternateTextRepresentationProperty(U value)
    {
        setValue(value);
    }
    
    public AlternateTextRepresentationProperty() { super(); }
}