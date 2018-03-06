package com.lapots.breed.judge.rest.facade

import com.lapots.breed.judge.domain.Player
import com.lapots.breed.judge.repository.PlayerRepository
import com.lapots.breed.judge.rest.exception.IncorrectRequestContentException
import com.lapots.breed.judge.service.rule.RuleExecutionService
import spock.lang.Specification

class PlayerLevelServiceFacadeTestSpec extends Specification {

    def "should level up player"() {
        setup:
            def player = new Player(1, 1, 100)
            def expected  = new Player(1, 2, 100)
            PlayerRepository playerRepository = Mock {
                findById(_) >> Optional.of(player)
            }
            RuleExecutionService ruleExecutionService = Mock {
                levelUpPlayer(_) >> expected
            }
            def serviceFacade = new PlayerLevelServiceFacade(ruleExecutionService, playerRepository)
        expect:
            expected == serviceFacade.levelUp(player)
    }

    def "should throw exception if no player found"() {
        setup:
            PlayerRepository playerRepository = Mock {
                findById(_) >> Optional.empty()
            }
            def serviceFacade = new PlayerLevelServiceFacade(null, playerRepository)
        when:
            serviceFacade.levelUp(new Player())
        then:
            IncorrectRequestContentException ex = thrown()
    }

}
