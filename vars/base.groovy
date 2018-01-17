@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def nextClosure) {
    return { variables ->

        withEnv(projectConfig.environment) {
            nextClosure(variables)
        }
    }
}