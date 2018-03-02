package com.lapots.breed.judge.service;

import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.repository.PlayerLevelCacheWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Service for player rules calculation.
 */
@Component
public class PlayerRuleCalculationService {

    private PlayerLevelCacheWrapper levelCache;

    /**
     * Constructor injection.
     * @param wrapper cache wrapper
     */
    @Autowired
    public PlayerRuleCalculationService(final PlayerLevelCacheWrapper wrapper) {
        this.levelCache = wrapper;
    }

    /**
     * Levels up the player.
     * @param player player
     * @return levelled up player or original
     */
    public Mono<Player> levelUp(final Mono<Player> player) {
        return player.map(p -> {
            if (levelCache.getClosestNextLevel(p.getExperience()) != p.getLevel()
                    && p.getLevel() < levelCache.getMaxLevel()) {
                p.setLevel(p.getLevel() + 1);
            }
            return p;
        });
    }
}
