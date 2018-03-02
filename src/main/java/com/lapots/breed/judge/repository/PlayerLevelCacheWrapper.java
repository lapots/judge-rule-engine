package com.lapots.breed.judge.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Wraps over {@link InMemoryPlayerLevelCache}.
 */
@Component
public class PlayerLevelCacheWrapper {
    private InMemoryPlayerLevelCache levelRepository;

    /**
     * Constructor injection.
     * @param levelRepository level repository
     */
    @Autowired
    public PlayerLevelCacheWrapper(final InMemoryPlayerLevelCache levelRepository) {
        this.levelRepository = levelRepository;
    }

    /**
     * Returns closest to the current exp level.
     * @param exp experience
     * @return closest level
     */
    public int getClosestNextLevel(long exp) {
        return search(exp, levelRepository.getAll().entrySet());
    }

    /**
     * Temporary comment until checkstyle is up.
     * @param exp experience
     * @param entries level entries
     * @return closest level
     */
    // TODO:move everything to SQL storage
    private int search(long exp, final Set<Map.Entry<Integer, Long>> entries) {
        NavigableSet<Map.Entry<Integer, Long>> sortedSet = new TreeSet<>(Comparator.comparing(Map.Entry::getValue));
        sortedSet.addAll(entries);

        Map.Entry<Integer, Long> foundEntry = sortedSet.lower(new AbstractMap.SimpleEntry<>(-1, exp));
        return null != foundEntry ? foundEntry.getKey() : -1;
    }
}
