package com.back.global.jpa.replication;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Random;

public class DataSourceRouter extends AbstractRoutingDataSource {
    private final Random random = new Random();

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()){
            return random.nextInt(2)+1;
        }
        return 0;
    }
}
