package com.lapots.breed.judge.service

import com.lapots.breed.judge.JudgeRuleEngineReactiveWebTestSpec
import com.lapots.breed.judge.domain.Player
import com.lapots.breed.judge.repository.PlayerLevelCacheWrapper
import reactor.core.publisher.Mono

class PlayerRuleCalculationServiceTestSpec extends JudgeRuleEngineReactiveWebTestSpec {

    def "levels up player if needed"(int lvl, int nlvl, long exp) {
        setup:
            def player = Mono.just(createPlayer(lvl, exp))
            def expected = Mono.just(createPlayer(nlvl, exp))
            PlayerLevelCacheWrapper wrapper = Mock {
                getClosestNextLevel(exp) >> nlvl
                getMaxLevel() >> 5
            }
            def service = new PlayerRuleCalculationService(wrapper)
        expect:
            assert expected.block() == service.levelUp(player).block()
        where:
            lvl | nlvl  | exp
              1 | 1     | 20
              2 | 3     | 1100
              5 | 5     | 10000
    }

    def createPlayer(int lvl, long exp) {
        def player = new Player()
        player.with {
            (level, experience) = [ lvl, exp ]
        }

        player
    }
}
