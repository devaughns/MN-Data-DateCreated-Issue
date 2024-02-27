package com.test

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import java.time.Instant
import java.time.temporal.ChronoUnit

@MicronautTest
class DateCreatedUpdateTest extends Specification implements DatabaseFixture {

    def setup() {
        migrate()
    }

    def "Test dateCreated insert"() {
        given:
        TestEntity test = new TestEntity();

        when:
        testRepository.save(test).block()

        then:
        test.dateProcessed != null

    }

    def "Test dateCreated update"() {
        given:
        TestEntity test = new TestEntity();
        test = testRepository.save(test).block()
        Instant tenDaysAgo = Instant.now().minus(10, ChronoUnit.DAYS)

        when:
        test.dateProcessed = tenDaysAgo
        test = testRepository.update(test).block()
        TestEntity fromDB = testRepository.findById(test.id).block()

        then:
        fromDB.dateProcessed == tenDaysAgo
    }

}
