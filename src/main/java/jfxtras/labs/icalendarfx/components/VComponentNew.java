package jfxtras.labs.icalendarfx.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.labs.icalendarfx.properties.Property;
import jfxtras.labs.icalendarfx.properties.PropertyType;
import jfxtras.labs.icalendarfx.properties.component.misc.IANAProperty;
import jfxtras.labs.icalendarfx.properties.component.misc.NonStandardProperty;

/**
 * iCalendar component
 * Contains the following properties:
 * Non-Standard Properties, IANA Properties
 * 
 * @author David Bal
 * @see VComponentBase
 * 
 * @see VComponentPrimary
 * 
 * @see VEventNewInt
 * @see VTodoInt
 * @see VJournalInt
 * @see VFreeBusy
 * @see VTimeZone
 * @see VAlarmInt
 */
public interface VComponentNew<T>
{
    /**
     * Returns the enum for the component as it would appear in the iCalendar content line
     * Examples:
     * VEVENT
     * VJOURNAL
     * 
     * @return - the component enum
     */
    CalendarElement componentType();
    
    /**
     * 3.8.8.2.  Non-Standard Properties
     * Any property name with a "X-" prefix
     * 
     * Example:
     * X-ABC-MMSUBJ;VALUE=URI;FMTTYPE=audio/basic:http://www.example.
     *  org/mysubj.au
     */
    ObservableList<NonStandardProperty> getNonStandardProperties();
    void setNonStandardProperties(ObservableList<NonStandardProperty> properties);
    default T withNonStandardProperty(String...nonStandardProps)
    {
        Arrays.stream(nonStandardProps).forEach(c -> PropertyType.NON_STANDARD.parse(this, c));
        return (T) this;
    }
    default T withNonStandardProperty(ObservableList<NonStandardProperty> nonStandardProps) { setNonStandardProperties(nonStandardProps); return (T) this; }
    default T withNonStandardProperty(NonStandardProperty...nonStandardProps)
    {
        if (getNonStandardProperties() == null)
        {
            setNonStandardProperties(FXCollections.observableArrayList(nonStandardProps));
        } else
        {
            getNonStandardProperties().addAll(nonStandardProps);
        }
        return (T) this;
    }

    /**
     * 3.8.8.1.  IANA Properties
     * An IANA-registered property name
     * 
     * Examples:
     * NON-SMOKING;VALUE=BOOLEAN:TRUE
     * DRESSCODE:CASUAL
     */
    ObservableList<IANAProperty> getIANAProperties();
    void setIANAProperties(ObservableList<IANAProperty> properties);
    default T withIANAProperty(String...ianaProps)
    {
        Arrays.stream(ianaProps).forEach(c -> PropertyType.IANA_PROPERTY.parse(this, c));
        return (T) this;
    }
    default T withIANAProperty(ObservableList<IANAProperty> ianaProps) { setIANAProperties(ianaProps); return (T) this; }
    default T withIANAProperty(IANAProperty...ianaProps)
    {
        if (getIANAProperties() == null)
        {
            setIANAProperties(FXCollections.observableArrayList(ianaProps));
        } else
        {
            getIANAProperties().addAll(ianaProps);
        }
        return (T) this;
    }

    /**
     * List of all properties enums found in component.
     * The list is unmodifiable.
     * 
     * @return - the list of properties enums
     */
    List<PropertyType> propertyEnums();
    /**
     * List of all properties found in component.
     * The list is unmodifiable.
     * 
     * @return - the list of properties
     */

    default List<Property<?>> properties()
    {
        return Collections.unmodifiableList(
                propertyEnums().stream().flatMap(e ->
        {
            Object obj = e.getProperty(this);
            if (obj instanceof Property)
            {
                return Arrays.asList((Property<?>) obj).stream();
            } else if (obj instanceof List)
            {
                return ((List<Property<?>>) obj).stream();
            } else
            {
                throw new RuntimeException("Unsupported property type:" + obj.getClass());
            }
        })
        .collect(Collectors.toList()));
    }
    

    /**
     * Return property content line for iCalendar output files.  See RFC 5545 3.4
     * Contains component properties with their values and any parameters.
     * 
     * The following is a simple example of an iCalendar component:
     *
     *  BEGIN:VEVENT
     *  UID:19970610T172345Z-AF23B2@example.com
     *  DTSTAMP:19970610T172345Z
     *  DTSTART:19970714T170000Z
     *  DTEND:19970715T040000Z
     *  SUMMARY:Bastille Day Party
     *  END:VEVENT
     * 
     * @return - the component content lines
     */
    CharSequence toContent();
    
    /**
     * Checks component to determine if necessary properties are set.
     * 
     * @return - true if component is valid, false otherwise
     */
    @Deprecated // replace with list of problems instead
    boolean isValid();
    
    /** Indicates the calendar component is top-level and can be a member of the VCalendar directly.
     * Components not implementing this interface, such as {@link #VAlarm}, {@link #StandardTime} and {@link #DaylightSavingTime}
     * must be embedded inside other components.
     * 
     * @author David Bal
     *
     */
    @Deprecated // maybe there is a better way to mark main components (as opposed to sub-components)
    public interface VComponentMain
    {
        
    }
}
