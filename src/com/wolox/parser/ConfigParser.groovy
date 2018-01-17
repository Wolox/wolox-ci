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
        }
        return new Steps(steps: steps);
    }

    static def parseServices(def steps) {
        def services = [];

        steps.each {
            def instance = Eval.me("new ${it.capitalize()}()")
            services.add(instance)
        };

        services.add(new Base());

        return services
    }
}
