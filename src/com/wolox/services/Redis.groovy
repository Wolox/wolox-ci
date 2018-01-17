package com.wolox.service;

class Redis {
    def getClosure(ProjectConfig ProjectConfig, def nextClosure) {
        return { variables ->
            /* Build redis image */
            docker.image('redis').withRun() { redis ->
                withEnv(["REDIS_URL=redis://redis"]) {
                    variables.redis = redis;
                    nextClosure(variables)
                }
            }
        }
    }
}