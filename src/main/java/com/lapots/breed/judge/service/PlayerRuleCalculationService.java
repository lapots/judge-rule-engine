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

    /*
        The idea behind it is this one:
            if player.exp >= level.exp && player.level != level.exp
                player.level ++
                player.exp = level.exp - player.exp
     */

    /**
     * Levels up player.
     * @param exp exp
     * @param currentLevel current level
     * @return current level or new one
     */
    public int levelUp(long exp, int currentLevel) {
        // stub implementation
        return levelCache.getClosestNextLevel(exp) != currentLevel ? ++currentLevel : currentLevel;
    }
}
