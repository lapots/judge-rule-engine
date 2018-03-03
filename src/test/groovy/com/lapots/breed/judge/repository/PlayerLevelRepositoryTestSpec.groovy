package com.lapots.breed.judge.repository

import com.lapots.breed.judge.common.JudgeJpaAbstractSpecification
import com.lapots.breed.judge.domain.PlayerLevel
import com.lapots.breed.judge.repository.api.IPlayerLevelRepository
import org.springframework.beans.factory.annotation.Autowired

class PlayerLevelRepositoryTestSpec extends JudgeJpaAbstractSpecification {

    @Autowired
    IPlayerLevelRepository repository

    def setup() {
        repository.deleteAll() // it fills it with data on startup
    }

    def "should return max level"() {
        setup:
            def maxLevel = new PlayerLevel(2, 1000)

            repository.save(new PlayerLevel(1, 100))
            repository.save(maxLevel)
        expect:
            maxLevel == repository.findMaxLevel()
    }

    def "should return closest"() {
        setup:
            def expected = new PlayerLevel(2, 1000)

            repository.save(new PlayerLevel(1, 100))
            repository.save(new PlayerLevel(2, 1000))
            repository.save(new PlayerLevel(3, 10000))
        expect:
            expected == repository.findClosestToExperienceLevel(9999)
    }
}
