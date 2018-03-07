package com.wolox;

import com.wolox.docker.DockerConfiguration;
import com.wolox.steps.Steps;

class ProjectConfiguration {
    def environment;
    def services;
    List<Steps> steps = [] as LinkedList ;
    def dockerfile;
    def projectName;
    def buildNumber;
    DockerConfiguration dockerConfiguration;
    def env;
}
