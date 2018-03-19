package com.lapots.breed.judge.rulebook.domain.execution.condition

enum ConditionType {
    NOT_EQUALS("not_equals"),
    LESS_THAN("less_than")

    def mnemonic
    ConditionType(mnemonic) {
        this.mnemonic = mnemonic
    }
}
