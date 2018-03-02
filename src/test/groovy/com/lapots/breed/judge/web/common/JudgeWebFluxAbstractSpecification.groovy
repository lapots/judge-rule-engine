package com.lapots.breed.judge.web.common

import com.lapots.breed.judge.WebConfig
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [ WebConfig.class ])
@WebFluxTest
abstract class JudgeWebFluxAbstractSpecification extends Specification {
}
