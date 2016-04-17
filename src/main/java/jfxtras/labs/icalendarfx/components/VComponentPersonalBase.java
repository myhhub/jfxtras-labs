package jfxtras.labs.icalendarfx.components;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Arrays;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.labs.icalendarfx.properties.PropertyEnum;
import jfxtras.labs.icalendarfx.properties.component.change.DateTimeStamp;
import jfxtras.labs.icalendarfx.properties.component.misc.RequestStatus;
import jfxtras.labs.icalendarfx.properties.component.relationship.Attendee;
import jfxtras.labs.icalendarfx.properties.component.relationship.Organizer;
import jfxtras.labs.icalendarfx.properties.component.relationship.UniformResourceLocator;
import jfxtras.labs.icalendarfx.properties.component.relationship.UniqueIdentifier;

/**
 * Components with the following properties:
 * ATTENDEE, DTSTAMP, ORGANIZER, REQUEST-STATUS, UID, URL
 * 
 * @author David Bal
 *
 * @param <T> - implementation subclass
 * @see VEventNew
 * @see VTodo
 * @see VJournal
 * @see VFreeBusy
 */
public abstract class VComponentPersonalBase<T> extends VComponentPrimaryBase<T> implements VComponentPersonal
{
    /**
     * ATTENDEE: Attendee
     * RFC 5545 iCalendar 3.8.4.1 page 107
     * This property defines an "Attendee" within a calendar component.
     * 
     * Examples:
     * ATTENDEE;MEMBER="mailto:DEV-GROUP@example.com":
     *  mailto:joecool@example.com
     * ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=ACCEPTED;CN=Jane Doe
     *  :mailto:jdoe@example.com
     */
    @Override
    public ObservableList<Attendee> getAttendees()
    {
        if (attendees == null)
        {
            attendees = FXCollections.observableArrayList();
        }
        return attendees;
    }
    private ObservableList<Attendee> attendees;
    @Override
    public void setAttendees(ObservableList<Attendee> attendees) { this.attendees = attendees; }
    /** add comma separated attendees into separate Attendee objects */
    public T withAttendees(String...attendees)
    {
        Arrays.stream(attendees).forEach(c -> getAttendees().add(new Attendee(c)));
        return (T) this;
    }
    
    /**
     * DTSTAMP: Date-Time Stamp, from RFC 5545 iCalendar 3.8.7.2 page 137
     * This property specifies the date and time that the instance of the
     * iCalendar object was created
     */
    @Override
    public ObjectProperty<DateTimeStamp> dateTimeStampProperty()
    {
        if (dateTimeStamp == null)
        {
            dateTimeStamp = new SimpleObjectProperty<>(this, PropertyEnum.DATE_TIME_STAMP.toString());
        }
        return dateTimeStamp;
    }
    private ObjectProperty<DateTimeStamp> dateTimeStamp;
    @Override
    public DateTimeStamp getDateTimeStamp() { return dateTimeStampProperty().get(); }
    @Override
    public void setDateTimeStamp(DateTimeStamp dtStamp) { dateTimeStampProperty().set(dtStamp); }
    public T withDateTimeStamp(ZonedDateTime zonedDateTime) { setDateTimeStamp(new DateTimeStamp(zonedDateTime)); return (T) this; }
    public T withDateTimeStamp(String zonedDateTime) { setDateTimeStamp(new DateTimeStamp(zonedDateTime)); return (T) this; }
    public T withDateTimeStamp(DateTimeStamp dtStamp) { setDateTimeStamp(dtStamp); return (T) this; }

    /**
     * ORGANIZER: Organizer
     * RFC 5545 iCalendar 3.8.4.3 page 111
     * This property defines the organizer for a calendar component
     * 
     * Example:
     * ORGANIZER;CN=John Smith:mailto:jsmith@example.com
     */
    @Override
    public ObjectProperty<Organizer> organizerProperty()
    {
        if (organizer == null)
        {
            organizer = new SimpleObjectProperty<Organizer>(this, PropertyEnum.ORGANIZER.toString());
        }
        return organizer;
    }
    private ObjectProperty<Organizer> organizer;
    @Override
    public Organizer getOrganizer() { return (organizer == null) ? null : organizer.get(); }
    @Override
    public void setOrganizer(Organizer organizer) { organizerProperty().set(organizer); }
    public T withOrganizer(String content) { setOrganizer(new Organizer(content)); return (T) this; }
    public T withOrganizer(Organizer organizer) { setOrganizer(organizer); return (T) this; }

