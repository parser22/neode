package org.neo4j.neode.properties;

import org.junit.Test;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PropertyContainer;
import org.neo4j.graphdb.Transaction;
import org.neo4j.neode.test.Db;

import static org.junit.Assert.assertEquals;

public class IndexablePropertyTest
{
    @Test
    public void shouldIndexProperty() throws Exception
    {
        // given
        PropertyValueGenerator generator = new PropertyValueGenerator()
        {
            @Override
            public Object generateValue( PropertyContainer propertyContainer, String nodeLabel, int iteration )
            {
                return "value";
            }
        };
        Property property = new IndexableProperty( "name", generator, "indexname" );

        GraphDatabaseService db = Db.impermanentDb();
        Node node;
        try ( Transaction tx = db.beginTx() )
        {
            node = db.createNode();

            // when
            property.setProperty( node, db, "user", 1 );
            tx.success();
        }

        // then
        try ( Transaction tx = db.beginTx() )
        {
            assertEquals( node, db.index().forNodes( "indexname" ).get( "name", "value" ).getSingle() );
            tx.success();
        }
    }

    @Test
    public void shouldIndexPropertyInIndexNamedAfterLabelIfIndexNameNotSupplied() throws Exception
    {
        // given
        PropertyValueGenerator generator = new PropertyValueGenerator()
        {
            @Override
            public Object generateValue( PropertyContainer propertyContainer, String nodeLabel, int iteration )
            {
                return "value";
            }
        };
        Property property = new IndexableProperty( "name", generator );

        GraphDatabaseService db = Db.impermanentDb();
        Node node;
        try ( Transaction tx = db.beginTx() )
        {
            node = db.createNode();

            // when
            property.setProperty( node, db, "user", 1 );
            tx.success();
        }

        // then
        try ( Transaction tx = db.beginTx() )
        {
            assertEquals( node, db.index().forNodes( "user" ).get( "name", "value" ).getSingle() );
            tx.success();
        }
    }
}
