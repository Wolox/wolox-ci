@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def _, def nextClosure) {
    return { variables ->
        timeout(time: projectConfig.timeout, unit: 'SECONDS') {
            withCredentials([string(credentialsId: 'myToken', variable: 'TOKEN')]){
                withEnv(projectConfig.environment) {
                    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
                        nextClosure(variables)
                    }
                }
            }
        }
    }
}
