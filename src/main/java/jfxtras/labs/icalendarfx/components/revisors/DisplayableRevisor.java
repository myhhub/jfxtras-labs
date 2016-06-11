package jfxtras.labs.icalendarfx.components.revisors;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.util.Callback;
import javafx.util.Pair;
import jfxtras.labs.icalendarfx.components.VComponentDisplayable;
import jfxtras.labs.icalendarfx.components.revisors.LocatableRevisor.RRuleStatus;
import jfxtras.labs.icalendarfx.properties.PropertyType;
import jfxtras.labs.icalendarfx.properties.component.recurrence.RecurrenceRule;
import jfxtras.labs.icalendarfx.properties.component.recurrence.rrule.RecurrenceRule2;
import jfxtras.labs.icalendarfx.utilities.DateTimeUtilities;

public abstract class DisplayableRevisor<T, U extends VComponentDisplayable<U>> implements DisplayRevisable<T, U>
{ 
    public DisplayableRevisor(U component)
    {
        setVComponentEdited(component);
    }

    @Override public U getVComponentEdited() { return vComponentEdited; }
    private U vComponentEdited;
    @Override public void setVComponentEdited(U vComponentEdited) { this.vComponentEdited = vComponentEdited; }

    @Override public U getVComponentOriginal() { return vComponentOriginal; }
    private U vComponentOriginal;
    @Override public void setVComponentOriginal(U vComponentOriginal) { this.vComponentOriginal = vComponentOriginal; }

    @Override public Temporal getStartOriginalRecurrence() { return startOriginalRecurrence; }
    private Temporal startOriginalRecurrence;
    @Override public void setStartOriginalRecurrence(Temporal startOriginalRecurrence) { this.startOriginalRecurrence = startOriginalRecurrence; }
    
    @Override public Temporal getStartRecurrence() { return startRecurrence; }
    private Temporal startRecurrence;
    @Override public void setStartRecurrence(Temporal startRecurrence) { this.startRecurrence = startRecurrence; }
    
    @Override public Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> getDialogCallback() { return dialogCallback; }
    private Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback;    
    @Override public void setDialogCallback(Callback<Map<ChangeDialogOption, Pair<Temporal,Temporal>>, ChangeDialogOption> dialogCallback) { this.dialogCallback = dialogCallback; }

