package org.neo4j.neode.properties;

import java.util.Random;

import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.neode.numbergenerators.ProbabilityDistribution;
import org.neo4j.neode.numbergenerators.Range;

class RangeBasedIntegerPropertyValueSetter extends PropertyValueSetter
{
    private final Range range;
    private final ProbabilityDistribution probabilityDistribution;

    public RangeBasedIntegerPropertyValueSetter( Range range, ProbabilityDistribution probabilityDistribution )
    {
        this.range = range;
        this.probabilityDistribution = probabilityDistribution;
    }

    @Override
    public Object setProperty( PropertyContainer propertyContainer, String propertyName, String entityName, int index, Random random )
    {
        int value = probabilityDistribution.generateSingle( range, random );
        propertyContainer.setProperty( propertyName, value );
        return value;
    }
}
