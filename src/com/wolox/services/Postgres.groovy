package com.wolox.services;

class Postgres {
    
    def getClosure(ProjectConfig ProjectConfig, def nextClosure) {
        return { variables ->
            /* Build postgres image */
            docker.image('postgres').withRun() { db ->
                withEnv(['DB_USERNAME=postgres', 'DB_PASSWORD=', "DB_1_PORT_5432_TCP_ADDR=${dbHost}"]) {
                    variables.db = db;
                    nextClosure()
                }
            }
        }
    }
}