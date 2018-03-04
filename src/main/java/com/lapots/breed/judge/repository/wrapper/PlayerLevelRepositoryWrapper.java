package com.lapots.breed.judge.repository.wrapper;

import com.lapots.breed.judge.repository.PlayerLevelRepository;
import com.lapots.breed.judge.repository.wrapper.api.IPlayerLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Wraps over repository interface to provide simpler return types.
 */
@Service
public class PlayerLevelRepositoryWrapper implements IPlayerLevelRepository {
    private PlayerLevelRepository levelRepository;

    /**
     * Constructor injection.
     * @param levelRepository level repository
     */
    @Autowired
    public PlayerLevelRepositoryWrapper(final PlayerLevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public int findClosestLevel(long exp) {
        return levelRepository.findClosestToExperienceLevel(exp).get(0).getLevel();
    }

    @Override
    public int findMaxLevel() {
        return levelRepository.findMaxLevel().get(0).getLevel();
    }
}
