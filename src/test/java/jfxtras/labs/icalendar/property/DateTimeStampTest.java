package jfxtras.labs.icalendar.property;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

import jfxtras.labs.icalendar.properties.component.change.DateTimeStamp;

public class DateTimeStampTest
{
    @Test
    public void canParseDateTimeStamp()
    {
        String expectedContentLine = "DTSTAMP:19971210T080000Z";
        DateTimeStamp property = new DateTimeStamp(expectedContentLine);
        String madeContentLine = property.toContentLine();
        assertEquals(expectedContentLine, madeContentLine);
        assertEquals(ZonedDateTime.of(LocalDateTime.of(1997, 12, 10, 8, 0), ZoneId.of("Z")), property.getValue());
    }
}