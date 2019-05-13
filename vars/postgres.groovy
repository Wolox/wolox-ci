@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        /* Build postgres image */
        docker.image("postgres:${version}").withRun() { db ->
            withEnv(['DB_USERNAME=postgres', 'DB_PASSWORD=', "DB_HOST=localhost", "DB_PORT=5432"]) {
                variables.db = db;
                sh 'sleep 45'
                nextClosure(variables)
            }
        }
    }
}
