package jfxtras.labs.icalendarfx.components;

import java.time.temporal.TemporalAmount;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.util.Pair;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Location;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Resources;

/**
 * Calendar component with location, among other properties
 * 
 * @author David Bal
 *
 * @param <I> class of recurrence instance
 * @see VEvent
 * @see VTodoOld
 */
public interface VComponentLocatable<I> extends VComponentDisplayableNew<I>
{
    /** 
     * DURATION
     * RFC 5545 iCalendar 3.8.2.5 page 99, 3.3.6 page 34
     * Can't be used if DTEND is used.  Must be one or the other.
     * 
     * Example:
     * DURATION:PT15M
     * */
    ObjectProperty<TemporalAmount> durationProperty();
    TemporalAmount getDuration();
    void setDuration(TemporalAmount duration);
    
    /**
     * GEO: Geographic Position
     * RFC 5545 iCalendar 3.8.1.6 page 85, 3.3.6 page 85
     * This property specifies information related to the global
     * position for the activity specified by a calendar component.
     * 
     * This property value specifies latitude and longitude,
     * in that order (i.e., "LAT LON" ordering).
     * 
     * Example:
     * GEO:37.386013;-122.082932
     */
    Pair<Double,Double> getGeographicPosition();
    ObjectProperty<Pair<Double,Double>> geographicPositionProperty();
    void setGeographicPosition(ObjectProperty<Pair<Double,Double>> geo);
    
    /**
     * LOCATION:
     * RFC 5545 iCalendar 3.8.1.12. page 87
     * This property defines the intended venue for the activity
     * defined by a calendar component.
     * Example:
     * LOCATION:Conference Room - F123\, Bldg. 002
     */
    Location getLocation();
    ObjectProperty<Location> locationProperty();
    void setLocation(Location location);
    
    /**
     * PRIORITY
     * RFC 5545 iCalendar 3.8.1.6 page 85, 3.3.6 page 85
     * This property defines the relative priority for a calendar component.
     * This priority is specified as an integer in the range 0 to 9.
     * 
     * Example: The following is an example of a property with the highest priority:
     * PRIORITY:1
     */
    int getPriority();
    IntegerProperty priorityProperty();
    void setPriority(int priority);
    
    /**
     * RESOURCES:
     * RFC 5545 iCalendar 3.8.1.10. page 91
     * This property defines the equipment or resources
     * anticipated for an activity specified by a calendar component.
     * More than one resource can be specified as a COMMA-separated list
     * Example:
     * RESOURCES:EASEL,PROJECTOR,VCR
     * RESOURCES;LANGUAGE=fr:Nettoyeur haute pression
     */
    Resources getResources();
    ObjectProperty<Resources> resourcesProperty();
    void setResources(Resources resources);
}