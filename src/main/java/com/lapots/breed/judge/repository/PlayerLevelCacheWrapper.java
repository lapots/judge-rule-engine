package com.lapots.breed.judge.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
        // sort it
        List<Map.Entry<Integer, Long>> entryList = new ArrayList<>(entries);
        entryList.sort(Comparator.comparing(Map.Entry::getValue));
        List<Long> exps = entryList.stream().map(Map.Entry::getValue).collect(Collectors.toList());

        // do the search
        if (exp < exps.get(0)) {
            return entryList.get(0).getKey();
        }
        if (exp > exps.get(exps.size() - 1)) {
            return entryList.get(exps.size() - 1).getKey();
        }

        int lo = 0;
        int hi = exps.size() - 1;
        while (lo <= hi) {
            int mid = (hi + lo) / 2;
            if (exp < exps.get(mid)) {
                hi = mid - 1;
            } else if (exp > exps.get(mid)) {
                lo = mid + 1;
            } else {
                return entryList.get(mid).getKey();
            }
        }

        return (exps.get(lo) - exp) < (exp - exps.get(hi)) ? entryList.get(lo).getKey() : entryList.get(hi).getKey();
    }
}
