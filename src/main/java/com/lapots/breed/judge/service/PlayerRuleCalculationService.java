package com.lapots.breed.judge.service;

import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.service.rule.api.IRuleExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for player rules calculation.
 */
// TODO:create interface
@Service
public class PlayerRuleCalculationService {
    private IRuleExecutionService ruleExecutionService;

    /**
     * Constructor injection.
     * @param ruleExecutionService rule execution service
     */
    @Autowired
    public PlayerRuleCalculationService(final IRuleExecutionService ruleExecutionService) {
        this.ruleExecutionService = ruleExecutionService;
    }

    /**
     * Levels up the player.
     * @param player player
     * @return levelled up player or original
     */
    public Player levelUp(Player player) {
        return ruleExecutionService.levelUpPlayer(player);
    }
}
