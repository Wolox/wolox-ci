@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def _, def nextClosure) {
    return { variables ->
        timeout(time: projectConfig.timeout, unit: 'SECONDS') {
            withCredentials([file(credentialsId: 'client_secret_json', variable: 'FILE')]){
                cp "\$FILE /home/node/project-health-checker"
                withEnv(projectConfig.environment) {
                    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
                        nextClosure(variables)
                    }
                }
            }
        }
    }
}
