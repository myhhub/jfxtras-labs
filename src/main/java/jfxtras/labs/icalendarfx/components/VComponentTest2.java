package jfxtras.labs.icalendarfx.components;

public class VComponentTest2 extends VComponentRepeatableBase<VComponentTest2>
{

    /*
     * CONSTRUCTORS
     */
    public VComponentTest2() { }
    
    public VComponentTest2(String contentLines)
    {
        super(contentLines);
    }
    
    @Override
    public VComponentEnum componentType()
    {
        return VComponentEnum.STANDARD_OR_SAVINGS_TIME; // for testing
    }
}
