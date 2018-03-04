package com.lapots.breed.judge.domain.rule;

import com.deliveredtechnologies.rulebook.annotation.*;
import com.lapots.breed.judge.domain.Player;

/**
 * Level up rule.
 */
@Rule(name = "level up rule")
public class LevelUpRule {
    @Given("player")
    @Result
    private Player player;

    @Given("closestLevel")
    private int closestLevel;

    @Given("maxLevel")
    private int maxLevel;

    /**
     * When part of rule.
     * @return when
     */
    @When
    public boolean when() {
        return player.getLevel() != closestLevel && player.getLevel() < maxLevel;
    }

    /**
     * Then part of rule.
     */
    @Then
    public void then() {
        player.setLevel(player.getLevel() + 1);
    }
}
