package jfxtras.labs.icalendarfx.properties.component.time;

import java.util.HashMap;
import java.util.Map;

import javafx.util.StringConverter;
import jfxtras.labs.icalendarfx.components.VEventNew;
import jfxtras.labs.icalendarfx.properties.PropertyBase;
import jfxtras.labs.icalendarfx.properties.component.time.TimeTransparency.TimeTransparencyType;

/**
 * TRANSP
 * Time Transparency
 * RFC 5545 iCalendar 3.8.2.7. page 101
 * 
 * This property defines whether or not an event is transparent to busy time searches.
 * Events that consume actual time SHOULD be recorded as OPAQUE.  Other
 * events, which do not take up time SHOULD be recorded as TRANSPARENT.
 *    
 * Example:
 * TRANSP:TRANSPARENT
 *
 * @author David Bal
 * 
 * The property can be specified in following components:
 * @see VEventNew
 */
    // TODO - REMOVE UNKNOWN AS VALUE - MAKE PROPER WITH REAL UNKNOWN WORK BEFORE DELETING CODE
public class TimeTransparency extends PropertyBase<TimeTransparencyType,TimeTransparency>
{
    private final static StringConverter<TimeTransparencyType> CONVERTER = new StringConverter<TimeTransparencyType>()
    {
        @Override
        public String toString(TimeTransparencyType object)
        {
            if (object == TimeTransparencyType.UNKNOWN)
            {
                // null means value is unknown and non-converted string in PropertyBase unknownValue should be used instead
                return null;
            }
            return object.toString();
        }

        @Override
        public TimeTransparencyType fromString(String string)
        {
            return TimeTransparencyType.enumFromName(string);
        }
    };

    public TimeTransparency(CharSequence contentLine)
    {
        super();
        setConverter(CONVERTER);
        parseContent(contentLine);
        
    }
    
    public TimeTransparency(TimeTransparencyType value)
    {
        super();
        setConverter(CONVERTER);
        setValue(value);
    }
    
    public TimeTransparency(TimeTransparency source)
    {
        super(source);
    }
    
    public TimeTransparency()
    {
        super();
        setConverter(CONVERTER);
        setValue(TimeTransparencyType.OPAQUE); // default value
    }
    
    public enum TimeTransparencyType
    {
        OPAQUE,
        TRANSPARENT,
        UNKNOWN;
        
        private static Map<String, TimeTransparencyType> enumFromNameMap = makeEnumFromNameMap();
        private static Map<String, TimeTransparencyType> makeEnumFromNameMap()
        {
            Map<String, TimeTransparencyType> map = new HashMap<>();
            TimeTransparencyType[] values = TimeTransparencyType.values();
            for (int i=0; i<values.length; i++)
            {
                map.put(values[i].toString(), values[i]);
            }
            return map;
        }
        /** get enum from name */
        public static TimeTransparencyType enumFromName(String propertyName)
        {
            TimeTransparencyType type = enumFromNameMap.get(propertyName.toUpperCase());
            return (type == null) ? UNKNOWN : type;
        }
    }
}
