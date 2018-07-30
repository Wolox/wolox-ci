@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        def dbUser = 'mongoadmin'
        def dbPassword = 'my-secret-pw'
        /* Build mssql image */
        docker.image("mongo:${version}").withRun("-e \"MONGO_INITDB_ROOT_USERNAME=${dbUser}\" -e \"MONGO_INITDB_ROOT_PASSWORD=${dbPassword}\"") { db ->
            withEnv(["DB_USERNAME=${dbUser}", "DB_PASSWORD=${dbPassword}", "DB_HOST=db", "DB_PORT=27017"]) {
                variables.db = db;
                nextClosure(variables)
            }
        }
    }
}
