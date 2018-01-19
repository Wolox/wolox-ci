@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def nextClosure) {
    return { variables ->
        /* Build postgres image */
        docker.image('postgres').withRun() { db ->
            withEnv(['DB_USERNAME=postgres', 'DB_PASSWORD=', "DB_1_PORT_5432_TCP_ADDR=db"]) {
                variables.db = db;
                nextClosure(variables)
            }
        }
    }
}
