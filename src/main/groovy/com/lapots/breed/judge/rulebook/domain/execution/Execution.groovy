package com.lapots.breed.judge.rulebook.domain.execution

import groovy.transform.ToString

@ToString
class Execution {
    ThenBlock then
    WhenBlock when
    def bindings = []
}
