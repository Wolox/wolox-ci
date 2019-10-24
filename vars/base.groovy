@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def _, def nextClosure) {
    return { variables ->
        timeout(time: projectConfig.timeout, unit: 'SECONDS') {
            withCredentials([
              file(credentialsId: 'SPREADSHEET_CLIENT_SECRET', variable: 'spreadsheet_client_secret')]) {
                sh "cp \$client_secret /client_secret.json"
            }

            withEnv(projectConfig.environment << "FOO=BAR") {
                wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
                    nextClosure(variables)
                }
            }
        }
    }
}
