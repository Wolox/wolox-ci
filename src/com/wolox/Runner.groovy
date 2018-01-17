package com.wolox;

import ProjectConfiguration;

class Runner {

    static void run(def yamlName) {
        def yaml = readYaml file: yamlName;

        def customImage = docker.build("tottus-api:${env.BUILD_ID}");

        ProjectConfiguration projectConfiguration = ConfigParser.parse(yaml);

        def closure = projectConfiguration.steps.getClosure(customImage);

        projectConfig.services.each {
            closure = it.getClosure(projectConfiguration, closure);
        }

        closure([:]);
    }
}