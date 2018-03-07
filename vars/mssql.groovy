import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        def dbPassword = 'someReallyStrongPwd123'
        /* Build mssql image */
        docker.image("microsoft/mssql-server-linux:${version}").withRun("-e \"ACCEPT_EULA=Y\" -e \"SA_PASSWORD=${dbPassword}\"") { db ->
            withEnv(['DB_USERNAME=sa', "DB_PASSWORD=${dbPassword}", "DB_HOST=db", "DB_PORT=1433"]) {
                variables.db = db;
                nextClosure(variables)
            }
        }
    }
}
