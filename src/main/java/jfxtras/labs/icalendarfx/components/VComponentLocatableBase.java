package jfxtras.labs.icalendarfx.components;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.labs.icalendarfx.VParent;
import jfxtras.labs.icalendarfx.properties.PropertyType;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Description;
import jfxtras.labs.icalendarfx.properties.component.descriptive.GeographicPosition;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Location;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Priority;
import jfxtras.labs.icalendarfx.properties.component.descriptive.Resources;
import jfxtras.labs.icalendarfx.properties.component.time.DurationProp;

public abstract class VComponentLocatableBase<T> extends VComponentDisplayableBase<T> implements VComponentDescribable2<T>, VComponentDuration<T>
{
    /**
     * DESCRIPTION
     * RFC 5545 iCalendar 3.8.1.5. page 84
     * 
     * This property provides a more complete description of the
     * calendar component than that provided by the "SUMMARY" property.
     * 
     * Example:
     * DESCRIPTION:Meeting to provide technical review for "Phoenix"
     *  design.\nHappy Face Conference Room. Phoenix design team
     *  MUST attend this meeting.\nRSVP to team leader.
     *
     * Note: Only VJournal allows multiple instances of DESCRIPTION
     */
    @Override
    public ObjectProperty<Description> descriptionProperty()
    {
        if (description == null)
        {
            description = new SimpleObjectProperty<>(this, PropertyType.DESCRIPTION.toString());
            orderer().registerSortOrderProperty(description);
        }
        return description;
    }
    @Override
    public Description getDescription() { return (description == null) ? null : descriptionProperty().get(); }
    private ObjectProperty<Description> description;

