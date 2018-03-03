package com.lapots.breed.judge.service

import com.lapots.breed.judge.common.JudgeJpaAbstractSpecification
import com.lapots.breed.judge.domain.Player
import com.lapots.breed.judge.domain.PlayerLevel
import com.lapots.breed.judge.repository.api.IPlayerLevelRepository
import reactor.core.publisher.Mono

class PlayerRuleCalculationServiceTestSpec extends JudgeJpaAbstractSpecification {

    def "should return level up player"(int lvl, int nlvl, long exp) {
        setup:
            def maxLevel = new PlayerLevel(3, 1000)
            IPlayerLevelRepository levelRepository = Mock {
                findClosestToExperienceLevel(exp) >> new PlayerLevel(nlvl, exp)
                findMaxLevel() >> maxLevel
            }
            def service = new PlayerRuleCalculationService(levelRepository)
        expect:
            new Player(1, nlvl, exp) == service.levelUp(Mono.just(new Player(1, lvl, exp))).block()
        where:
            lvl | nlvl | exp
             1  |  1   | 2
             1  |  2   | 102
             1  |  1   | 99
             3  |  3   | 15000
    }
}
