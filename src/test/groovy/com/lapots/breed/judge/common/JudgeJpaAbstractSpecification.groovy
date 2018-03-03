package com.lapots.breed.judge.common

import com.lapots.breed.judge.WebConfig
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [ WebConfig.class ])
@DataJpaTest
class JudgeJpaAbstractSpecification extends Specification {
}
