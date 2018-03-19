package com.lapots.breed.judge.rulebook.domain.execution.binding

import groovy.transform.ToString

@ToString
class Binding {
    def type
    // list of condition ids
    def conditions = []
}
