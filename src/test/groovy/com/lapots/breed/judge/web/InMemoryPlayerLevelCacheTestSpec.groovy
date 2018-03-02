package com.lapots.breed.judge.web

import com.lapots.breed.judge.repository.InMemoryPlayerLevelCache
import com.lapots.breed.judge.web.common.JudgeWebFluxAbstractSpecification
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
}