    /** Edit VEvent or VTodo or VJournal */
    @Override
    public Collection<U> revise()
    {
        // TODO - CHECK IF FIELDS ARE SET
        
        U vComponentEdited = getVComponentEdited();
        U vComponentOriginal = getVComponentOriginal();
        Temporal startRecurrence = getStartRecurrence();
        Temporal startOriginalRecurrence = getStartOriginalRecurrence();
//        System.out.println("shiftAmout:" + shiftAmount + " " + getVComponentEdited().getDateTimeStart().getValue() + " " + ((VEvent) vComponentEdited).getDateTimeEnd().getValue());
        if (! getVComponentEdited().isValid())
        {
            throw new RuntimeException("Can't revise. Edited component is invalid:" + System.lineSeparator() + 
                    getVComponentEdited().errors().stream().collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() +
                    getVComponentEdited().toContent());
        }
        if (! getVComponentOriginal().isValid())
        {
            throw new RuntimeException("Can't revise. Original component is invalid:" + System.lineSeparator() + 
                    getVComponentEdited().errors().stream().collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() +
                    getVComponentEdited().toContent());
        }
        
        Collection<U> vComponents = new ArrayList<>(); // new components that should be added to main list
        validateStartRecurrenceAndDTStart(startRecurrence);
        final RRuleStatus rruleType = RRuleStatus.getRRuleType(getVComponentOriginal().getRecurrenceRule(), getVComponentEdited().getRecurrenceRule());
        System.out.println("rruleType:" + rruleType);
        boolean incrementSequence = true;
//        Collection<R> newRecurrences = null;
//        Collection<R> allRecurrences = recurrences;
        switch (rruleType)
        {
        case HAD_REPEAT_BECOMING_INDIVIDUAL:
            becomeNonRecurring();
//            getVComponentEdited().becomeNonRecurring(vComponentOriginal, startRecurrence, endRecurrence);
            // fall through
        case WITH_NEW_REPEAT: // no dialog
        case INDIVIDUAL:
            adjustDateTime();
//            getVComponentEdited().adjustDateTime(startOriginalRecurrence, startRecurrence, endRecurrence);
            
//            if (! getVComponentEdited().equals(vComponentOriginal))
//            {
//                newRecurrences = updateRecurrences(vComponentEditedCopy);
//            }
            break;
        case WITH_EXISTING_REPEAT:
            // Find which properties changed
            List<PropertyType> changedProperties = findChangedProperties();
//                    vComponentOriginal,
//                    startOriginalRecurrence,
//                    startRecurrence,
//                    endRecurrence);
            /* Note:
             * time properties must be checked separately because changes are stored in startRecurrence and endRecurrence,
             * not the VComponents DTSTART and DTEND yet.  The changes to DTSTART and DTEND are made after the dialog
             * question is answered. */
//            changedProperties.addAll(changedStartAndEndDateTime(startOriginalRecurrence, startRecurrence, endRecurrence));
            // determine if any changed properties warrant dialog
//            changedProperties.stream().forEach(a -> System.out.println("changed property:" + a));
            boolean provideDialog = changedProperties.stream()
                .map(p -> dialogRequiredProperties().contains(p))
                .anyMatch(b -> b == true);
//            boolean provideDialog = requiresChangeDialog(changedProperties);
            if (changedProperties.size() > 0) // if changes occurred
            {
                List<U> relatedVComponents = Arrays.asList(vComponentEdited); // TODO - support related components
                final ChangeDialogOption changeResponse;
                if (provideDialog)
                {
                    Map<ChangeDialogOption, Pair<Temporal,Temporal>> choices = ChangeDialogOption.makeDialogChoices(
                            vComponentOriginal,
                            vComponentEdited,
                            startOriginalRecurrence,
                            changedProperties);
                    changeResponse = dialogCallback.call(choices);
                    System.out.println("changeResponse:" + changeResponse);
                } else
                {
                    changeResponse = ChangeDialogOption.ALL;
                }
                switch (changeResponse)
                {
                case ALL:
                    System.out.println("components size:" + relatedVComponents.size());
                    if (relatedVComponents.size() == 1)
                    {
                        adjustDateTime();
//                        if (getVComponentEdited().childComponentsWithRecurrenceIDs().size() > 0)
//                        {
                        // Adjust children components with RecurrenceIDs
                        getVComponentEdited().childComponents()
                                .stream()
//                                .map(c -> c.getRecurrenceId())
                                .forEach(v ->
                                {
                                    TemporalAmount shiftAmount = DateTimeUtilities.temporalAmountBetween(startOriginalRecurrence, startRecurrence);
                                    Temporal newRecurreneId = v.getRecurrenceId().getValue().plus(shiftAmount);
                                    v.setRecurrenceId(newRecurreneId);
                                });
//                        }
//                        newRecurrences = updateRecurrences(vComponentEditedCopy);
                    } else
                    {
                        throw new RuntimeException("Only 1 relatedVComponents currently supported");
                    }
                    break;
                case CANCEL:
//                    getVComponentEdited().copyComponentFrom(vComponentOriginal);  // return to original
//                    return Arrays.asList(vComponentOriginal); // return original
                    return null;
                case THIS_AND_FUTURE:
                    editThisAndFuture(vComponents);
                    break;
                case ONE:
                    editOne(vComponents);
                    break;
                default:
                    throw new RuntimeException("Unknown response:" + changeResponse);
                }
            }
        }
        if (! getVComponentEdited().isValid())
        {
            throw new RuntimeException("Invalid component:" + System.lineSeparator() + 
                    getVComponentEdited().errors().stream().collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() +
                    getVComponentEdited().toContent());
        }
        if (incrementSequence)
        {
            getVComponentEdited().incrementSequence();
        }
        System.out.println("endedit:" +vComponents.size());
//        vComponents.remove(vComponentOriginal);
//        System.out.println(vComponents.size());
//        if (! vComponents.isEmpty())
//        {
        vComponents.add(vComponentEdited);
//        }
//        System.out.println(vComponents.size());
//        if (newRecurrences != null)
//        {
////            allRecurrences.clear();
//            allRecurrences.addAll(newRecurrences);
//        }
        return vComponents;
    }
    
