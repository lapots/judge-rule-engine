package com.lapots.breed.judge.service.rule;

import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.RuleBook;
import com.lapots.breed.judge.domain.Player;
import com.lapots.breed.judge.repository.wrapper.api.IPlayerLevelRepository;
import com.lapots.breed.judge.service.rule.api.IRuleExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for rule execution.
 */
@Service
public class RuleExecutionService implements IRuleExecutionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionService.class);

    private RuleBook ruleBook;
    private IPlayerLevelRepository levelRepo;

    /**
     * Constructor injection.
     * @param ruleBook rule book object
     * @param levelRepo level repository object
     */
    @Autowired
    public RuleExecutionService(final RuleBook ruleBook, final IPlayerLevelRepository levelRepo) {
        this.ruleBook = ruleBook;
        this.levelRepo = levelRepo;
    }

    @Override
    public Player levelUpPlayer(Player player) {
        LOGGER.debug("Attempt to level up player: [{}].", player);
        int closestLevel = levelRepo.findClosestLevel(player.getExperience());
        int maxLevel = levelRepo.findMaxLevel();

        NameValueReferableMap<Object> facts = new FactMap<>();
        facts.put(new Fact<>("player", player));
        facts.put(new Fact<>("closestLevel", closestLevel));
        facts.put(new Fact<>("maxLevel", maxLevel));

        LOGGER.debug("Running rule engine.");
        ruleBook.run(facts);
        LOGGER.debug("Finished running rule engine.");
        return player;
    }
}
