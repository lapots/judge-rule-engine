package com.lapots.breed.judge.service.api;

import com.lapots.breed.judge.domain.Player;

/**
 * Interface for rule calculation service for player.
 */
public interface IPlayerRuleCalculationService {
    /**
     * Levels up player if needed.
     * @param player player
     * @return leveled up player or player
     */
    Player levelUp(Player player);
}
