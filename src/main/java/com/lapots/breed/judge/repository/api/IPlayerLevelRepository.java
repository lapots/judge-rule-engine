package com.lapots.breed.judge.repository.api;

import com.lapots.breed.judge.domain.PlayerLevel;

/**
 * Repository for player levels.
 */
public interface IPlayerLevelRepository {
    /**
     * Returns closest level.
     * @param exp experience
     * @return player level entity
     */
    PlayerLevel findClosestToExperienceLevel(long exp);
    /**
     * Returns max level.
     * @return max level entity
     */
    PlayerLevel findMaxLevel();
    /**
     * Deletes all records.
     */
    void deleteAll();

    /**
     * Saves record to database.
     * @param level level
     */
    void save(PlayerLevel level);
}
