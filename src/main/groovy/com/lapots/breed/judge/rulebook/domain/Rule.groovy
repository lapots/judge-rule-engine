package com.lapots.breed.judge.rulebook.domain

import com.lapots.breed.judge.rulebook.domain.execution.Execution
import groovy.transform.ToString

@ToString
class Rule {
    def name
    def inputs = []
    Execution execution
    def outputs = []
}