    /** 
     * DURATION
     * RFC 5545 iCalendar 3.8.2.5 page 99, 3.3.6 page 34
     * Can't be used if DTEND is used.  Must be one or the other.
     * 
     * Example:
     * DURATION:PT15M
     * */
    @Override
    public ObjectProperty<DurationProp> durationProperty()
    {
        if (duration == null)
        {
            duration = new SimpleObjectProperty<>(this, PropertyType.DURATION.toString());
            orderer().registerSortOrderProperty(duration);
        }
        return duration;
    }
    private ObjectProperty<DurationProp> duration;
    
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
    public ObjectProperty<GeographicPosition> geographicPositionProperty()
    {
        if (geographicPosition == null)
        {
            geographicPosition = new SimpleObjectProperty<>(this, PropertyType.GEOGRAPHIC_POSITION.toString());
            orderer().registerSortOrderProperty(geographicPosition);
        }
        return geographicPosition;
    }
    private ObjectProperty<GeographicPosition> geographicPosition;
    public GeographicPosition getGeographicPosition() { return geographicPositionProperty().get(); }
    public void setGeographicPosition(GeographicPosition geographicPosition) { geographicPositionProperty().set(geographicPosition); }
    public void setGeographicPosition(String geographicPosition)
    {
        if (getGeographicPosition() == null)
        {
            setGeographicPosition(GeographicPosition.parse(geographicPosition));
        } else
        {
            getGeographicPosition().setValue(geographicPosition);
        }
    }
    public void setGeographicPosition(double latitude, double longitude)
    {
        if (getGeographicPosition() == null)
        {
            setGeographicPosition(new GeographicPosition(latitude, longitude));
        } else
        {
            getGeographicPosition().setLatitude(latitude);
            getGeographicPosition().setLongitude(longitude);
        }
    }
    public T withGeographicPosition(GeographicPosition geographicPosition)
    {
        if (getGeographicPosition() == null)
        {
            setGeographicPosition(geographicPosition);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public T withGeographicPosition(String geographicPosition)
    {
        if (getGeographicPosition() == null)
        {
            setGeographicPosition(geographicPosition);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public T withGeographicPosition(double latitude, double longitude)
    {
        if (getGeographicPosition() == null)
        {
            setGeographicPosition(latitude, longitude);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }

    /**
     * LOCATION:
     * RFC 5545 iCalendar 3.8.1.12. page 87
     * This property defines the intended venue for the activity
     * defined by a calendar component.
     * Example:
     * LOCATION:Conference Room - F123\, Bldg. 002
     */
    public ObjectProperty<Location> locationProperty()
    {
        if (location == null)
        {
            location = new SimpleObjectProperty<>(this, PropertyType.LOCATION.toString());
            orderer().registerSortOrderProperty(location);
        }
        return location;
    }
    private ObjectProperty<Location> location;
    public Location getLocation() { return locationProperty().get(); }
    public void setLocation(Location location) { locationProperty().set(location); }
    public void setLocation(String location)
    {
        if (getLocation() == null)
        {
            setLocation(Location.parse(location));
        } else
        {
            Location temp = Location.parse(location);
            getLocation().setValue(temp.getValue());
        }
    }
    public T withLocation(Location location)
    {
        if (getLocation() == null)
        {
            setLocation(location);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public T withLocation(String location)
    {
        if (getLocation() == null)
        {
            if (location != null)
            {
                setLocation(location);
            }
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }

    /**
     * PRIORITY
     * RFC 5545 iCalendar 3.8.1.6 page 85, 3.3.6 page 85
     * This property defines the relative priority for a calendar component.
     * This priority is specified as an integer in the range 0 to 9.
     * 
     * Example: The following is an example of a property with the highest priority:
     * PRIORITY:1
     */
    public ObjectProperty<Priority> priorityProperty()
    {
        if (priority == null)
        {
            priority = new SimpleObjectProperty<>(this, PropertyType.PRIORITY.toString());
            orderer().registerSortOrderProperty(priority);
        }
        return priority;
    }
    private ObjectProperty<Priority> priority;
    public Priority getPriority() { return priorityProperty().get(); }
    public void setPriority(Priority priority) { priorityProperty().set(priority); }
    public void setPriority(String priority)
    {
        if (getPriority() == null)
        {
            setPriority(Priority.parse(priority));
        } else
        {
            Priority temp = Priority.parse(priority);
            getPriority().setValue(temp.getValue());
        }
    }
    public void setPriority(int priority)
    {
        if (getPriority() == null)
        {
            setPriority(new Priority(priority));
        } else
        {
            getPriority().setValue(priority);
        }
    }
    public T withPriority(Priority priority)
    {
        if (getPriority() == null)
        {
            setPriority(priority);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public T withPriority(String priority)
    {
        if (getPriority() == null)
        {
            setPriority(priority);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    public T withPriority(int priority)
    {
        if (getPriority() == null)
        {
            setPriority(priority);
            return (T) this;
        } else
        {
            throw new IllegalArgumentException("Property can only occur once in the calendar component");
        }
    }
    
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
    public ObservableList<Resources> getResources() { return resources; }
    private ObservableList<Resources> resources;
    public void setResources(ObservableList<Resources> resources)
    {
        if (resources != null)
        {
            orderer().registerSortOrderProperty(resources);
        } else
        {
            orderer().unregisterSortOrderProperty(this.resources);
        }
        this.resources = resources;
    }
    public T withResources(ObservableList<Resources> resources)
    {
        setResources(resources);
        return (T) this;
    }
    public T withResources(String...resources)
    {
        Arrays.stream(resources).forEach(c -> PropertyType.RESOURCES.parse(this, c));
        return (T) this;
    }
    public T withResources(Resources...resources)
    {
        if (getResources() == null)
        {
            setResources(FXCollections.observableArrayList(resources));
        } else
        {
            getResources().addAll(resources);
        }
        return (T) this;
    }

    /** 
     * VALARM
     * Alarm Component
     * RFC 5545 iCalendar 3.6.6. page 71
     * 
     * Provide a grouping of component properties that define an alarm.
     * 
     * The "VALARM" calendar component MUST only appear within either a
     * "VEVENT" or "VTODO" calendar component.
     */
    public ObservableList<VAlarm> getVAlarms() { return vAlarms; }
    private ObservableList<VAlarm> vAlarms;
    public void setVAlarms(ObservableList<VAlarm> vAlarms)
    {
        if (vAlarms != null)
        {
            orderer().registerSortOrderProperty(vAlarms);
        } else
        {
            orderer().unregisterSortOrderProperty(this.vAlarms);
        }
        this.vAlarms = vAlarms;
    }
    public T withVAlarms(ObservableList<VAlarm> vAlarms) { setVAlarms(vAlarms); return (T) this; }
    public T withVAlarms(VAlarm...vAlarms)
    {
        if (getVAlarms() == null)
        {
            setVAlarms(FXCollections.observableArrayList(vAlarms));
        } else
        {
            getVAlarms().addAll(vAlarms);
        }
        return (T) this;
    }
    
    static void copyVAlarms(VComponentLocatableBase<?> source, VComponentLocatableBase<?> destination)
    {
        VAlarm[] collect = source.getVAlarms()
                .stream()
                .map(c -> new VAlarm(c))
                .toArray(size -> new VAlarm[size]);
        ObservableList<VAlarm> properties = FXCollections.observableArrayList(collect);
        destination.setVAlarms(properties);
    }
    
    /*
     * CONSTRUCTORS
     */
    public VComponentLocatableBase() { super(); }
    
    public VComponentLocatableBase(VComponentLocatableBase<T> source)
    {
        super(source);
    }

    @Override
    void addSubcomponent(VComponent subcomponent)
    {
        if (subcomponent instanceof VAlarm)
        {
            final ObservableList<VAlarm> list;
            if (getVAlarms() == null)
            {
                list = FXCollections.observableArrayList();
                setVAlarms(list);
            } else
            {
                list = getVAlarms();
            }
            list.add((VAlarm) subcomponent);
        } else
        {
            throw new IllegalArgumentException("Unspoorted subcomponent type:" + subcomponent.getClass().getSimpleName() +
                    " found inside " + componentName() + " component");
        }        
    }
    
    /** copy VAlarms */
    @Override
    public void copyChildrenFrom(VParent source)
    {
        super.copyChildrenFrom(source);
        VComponentLocatableBase<?> castSource = (VComponentLocatableBase<?>) source;
        if (castSource.getVAlarms() != null)
        {
            if (getVAlarms() == null)
            {
                setVAlarms(FXCollections.observableArrayList());
            }
            castSource.getVAlarms().forEach(a -> this.getVAlarms().add(new VAlarm(a)));            
        }
    }
    
    @Override
    public List<String> errors()
    {
        List<String> errors = super.errors();
        if (getVAlarms() != null)
        {
            getVAlarms().forEach(v -> errors.addAll(v.errors()));
        }
        return errors;
    }
    
    @Override // include VAlarms
    public boolean equals(Object obj)
    {
        VComponentLocatableBase<?> testObj = (VComponentLocatableBase<?>) obj;
        final boolean isVAlarmsEqual;
        if (getVAlarms() != null)
        {
            if (testObj.getVAlarms() == null)
            {
                isVAlarmsEqual = false;
            } else
            {
                isVAlarmsEqual = getVAlarms().equals(testObj.getVAlarms());
            }
        } else
        {
            isVAlarmsEqual = true;
        }
        return isVAlarmsEqual && super.equals(obj);
    }
    
    @Override // include VAlarms
    public int hashCode()
    {
        int hash = super.hashCode();
        if (getVAlarms() != null)
        {
            Iterator<VAlarm> i = getVAlarms().iterator();
            while (i.hasNext())
            {
                Object property = i.next();
                hash = (31 * hash) + property.hashCode();
            }
        }
        return hash;
    }
    
    /** Stream recurrence dates with adjustment to include recurrences that don't end before start parameter */
    @Override
    public Stream<Temporal> streamRecurrences(Temporal start)
    {
        final TemporalAmount adjustment = getActualDuration();
        return super.streamRecurrences(start.minus(adjustment));
    }
    
    /** A convenience method that returns either Duration property value, or a calculated duration based on start and end values */
    public abstract TemporalAmount getActualDuration();
    
    /**
     * A convenience method that sets DTEND, DURATION (VEvent) or DUE (VTodo) depending on which ever is already set
     * to new value calculated by the duration or period between input parameters (depending on if the parameters
     * are LocalDate or a date/time type (i.e. ZonedDateTime))
     *  
     * Note: In order to set DTEND, DTSTART must be assigned a value.  DURATION and DUE doesn't require a DTSTART value.
     *  
     * @param startRecurrence
     * @param endRecurrence
     */
    public abstract void setEndOrDuration(Temporal startRecurrence, Temporal endRecurrence);
}
