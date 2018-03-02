package com.lapots.breed.judge.service;

import com.lapots.breed.judge.repository.PlayerLevelCacheWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
     * Gets closest level to the current experience.
     * @param exp exp
     * @return closest level
     */
    public int calculateLevelByExperience(long exp) {
        return levelCache.getClosestNextLevel(exp);
    }

}
