package jfxtras.labs.repeatagenda.scene.control.repeatagenda.icalendar.rrule.byxxx;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import javafx.beans.property.ObjectProperty;
import jfxtras.labs.repeatagenda.scene.control.repeatagenda.icalendar.rrule.freq.Frequency;

public class ByMinute extends ByRuleAbstract
{
    private final static int PROCESS_ORDER = 60; // order for processing Byxxx Rules from RFC 5545 iCalendar page 44

    public ByMinute(Frequency frequency)
    {
        super(PROCESS_ORDER);
        throw new RuntimeException("not implemented");
    }
    
    @Override
    public Stream<LocalDateTime> stream(Stream<LocalDateTime> inStream, ObjectProperty<ChronoUnit> chronoUnit,
            LocalDateTime startDateTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void copyTo(Rule destination) {
        // TODO Auto-generated method stub
        
    }


}