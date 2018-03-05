@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def _, def nextClosure) {
    return { variables ->

        withEnv(projectConfig.environment) {
            nextClosure(variables)
        }
    }
}
