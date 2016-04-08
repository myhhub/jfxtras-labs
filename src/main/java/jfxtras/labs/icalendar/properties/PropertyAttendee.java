package jfxtras.labs.icalendar.properties;

import javafx.beans.property.ObjectProperty;
import jfxtras.labs.icalendar.parameters.CalendarUser;
import jfxtras.labs.icalendar.parameters.Delegatees;
import jfxtras.labs.icalendar.parameters.Delegators;
import jfxtras.labs.icalendar.parameters.GroupMembership;
import jfxtras.labs.icalendar.parameters.Participation;
import jfxtras.labs.icalendar.parameters.ParticipationRole;
import jfxtras.labs.icalendar.parameters.RSVP;
import jfxtras.labs.icalendar.properties.component.relationship.PropertyBaseAttendee;

/**
 * Abstract class for Attendee and unknown properties
 * 
 * @author David Bal
 *
 * @param <T> - subclass
 * @param <T> - property value type
 * 
 * @see PropertyBaseAttendee
 */
public interface PropertyAttendee<T> extends PropertyCalendarUser<T>
{
    /**
     * CUTYPE
     * Calendar User Type
     * RFC 5545, 3.2.3, page 16
     * 
     * To identify the type of calendar user specified by the property.
     * 
     * Example:
     * ATTENDEE;CUTYPE=GROUP:mailto:ietf-calsch@example.org
     */
    CalendarUser getCalendarUser();
    ObjectProperty<CalendarUser> calendarUserProperty();
    void setCalendarUser(CalendarUser calendarUser);  

    /**
     * DELEGATED-FROM
     * Delegators
     * RFC 5545, 3.2.4, page 17
     * 
     * To specify the calendar users that have delegated their
     *    participation to the calendar user specified by the property.
     * 
     * Example:
     * ATTENDEE;DELEGATED-FROM="mailto:jsmith@example.com":mailto:
     *  jdoe@example.com
     */
    Delegators getDelegators();
    ObjectProperty<Delegators> delegatorsProperty();
    void setDelegators(Delegators delegators);

    /**
     * DELEGATED-TO
     * Delegatees
     * RFC 5545, 3.2.5, page 17
     * 
     * To specify the calendar users to whom the calendar user
     *    specified by the property has delegated participation.
     * 
     * Example:
     * ATTENDEE;DELEGATED-TO="mailto:jdoe@example.com","mailto:jqpublic
     *  @example.com":mailto:jsmith@example.com
     * 
     */
    Delegatees getDelegatees();
    ObjectProperty<Delegatees> delegateesProperty();
    void setDelegatees(Delegatees delegatees);
    
    /**
     * MEMBER
     * Group or List Membership
     * RFC 5545, 3.2.11, page 21
     * 
     * To specify the group or list membership of the calendar user specified by the property.
     * 
     * Example:
     * ATTENDEE;MEMBER="mailto:projectA@example.com","mailto:pr
     *  ojectB@example.com":mailto:janedoe@example.com
     * 
     */
    GroupMembership getGroupMembership();
    ObjectProperty<GroupMembership> groupMembershipProperty();
    void setGroupMembership(GroupMembership groupMembership);
    
    /**
     * RSVP
     * RSVP Expectation
     * RFC 5545, 3.2.17, page 26
     * 
     * To specify whether there is an expectation of a favor of a reply from the calendar user specified by the property value.
     * 
     * Example:
     * ATTENDEE;RSVP=TRUE:mailto:jsmith@example.com
     */
    RSVP getRSVP();
    ObjectProperty<RSVP> rsvpProperty();
    void setRSVP(RSVP rsvp);
    
    /**
     * PARTSTAT
     * Participation Status
     * RFC 5545, 3.2.12, page 22
     * 
     * To specify the participation role for the calendar user specified by the property.
     * 
     * Example:
     * ATTENDEE;PARTSTAT=DECLINED:mailto:jsmith@example.com
     */
    Participation getParticipation();
    ObjectProperty<Participation> participationProperty();
    void setParticipation(Participation participation); 

    /**
     * ROLE
     * Participation Role
     * RFC 5545, 3.2.16, page 25
     * 
     * To specify the participation role for the calendar user specified by the property.
     * 
     * Example:
     * ATTENDEE;ROLE=CHAIR:mailto:mrbig@example.com
     */
    ParticipationRole getParticipationRole();
    ObjectProperty<ParticipationRole> participationRoleProperty();
    void setParticipationRole(ParticipationRole participationRole);
}
