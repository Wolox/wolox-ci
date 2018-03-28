@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def _, def nextClosure) {
    return { variables ->
        def timeout = projectConfig.environment.TIMEOUT ?: 600 // timeout 10 minutes
        timeout(time: timeout, unit: 'SECONDS') {
            withEnv(projectConfig.environment) {
                wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
                    nextClosure(variables)
                }
            }
        }
    }
}