    /** If startRecurrence isn't valid due to a RRULE change, change startRecurrence and
     * endRecurrence to closest valid values
     */
     // TODO - VERITFY THIS WORKS - changed from old version
    void validateStartRecurrenceAndDTStart(Temporal startRecurrence)
    {
        if (getVComponentEdited().getRecurrenceRule() != null)
        {
            Temporal firstTemporal = getVComponentEdited().getRecurrenceRule().getValue()
                    .streamRecurrences(getVComponentEdited().getDateTimeStart().getValue())
                    .findFirst()
                    .get();
            if (! firstTemporal.equals(getVComponentEdited().getDateTimeStart().getValue()))
            {
                getVComponentEdited().setDateTimeStart(firstTemporal);
            }
        }
    }
    
    void becomeNonRecurring()
    {
        getVComponentEdited().setRecurrenceRule((RecurrenceRule2) null);
        getVComponentEdited().setRecurrenceDates(null);
        getVComponentEdited().setExceptionDates(null);
    }
    
    void adjustDateTime()
    {
        TemporalAmount amount = DateTimeUtilities.temporalAmountBetween(getStartOriginalRecurrence(), getStartRecurrence());
        Temporal newStart = getVComponentEdited().getDateTimeStart().getValue().plus(amount);
        getVComponentEdited().setDateTimeStart(newStart);
//        System.out.println("new DTSTART2:" + newStart + " " + startRecurrence + " " + endRecurrence);
//        getVComponentEdited().setEndOrDuration(getStartRecurrence(), endRecurrence);
//        endType().setDuration(this, startRecurrence, endRecurrence);
    }
    
    /**
     * Generates a list of iCalendar property names that have different values from the 
     * input parameter
     * 
     * equal checks are encapsulated inside the enum VComponentProperty
     * @param <T>
     * @param <U>
     */
    List<PropertyType> findChangedProperties()
//            T vComponentEditedCopy,
//            T vComponentOriginal,
//            Temporal startOriginalRecurrence,
//            U startRecurrence,
//            U endRecurrence)
//            TemporalAmount shiftAmount)
    {
        List<PropertyType> changedProperties = new ArrayList<>();
        getVComponentEdited().properties()
                .stream()
                .map(p -> p.propertyType())
                .forEach(t ->
                {
                    Object p1 = t.getProperty(getVComponentEdited());
                    Object p2 = t.getProperty(getVComponentOriginal());
//                    System.out.println("prop:" + p1 + " " + p2);
                    if (! p1.equals(p2))
                    {
                        changedProperties.add(t);
                    }
                });
        
        /* Note:
         * time properties must be checked separately because changes are stored in startRecurrence and endRecurrence,
         * not the VComponents DTSTART and DTEND yet.  The changes to DTSTART and DTEND are made after the dialog
         * question is answered. */
        if (! startOriginalRecurrence.equals(startRecurrence))
        {
            changedProperties.add(PropertyType.DATE_TIME_START);
        }        
        return changedProperties;
    }
    
    /*
     * When one of these properties the change-scope dialog should be activated to determine if the changes should be applied
     * to ONE, ALL, or THIS_AND_FUTURE recurrences.  If other properties are modified the changes should be applied without a dialog.
     */
    @Override
    public List<PropertyType> dialogRequiredProperties()
    {
        return new ArrayList<>(Arrays.asList(             
            PropertyType.ATTACHMENT,
            PropertyType.ATTENDEE,
            PropertyType.CATEGORIES,
            PropertyType.COMMENT,
            PropertyType.CONTACT,
            PropertyType.DATE_TIME_START,
            PropertyType.DATE_TIME_END,
            PropertyType.RECURRENCE_RULE,
            PropertyType.STATUS,
            PropertyType.SUMMARY,
            PropertyType.UNIFORM_RESOURCE_LOCATOR
            ));
    }
    
