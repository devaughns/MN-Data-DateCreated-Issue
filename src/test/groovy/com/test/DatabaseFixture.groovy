package com.test

import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.ClassicConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared

@Testcontainers
trait DatabaseFixture implements TestPropertyProvider {

    @Inject
    TestRepository testRepository

    static String getPostgresImageName() {
        return "postgres:12"
    }


    @Shared
    private static final PostgreSQLContainer DB_CONTAINER = new PostgreSQLContainer(
            DockerImageName.parse(getPostgresImageName())
    )

    @Shared
    ClassicConfiguration config = new ClassicConfiguration()

    void migrate(boolean baseline = true) {
        DB_CONTAINER.start()
        config.setDataSource(DB_CONTAINER.jdbcUrl, DB_CONTAINER.username, DB_CONTAINER.password)
        Flyway flyway = new Flyway(config)
        if (baseline) flyway.baseline()
        flyway.migrate()
    }

    Map<String, String> getDatabaseProperties() {
        if (!DB_CONTAINER.isRunning()) DB_CONTAINER.start()

        var props = [
                "r2dbc.datasources.default.url"     : "r2dbc:postgresql://${DB_CONTAINER.getHost()}:${DB_CONTAINER.getFirstMappedPort()}/test",
                "r2dbc.datasources.default.username": DB_CONTAINER.getUsername(),
                "r2dbc.datasources.default.password": DB_CONTAINER.getPassword(),
        ]

        return props
    }

    @Override
    Map<String, String> getProperties() {
        return [:] + getDatabaseProperties()

    }
}