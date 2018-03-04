package com.lapots.breed.judge.repository.wrapper.api;

/**
 * Interface for wrapping over repository API.
 */
public interface IPlayerLevelRepository {
    /**
     * Returns closest level to experience.
     * @param exp exp
     * @return closest level
     */
    int findClosestLevel(long exp);

    /**
     * Returns max level in database.
     * @return max level
     */
    int findMaxLevel();
}
