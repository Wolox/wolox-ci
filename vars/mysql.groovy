@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        def dbPassword = 'my-secret-pw'
        /* Build mysql image */
        docker.image("mysql:${version}").withRun("-e \"MYSQL_ROOT_PASSWORD=${dbPassword}\"") { db ->
            withEnv(['DB_USERNAME=root', "DB_PASSWORD=${dbPassword}", "DB_HOST=db", "DB_PORT=3306"]) {
                variables.db = db;
                nextClosure(variables)
            }
        }
    }
}
