package com.wolox.steps;

class Steps {
    List<Step> steps;

    def getClosure(def dockerImage) {
        return { variables ->
            def links = variables.collect { k, v -> "--link ${v.id}:${k}" }.join(" ")
            dockerImage.inside(links) {
                steps.each { step ->
                    // def key = k.replace("_", " ");
                    // key = key.split(" ").collect { it.capitalize() }.join(" ")
                    stage(step.name) {
                        step.commands.each { command ->
                            sh command
                        }
                    }
                }
            }
        }
    }
}