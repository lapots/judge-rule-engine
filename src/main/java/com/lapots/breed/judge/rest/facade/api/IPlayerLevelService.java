package com.lapots.breed.judge.rest.facade.api;

import com.lapots.breed.judge.domain.Player;

/**
 * Service for player.
 */
public interface IPlayerLevelService {
    /**
     * Checks player existence and levels up it if needed.
     * @param player player
     * @return leveled up player or the same
     */
    Player levelUp(Player player);
}
