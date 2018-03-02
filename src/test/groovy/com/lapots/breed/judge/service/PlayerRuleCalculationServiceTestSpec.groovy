package com.lapots.breed.judge.service

import com.lapots.breed.judge.JudgeRuleEngineReactiveWebTestSpec
import com.lapots.breed.judge.repository.PlayerLevelCacheWrapper

class PlayerRuleCalculationServiceTestSpec extends JudgeRuleEngineReactiveWebTestSpec {

    def "should increase level"(int cur, long exp, int next, int res) {
        setup:
            PlayerLevelCacheWrapper wrapper = Mock {
                getClosestNextLevel(exp) >> next
            }
            def service = new PlayerRuleCalculationService(wrapper)
        expect:
            assert res == service.levelUp(exp, cur)
        where:
            cur | exp  | next | res
              1 | 10   | 1    | 1
              1 | 120  | 2    | 2
    }
}
