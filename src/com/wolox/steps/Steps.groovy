package com.wolox.steps;

class Steps {
    List<Step> steps;
    String context;

    def getVar(def dockerImage) {
        return "buildSteps"
    }
}
