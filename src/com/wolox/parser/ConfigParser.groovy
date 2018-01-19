package com.wolox.parser;

import com.wolox.ProjectConfiguration;
import com.wolox.services.*;
import com.wolox.steps.*;

class ConfigParser {

    static ProjectConfiguration parse(def yaml) {
        ProjectConfiguration projectConfiguration = new ProjectConfiguration();

        projectConfiguration.environment    = parseEnvironment(yaml.environment);
        projectConfiguration.steps          = parseSteps(yaml.steps);
        projectConfiguration.services   = parseServices(yaml.services);
        projectConfiguration.dockerfile = parseDockerfile(yaml.config);
        projectConfiguration.projectName = parseProjectName(yaml.config);

        return projectConfiguration;
    }

    static def parseEnvironment(def environment) {
        if (!environment) {
            return "";
        }

        return environment.collect { k, v -> "${k}=${v}"};
    }

    static def parseSteps(def yamlSteps) {
        List<Step> steps = yamlSteps.collect { k, v ->
            Step step = new Step(name: k)

            v.each {
                step.commands.add(it);
            }
            return step
        }
        return new Steps(steps: steps);
    }

    static def parseServices(def steps) {
        def services = [];

        steps.each {
            def instance = getServiceClass(it.capitalize())?.newInstance()
            services.add(instance)
        };

        services.add(new Base());

        return services
    }

    static def getServiceClass(def name) {
        switch(name) {
            case "Postgres":
                return Postgres
                break
            case "Redis":
                return Redis
                break
            case "Mssql":
                return Mssql
                break
        }
    }

    static def parseDockerfile(def config) {
        if (!config || !config["dockerfile"]) {
            return "Dockerfile";
        }

        return config["dockerfile"];
    }

    static def parseProjectName(def config) {
        if (!config || !config["project_name"]) {
            return "woloxci-project";
        }

        return config["project_name"];
    }
}
