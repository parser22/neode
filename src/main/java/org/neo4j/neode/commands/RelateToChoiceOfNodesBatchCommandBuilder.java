package org.neo4j.neode.commands;

import java.util.List;

import org.neo4j.neode.DomainEntityInfo;

public class RelateToChoiceOfNodesBatchCommandBuilder
{
    private static final int DEFAULT_BATCH_SIZE = 10000;

    private final DomainEntityInfo domainEntityInfo;
    private final EntityChoices entityChoices;

    public RelateToChoiceOfNodesBatchCommandBuilder( DomainEntityInfo domainEntityInfo, EntityChoices entityChoices )
    {
        this.domainEntityInfo = domainEntityInfo;
        this.entityChoices = entityChoices;
    }

    public List<DomainEntityInfo> update( Dataset dataset, int batchSize )
    {
        CommandSelector commandSelector = entityChoices.createCommandSelector( domainEntityInfo, batchSize );
        RelateToChoiceOfNodesBatchCommand command = new RelateToChoiceOfNodesBatchCommand( domainEntityInfo,
                commandSelector, batchSize );
        dataset.execute( command );
        return command.results();
    }

    public List<DomainEntityInfo> update( Dataset dataset )
    {
        CommandSelector commandSelector = entityChoices.createCommandSelector( domainEntityInfo,DEFAULT_BATCH_SIZE );
        RelateToChoiceOfNodesBatchCommand command = new RelateToChoiceOfNodesBatchCommand( domainEntityInfo,
                commandSelector, DEFAULT_BATCH_SIZE );
        dataset.execute( command );
        return command.results();
    }
}