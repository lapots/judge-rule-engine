package com.lapots.breed.judge.repository

import com.lapots.breed.judge.JudgeRuleEngineReactiveWebTestSpec

class PlayerLevelCacheWrapperTestSpec extends JudgeRuleEngineReactiveWebTestSpec {

    def "returns closest level according to the provided experience" (int level, long exp) {
        setup:
            InMemoryPlayerLevelCache cache = Mock {
                getAll() >> [1 : 100L, 2 : 1000L, 3 : 10000L]
            }
            def wrapper = new PlayerLevelCacheWrapper(cache)
        expect:
            assert level == wrapper.getClosestNextLevel(exp)
        where:
            level | exp
              1   | 102
              2   | 1200
              1   | 999
              2   | 9999
              3   | 10010
              3   | 12000
    }

}
