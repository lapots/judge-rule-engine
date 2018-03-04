package com.lapots.breed.judge.service.rule.api;

import com.lapots.breed.judge.domain.Player;

/**
 * Main interface for rule execution service.
 */
public interface IRuleExecutionService {
    /**
     * Levels up player if possible.
     * @param player player
     * @return leveled up player or the original
     */
    Player levelUpPlayer(Player player);
}