    /**
     * Changing this and future instances in VComponent is done by ending the previous
     * VComponent with a UNTIL date or date/time and starting a new VComponent from 
     * the selected instance.  EXDATE, RDATE and RECURRENCES are split between both
     * VComponents.  vEventNew has new settings, vEvent has former settings.
     * 
     */
    void editThisAndFuture(Collection<U> vComponents)
    {
        // adjust original VEvent - remove COUNT, replace with UNTIL
        if (getVComponentOriginal().getRecurrenceRule().getValue().getCount() != null)
        {
            getVComponentOriginal().getRecurrenceRule().getValue().setCount(null);
        }
//        final Temporal untilNew = LocalDateTime.from(startRecurrence).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Z"));        
        
        final Temporal untilNew;
        if (getVComponentEdited().isWholeDay())
        {
            untilNew = getVComponentEdited().previousStreamValue(startRecurrence);
        } else
        {
            Temporal previousRecurrence = getVComponentEdited().previousStreamValue(startRecurrence);
            if (startRecurrence instanceof LocalDateTime)
            {
                untilNew = LocalDateTime.from(previousRecurrence).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Z"));
            } else if (startRecurrence instanceof ZonedDateTime)
            {
                untilNew = ((ZonedDateTime) previousRecurrence).withZoneSameInstant(ZoneId.of("Z"));
            } else
            {
                throw new DateTimeException("Unsupported Temporal type:" + previousRecurrence.getClass());
            }
            getVComponentOriginal().getRecurrenceRule().getValue().setUntil(untilNew);
        }
     
        
        
        // Adjust start and end
//        Temporal startOriginalRecurrence = startRecurrence.plus(shiftAmount);
        Period dateTimeStartShift = Period.between(LocalDate.from(getVComponentEdited().getDateTimeStart().getValue()),
                LocalDate.from(getVComponentOriginal().getDateTimeStart().getValue()));
//        TemporalAmount amount = DateTimeUtilities.temporalAmountBetween(startOriginalRecurrence, startRecurrence); // TODO - make work for different Temporal classes
        getVComponentEdited().setDateTimeStart(startOriginalRecurrence.plus(dateTimeStartShift));
//        getVComponentEdited().adjustDateTime(startOriginalRecurrence, startRecurrence, endRecurrence); // TODO - is this worthy of being in a method?
//        adjustDateTime(startRecurrence, startRecurrence, endInstance);
        getVComponentEdited().setUniqueIdentifier(); // ADD UID CALLBACK
        // only supports one RELATED-TO value
        String relatedUID = (getVComponentOriginal().getRelatedTo() == null) ?
                getVComponentOriginal().getUniqueIdentifier().getValue() : getVComponentOriginal().getRelatedTo().get(0).getValue();
        getVComponentEdited().withRelatedTo(relatedUID);
        getVComponentEdited().setDateTimeStamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Z")));
        
        // remove EXDATEs that are out of bounds
        if (getVComponentEdited().getExceptionDates() != null)
        {
            final Iterator<Temporal> exceptionDateIterator = getVComponentEdited().getExceptionDates()
                    .stream()
                    .flatMap(e -> e.getValue().stream())
                    .iterator();
            while (exceptionDateIterator.hasNext())
            {
                Temporal t = exceptionDateIterator.next();
                int result = DateTimeUtilities.TEMPORAL_COMPARATOR.compare(t, startRecurrence);
                if (result < 0)
                {
                    exceptionDateIterator.remove();
                }
            }
        }
        if (getVComponentOriginal().getExceptionDates() != null)
        {
            final Iterator<Temporal> exceptionDateIterator = getVComponentOriginal().getExceptionDates()
                    .stream()
                    .flatMap(e -> e.getValue().stream())
                    .iterator();
            while (exceptionDateIterator.hasNext())
            {
                Temporal t = exceptionDateIterator.next();
                int result = DateTimeUtilities.TEMPORAL_COMPARATOR.compare(t, startRecurrence);
                if (result > 0)
                {
                    exceptionDateIterator.remove();
                }
            }
        }
        
        // remove RDATEs that are out of bounds
        if (getVComponentEdited().getRecurrenceDates() != null)
        {
            final Iterator<Temporal> recurrenceDateIterator = getVComponentEdited().getRecurrenceDates()
                    .stream()
                    .flatMap(e -> e.getValue().stream())
                    .iterator();
            while (recurrenceDateIterator.hasNext())
            {
                Temporal t = recurrenceDateIterator.next();
                int result = DateTimeUtilities.TEMPORAL_COMPARATOR.compare(t, startRecurrence);
                if (result < 0)
                {
                    recurrenceDateIterator.remove();
                }
            }
        }
        if (getVComponentOriginal().getRecurrenceDates() != null)
        {
            final Iterator<Temporal> recurrenceDateIterator = getVComponentOriginal().getRecurrenceDates()
                    .stream()
                    .flatMap(e -> e.getValue().stream())
                    .iterator();
            while (recurrenceDateIterator.hasNext())
            {
                Temporal t = recurrenceDateIterator.next();
                int result = DateTimeUtilities.TEMPORAL_COMPARATOR.compare(t, startRecurrence);
                if (result > 0)
                {
                    recurrenceDateIterator.remove();
                }
            }
        }
        
        // remove RECURRENCE-ID components that are out of bounds
        if (getVComponentEdited().childComponents() != null)
        {
            final Iterator<Temporal> recurrenceIDIterator = getVComponentEdited().childComponents()
                    .stream()
                    .map(e -> e.getRecurrenceId().getValue())
                    .iterator();
            while (recurrenceIDIterator.hasNext())
            {
                Temporal t = recurrenceIDIterator.next();
                int result = DateTimeUtilities.TEMPORAL_COMPARATOR.compare(t, startRecurrence);
                if (result < 0)
                {
                    recurrenceIDIterator.remove();
                }
            }
        }
        if (getVComponentOriginal().getRecurrenceDates() != null)
        {
            final Iterator<Temporal> recurrenceIDIterator = getVComponentOriginal().childComponents()
                    .stream()
                    .map(e -> e.getRecurrenceId().getValue())
                    .iterator();
            while (recurrenceIDIterator.hasNext())
            {
                Temporal t = recurrenceIDIterator.next();
                int result = DateTimeUtilities.TEMPORAL_COMPARATOR.compare(t, startRecurrence);
                if (result > 0)
                {
                    recurrenceIDIterator.remove();
                }
            }
        }
        
        // Modify COUNT for the edited vEvent
//        System.out.println(getVComponentOriginal().getRecurrenceRule().getValue());
//        System.out.println(getVComponentEdited().getRecurrenceRule().getValue());
        if (getVComponentEdited().getRecurrenceRule().getValue().getCount() != null)
        {
            int countInOrginal = (int) getVComponentOriginal().streamRecurrences().count();
            int countInNew = getVComponentEdited().getRecurrenceRule().getValue().getCount().getValue() - countInOrginal;
            getVComponentEdited().getRecurrenceRule().getValue().setCount(countInNew);
        }
        
        if (! getVComponentOriginal().isValid())
        {
            throw new RuntimeException("Invalid component:" + System.lineSeparator() + 
                    getVComponentOriginal().errors().stream().collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() +
                    getVComponentOriginal().toContent());
        }
        vComponents.add(getVComponentOriginal());
        

        // Remove old appointments, add back ones
//        Collection<R> recurrencesTemp = new ArrayList<>(); // use temp array to avoid unnecessary firing of Agenda change listener attached to appointments
//        recurrencesTemp.addAll(recurrences);
//        recurrencesTemp.removeIf(a -> getVComponentOriginal().recurrences().stream().anyMatch(a2 -> a2 == a));
//        getVComponentOriginal().recurrences().clear(); // clear vEvent outdated collection of appointments
//        recurrencesTemp.addAll(getVComponentOriginal().makeInstances()); // make new appointments and add to main collection (added to vEvent's collection in makeAppointments)
//        recurrences().clear(); // clear vEvent's outdated collection of appointments
//        recurrencesTemp.addAll(makeInstances()); // add vEventOld part of new appointments
//        return recurrencesTemp;
//        instances.clear();
//        instances.addAll(instancesTemp);
        
        // UPDATE RECURRENCES
        // TODO - MOVE TO LISTENERS IN AGENDA
//        Collection<R> componentRecurrences = vComponentRecurrencetMap.get(vComponentEditedCopy);
//        Collection<R> recurrencesTemp = new ArrayList<>(); // use temp array to avoid unnecessary firing of Agenda change listener attached to appointments
//        recurrencesTemp.addAll(recurrences);
//        recurrencesTemp.removeIf(a -> componentRecurrences.stream().anyMatch(a2 -> a2 == a));
//        List<R> newRecurrences = makeRecurrences(vComponentOriginal);
//        vComponentRecurrencetMap.put(System.identityHashCode(vComponentOriginal), newRecurrences);
//        recurrencesTemp.addAll(newRecurrences); // make new recurrences and add to main collection (added to VEvent's collection in makeAppointments)
        
//        return recurrencesTemp;
    }
    
