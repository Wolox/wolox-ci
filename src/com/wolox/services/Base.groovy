package com.wolox.service;

class Base {

    def getClosure(ProjectConfig ProjectConfig, def nextClosure) {
        return { variables ->

            withEnv(projectConfig.environment) {
                nextClosure(variables)
            }
        }
    }
    
}