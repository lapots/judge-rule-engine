package com.lapots.breed.judge.support

import groovy.transform.AnnotationCollector;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.transaction.annotation.Transactional;

@AnnotationCollector([
    WebFluxTest,
    Transactional,
    AutoConfigureDataJpa,
    AutoConfigureTestDatabase,
    AutoConfigureTestEntityManager,
])
@interface IntegrationTest {
}
