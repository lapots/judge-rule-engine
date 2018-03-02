package com.lapots.breed.judge.service;

import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.repository.PlayerLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Service for player rules calculation.
 */
@Component
public class PlayerRuleCalculationService {
    private PlayerLevelRepository levelRepository;

    /**
     * Constructor injection.
     * @param levelRepository level repository
     */
    @Autowired
    public PlayerRuleCalculationService(final PlayerLevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    /**
     * Levels up the player.
     * @param player player
     * @return levelled up player or original
     */
    public Mono<Player> levelUp(final Mono<Player> player) {
        return player.map(p -> {
            if (levelRepository.findClosestToExperienceLevel(p.getExperience()).getLevel() != p.getLevel()
                    && p.getLevel() < levelRepository.findMaxLevel().getLevel()) {
                p.setLevel(p.getLevel() + 1);
            }
            return p;
        });
    }
}
