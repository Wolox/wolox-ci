@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        /* Build postgres image with postgis support */
        docker.image("mdillon/postgis:${version}").withRun() { db ->
            withEnv(['DB_USERNAME=postgres', 'DB_PASSWORD=', "DB_HOST=db", "DB_PORT=5432"]) {
                variables.db = db;
                nextClosure(variables)
            }
        }
    }
}
