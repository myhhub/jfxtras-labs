package jfxtras.labs.icalendarfx.properties.component.relationship;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import jfxtras.labs.icalendarfx.components.VEventNew;
import jfxtras.labs.icalendarfx.components.VJournal;
import jfxtras.labs.icalendarfx.components.VTodo;
import jfxtras.labs.icalendarfx.parameters.ParameterEnum;
import jfxtras.labs.icalendarfx.parameters.Relationship;
import jfxtras.labs.icalendarfx.parameters.Relationship.RelationshipType;
import jfxtras.labs.icalendarfx.properties.PropertyBase;
import jfxtras.labs.icalendarfx.properties.PropertyRelationship;

/**
 * RELATED-TO
 * RFC 5545, 3.8.4.5, page 115
 * 
 * This property is used to represent a relationship or
 * reference between one calendar component and another.
 * 
 * Example:
 * RECURRENCE-ID;VALUE=DATE:19960401
 * 
 * @author David Bal
 * @see VEventNew
 * @see VTodo
 * @see VJournal
 */
public class RelatedTo extends PropertyBase<String, RelatedTo> implements PropertyRelationship<String>
{
    /**
     * RELTYPE
     * Relationship Type
     * RFC 5545, 3.2.15, page 25
     * 
     * To specify the type of hierarchical relationship associated
     *  with the calendar component specified by the property.
     * 
     * Example:
     * RELATED-TO;RELTYPE=SIBLING:19960401-080045-4000F192713@
     *  example.com
     */
    @Override
    public Relationship getRelationship() { return (relationship == null) ? null : relationship.get(); }
    @Override
    public ObjectProperty<Relationship> relationshipProperty()
    {
        if (relationship == null)
        {
            relationship = new SimpleObjectProperty<>(this, ParameterEnum.RELATIONSHIP_TYPE.toString());
        }
        return relationship;
    }
    private ObjectProperty<Relationship> relationship;
    @Override
    public void setRelationship(Relationship relationship) { relationshipProperty().set(relationship); }
    public void setRelationship(String value) { setRelationship(new Relationship(value)); }
    public RelatedTo withRelationship(Relationship altrep) { setRelationship(altrep); return this; }
    public RelatedTo withRelationship(RelationshipType value) { setRelationship(new Relationship(value)); return this; }
    public RelatedTo withRelationship(String content) { setRelationship(content); return this; }
    
    public RelatedTo(CharSequence contentLine)
    {
        super(contentLine);
    }

    public RelatedTo(UniqueIdentifier source)
    {
        super(source);
    }
}
