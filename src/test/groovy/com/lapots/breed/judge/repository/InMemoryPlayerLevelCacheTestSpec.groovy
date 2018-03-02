package com.lapots.breed.judge.repository

import com.lapots.breed.judge.common.JudgeWebFluxAbstractSpecification
import org.springframework.beans.factory.annotation.Autowired

class InMemoryPlayerLevelCacheTestSpec extends JudgeWebFluxAbstractSpecification {

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

    def "should return everything"() {
        when:
            cache.put(1, 10)
            cache.put(2, 100)
            cache.put(3, 1000)
        then:
            [1:10, 2:100, 3:1000] == cache.getAll();
    }
}
