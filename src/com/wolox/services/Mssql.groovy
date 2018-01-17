package com.wolox.services;

class Mssql {
    def getClosure(ProjectConfig ProjectConfig, def nextClosure) {
        return { variables ->
            def dbPassword = 'someReallyStrongPwd123'
            /* Build mssql image */
            docker.image('microsoft/mssql-server-linux').withRun("-e \"ACCEPT_EULA=Y\" -e \"SA_PASSWORD=${dbPassword}\"") { db ->
                withEnv(['DB_USERNAME=sa', "DB_PASSWORD=${dbPassword}", "DB_1_PORT_1433_TCP_ADDR=db"]) {
                    variables.db = db;
                    nextClosure(variables)
                }
            }
        }
    }
}