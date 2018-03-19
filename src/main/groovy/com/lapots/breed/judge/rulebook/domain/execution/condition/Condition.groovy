package com.lapots.breed.judge.rulebook.domain.execution.condition

import groovy.transform.ToString

@ToString
class Condition {
    def id
    def type
    ConditionBlock left, right
}
