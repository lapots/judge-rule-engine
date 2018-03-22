package com.lapots.breed.judge.rulebook.core.generator.api

import com.lapots.breed.judge.rulebook.domain.Rule

interface IClassGenerator {
    Class<?> generateRule(Rule rule)
}
