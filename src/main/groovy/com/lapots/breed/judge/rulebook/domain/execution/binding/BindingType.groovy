package com.lapots.breed.judge.rulebook.domain.execution.binding

enum BindingType {
    AND("and")

    def mnemonic
    BindingType(mnemonic) {
        this.mnemonic = mnemonic
    }
}
