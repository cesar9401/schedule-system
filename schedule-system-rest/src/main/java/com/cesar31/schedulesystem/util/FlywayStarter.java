package com.cesar31.schedulesystem.util;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;

import javax.sql.DataSource;

@Startup
@Singleton
public class FlywayStarter {

    @Inject
    Logger logger;

    @Resource(name = "java:app/jdbc/schedule-system")
    DataSource dataSource;

    @PostConstruct
    private void startUp() {
        var flyway = Flyway
                .configure()
                .dataSource(dataSource)
                .locations("database/mssql-schedule")
                .table("schema_version_schedule")
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .load();

        var migrationInfo = flyway.info().current();
        if (migrationInfo != null) {
            logger.info("Found a database with the version: {} - {}", migrationInfo.getVersion(), migrationInfo.getDescription());
        }

        flyway.migrate();
        logger.info("Successfully migrated to version: {}", flyway.info().current().getVersion());
    }
}
