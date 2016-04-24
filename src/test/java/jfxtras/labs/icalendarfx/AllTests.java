package jfxtras.labs.icalendarfx;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import jfxtras.labs.icalendarfx.component.BaseTest;
import jfxtras.labs.icalendarfx.component.GeneralTest;
import jfxtras.labs.icalendarfx.component.PersonalTest;
import jfxtras.labs.icalendarfx.component.PrimaryTest;
import jfxtras.labs.icalendarfx.component.StandardTimeTest;
import jfxtras.labs.icalendarfx.component.VAlarmTest;
import jfxtras.labs.icalendarfx.component.VEventTest;
import jfxtras.labs.icalendarfx.component.VFreeBusyTest;
import jfxtras.labs.icalendarfx.component.VJournalTest;
import jfxtras.labs.icalendarfx.component.VTimeZoneTest;
import jfxtras.labs.icalendarfx.component.VTodoTest;
import jfxtras.labs.icalendarfx.parameter.DelegateesTest;
import jfxtras.labs.icalendarfx.parameter.DirectoryEntryReferenceTest;
import jfxtras.labs.icalendarfx.parameter.ParseParameterTest;
import jfxtras.labs.icalendarfx.parameter.RecurrenceRuleParameterTest;
import jfxtras.labs.icalendarfx.property.ActionTest;
import jfxtras.labs.icalendarfx.property.AttachmentTest;
import jfxtras.labs.icalendarfx.property.AttendeeTest;
import jfxtras.labs.icalendarfx.property.CategoriesTest;
import jfxtras.labs.icalendarfx.property.ClassificationTest;
import jfxtras.labs.icalendarfx.property.CommentTest;
import jfxtras.labs.icalendarfx.property.DateTimeCompletedTest;
import jfxtras.labs.icalendarfx.property.DateTimeCreatedTest;
import jfxtras.labs.icalendarfx.property.DateTimeDueTest;
import jfxtras.labs.icalendarfx.property.DateTimeEndTest;
import jfxtras.labs.icalendarfx.property.DateTimeStampTest;
import jfxtras.labs.icalendarfx.property.DateTimeStartTest;
import jfxtras.labs.icalendarfx.property.DescriptionTest;
import jfxtras.labs.icalendarfx.property.DurationTest;
import jfxtras.labs.icalendarfx.property.ExceptionsTest;
import jfxtras.labs.icalendarfx.property.FreeBusyTimeTest;
import jfxtras.labs.icalendarfx.property.GeneralPropertyTest;
import jfxtras.labs.icalendarfx.property.IANATest;
import jfxtras.labs.icalendarfx.property.LocationTest;
import jfxtras.labs.icalendarfx.property.NonStandardTest;
import jfxtras.labs.icalendarfx.property.OrganizerTest;
import jfxtras.labs.icalendarfx.property.ParsePropertyTest;
import jfxtras.labs.icalendarfx.property.RecurrenceIdTest;
import jfxtras.labs.icalendarfx.property.RecurrenceRuleTest;
import jfxtras.labs.icalendarfx.property.RecurrencesTest;
import jfxtras.labs.icalendarfx.property.RepeatCountTest;
import jfxtras.labs.icalendarfx.property.ResourcesTest;
import jfxtras.labs.icalendarfx.property.SequenceTest;
import jfxtras.labs.icalendarfx.property.StatusTest;
import jfxtras.labs.icalendarfx.property.SummaryTest;
import jfxtras.labs.icalendarfx.property.TimeTransparencyTest;
import jfxtras.labs.icalendarfx.property.TimeZoneIdentifierTest;
import jfxtras.labs.icalendarfx.property.TimeZoneNameTest;
import jfxtras.labs.icalendarfx.property.TimeZoneOffsetTest;
import jfxtras.labs.icalendarfx.property.TimeZoneURLTest;
import jfxtras.labs.icalendarfx.property.TriggerTest;
import jfxtras.labs.icalendarfx.property.URLTest;
import jfxtras.labs.icalendarfx.property.UniqueIdentifierTest;

@RunWith(Suite.class)
@SuiteClasses({ 
                ICalendarCopyTest.class,
                ICalendarDateTest.class,
                ICalendarDeleteTest.class,
                ICalendarEditTest.class,
                ICalendarEqualsTest.class,
                ICalendarMakeInstancesTest.class,
//              , ICalendarParseVEventTest.class
//                ICalendarVEventToStringTest.class,
//              , ICalendarReadICSTest.class,
        
        //component tests
        GeneralTest.class,
        BaseTest.class,
        PrimaryTest.class,
        PersonalTest.class,
        VEventTest.class,
        VTodoTest.class,
        VJournalTest.class,
        VFreeBusyTest.class,
        VTimeZoneTest.class,
        VAlarmTest.class,
        StandardTimeTest.class,
       
       // property tests
        ActionTest.class,
        AttachmentTest.class,
        AttendeeTest.class,
        CategoriesTest.class,
        ClassificationTest.class,
        CommentTest.class,
        DateTimeCompletedTest.class,
        DateTimeCreatedTest.class,
        DateTimeDueTest.class,
        DateTimeEndTest.class,
        DateTimeStampTest.class,
        DateTimeStartTest.class,
        DescriptionTest.class,
        DurationTest.class,
        ExceptionsTest.class,
        FreeBusyTimeTest.class,
        GeneralPropertyTest.class,
        IANATest.class,
        LocationTest.class,
        NonStandardTest.class,
        OrganizerTest.class,
        ParsePropertyTest.class,
        RecurrenceIdTest.class,
        RecurrenceRuleTest.class,
        RecurrencesTest.class,
        RepeatCountTest.class,
        ResourcesTest.class,
        SequenceTest.class,
        StatusTest.class,
        SummaryTest.class,
        TimeTransparencyTest.class,
        TimeZoneIdentifierTest.class,
        TimeZoneNameTest.class,
        TimeZoneOffsetTest.class,
        TimeZoneURLTest.class,
        TriggerTest.class,
        UniqueIdentifierTest.class,
        URLTest.class,
        
        // parameter tests
        DelegateesTest.class,
        DirectoryEntryReferenceTest.class, 
        ParseParameterTest.class,
        RecurrenceRuleParameterTest.class
              
              })

public class AllTests {

}