    /**
     * Edit one instance of a VEvent with a RRule.  The instance becomes a new VEvent without a RRule
     * as with the same UID as the parent and a recurrence-id for the replaced date or date/time.
     * 
     */
    void editOne(Collection<U> vComponents)
    {
        // Remove RRule and set parent component
        getVComponentEdited().setRecurrenceRule((RecurrenceRule) null);
//        setParent(vComponentOriginal);

        // Apply dayShift, account for editing recurrence beyond first
        Period shiftAmount = Period.between(LocalDate.from(getVComponentEdited().getDateTimeStart().getValue()),
                  LocalDate.from(startOriginalRecurrence));
        Temporal newStart = getVComponentEdited().getDateTimeStart().getValue().plus(shiftAmount);
        getVComponentEdited().setDateTimeStart(newStart);
//        getVComponentEdited().adjustDateTime(startOriginalRecurrence, startRecurrence, endRecurrence);
//        Temporal startOriginalRecurrence = startRecurrence.plus(shiftAmount);
        getVComponentEdited().setRecurrenceId(startOriginalRecurrence);
        getVComponentEdited().setDateTimeStamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Z")));
   
        // Add recurrence to original vEvent - done automatically
//        vComponentOriginal.childComponents().add(vComponentEditedCopy);
        
        // Check for validity
        if (! getVComponentEdited().isValid()) { throw new RuntimeException("Invalid component"); }
//        System.out.println("here:" + vComponentOriginal);
        if (! vComponentOriginal.isValid()) { throw new RuntimeException("Invalid component"); }
        
        // Remove old recurrences, add back ones
//        Collection<R> recurrencesTemp = new ArrayList<>(); // use temp array to avoid unnecessary firing of Agenda change listener attached to appointments
//        recurrencesTemp.addAll(recurrences);
//        Collection<R> componentRecurrences = vComponentRecurrencetMap.get(vComponentEditedCopy);
//        recurrencesTemp.removeIf(a -> componentRecurrences.stream().anyMatch(a2 -> a2 == a));
//        List<R> newRecurrences = makeRecurrences(vComponentOriginal);
//        vComponentRecurrencetMap.put(System.identityHashCode(vComponentOriginal), newRecurrences);
//        recurrencesTemp.addAll(newRecurrences); // make new recurrences and add to main collection (added to VEvent's collection in makeAppointments)

//        recurrences().clear(); // clear vEvent outdated collection of recurrences
//        recurrencesTemp.addAll(makeRecurrences()); // add vEventOld part of new recurrences
        vComponents.add(vComponentOriginal);
//        return recurrencesTemp;
    }
}