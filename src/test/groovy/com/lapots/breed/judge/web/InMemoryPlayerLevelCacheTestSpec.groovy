package com.lapots.breed.judge.web

import com.lapots.breed.judge.repository.InMemoryPlayerLevelCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import spock.lang.Specification

@WebFluxTest(excludeFilters =
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
class InMemoryPlayerLevelCacheTestSpec extends Specification {

    @Autowired
    InMemoryPlayerLevelCache cache

    // TODO:investigate exclude
    def setup() {
        cache.clear()
    }

    def "should access cache"() {
        when:
            cache.put(10, 200)
        then:
            1 == cache.size()
            200 == cache.get(10)
    }
}