    /**
     * REQUEST-STATUS: Request Status
     * RFC 5545 iCalendar 3.8.8.3 page 141
     * This property defines the status code returned for a scheduling request.
     * 
     * Examples:
     * REQUEST-STATUS:2.0;Success
     * REQUEST-STATUS:3.7;Invalid calendar user;ATTENDEE:
     *  mailto:jsmith@example.com
     * 
     */
    @Override
    public ObservableList<RequestStatus> getRequestStatus()
    {
        return requestStatus;
    }
    private ObservableList<RequestStatus> requestStatus;
    @Override
    public void setRequestStatus(ObservableList<RequestStatus> requestStatus) { this.requestStatus = requestStatus; }
    /** add comma separated requestStatus into separate comment objects */
    public T withRequestStatus(String...requestStatus)
    {
        if (this.requestStatus == null)
        {
            this.requestStatus = FXCollections.observableArrayList();
        }
        Arrays.stream(requestStatus).forEach(c -> getRequestStatus().add(new RequestStatus(c)));
        return (T) this;
    }

    /**
     * UID, Unique identifier
     * RFC 5545, iCalendar 3.8.4.7 page 117
     * A globally unique identifier for the calendar component.
     * required property
     * 
     * Example:
     * UID:19960401T080045Z-4000F192713-0052@example.com
     */
    @Override public ObjectProperty<UniqueIdentifier> uniqueIdentifierProperty()
    {
        if (uniqueIdentifier == null)
        {
            uniqueIdentifier = new SimpleObjectProperty<>(this, PropertyEnum.UNIQUE_IDENTIFIER.toString());
        }
        return uniqueIdentifier;
    }
    private ObjectProperty<UniqueIdentifier> uniqueIdentifier;
    @Override
    public UniqueIdentifier getUniqueIdentifier() { return uniqueIdentifierProperty().get(); }
    @Override
    public void setUniqueIdentifier(UniqueIdentifier uniqueIdentifier) { uniqueIdentifierProperty().set(uniqueIdentifier); }
    public T withUniqueIdentifier(String uniqueIdentifier) { setUniqueIdentifier(new UniqueIdentifier(uniqueIdentifier)); return (T) this; }
    public T withUniqueIdentifier(UniqueIdentifier uniqueIdentifier) { setUniqueIdentifier(uniqueIdentifier); return (T) this; }

    /**
     * URL: Uniform Resource Locator
     * RFC 5545 iCalendar 3.8.4.6 page 116
     * This property defines a Uniform Resource Locator (URL)
     * associated with the iCalendar object
     * 
     * Example:
     * URL:http://example.com/pub/calendars/jsmith/mytime.ics
     */
    @Override
    public ObjectProperty<UniformResourceLocator> uniformResourceLocatorProperty()
    {
        if (uniformResourceLocator == null)
        {
            uniformResourceLocator = new SimpleObjectProperty<>(this, PropertyEnum.UNIFORM_RESOURCE_LOCATOR.toString());
        }
        return uniformResourceLocator;
    }
    private ObjectProperty<UniformResourceLocator> uniformResourceLocator;
    @Override
    public UniformResourceLocator getUniformResourceLocator() { return (uniformResourceLocator == null) ? null : uniformResourceLocatorProperty().get(); }
    @Override
    public void setUniformResourceLocator(UniformResourceLocator url) { uniformResourceLocatorProperty().set(url); };
    public T withUniformResourceLocator(String url) { setUniformResourceLocator(new UniformResourceLocator(url)); return (T) this; }
    public T withUniformResourceLocator(URI uri) { setUniformResourceLocator(new UniformResourceLocator(uri)); return (T) this; }
    public T withUniformResourceLocator(UniformResourceLocator url) { setUniformResourceLocator(url); return (T) this; }


    /*
     * CONSTRUCTORS
     */
    VComponentPersonalBase() { }
    
    VComponentPersonalBase(String contentLines)
    {
        super(contentLines);
    }
}
