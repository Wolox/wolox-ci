@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def _, def nextClosure) {
    return { variables ->
        podTemplate(label: 'base', containers: [
            containerTemplate(
                name: 'base',
                image: projectConfig.baseImage)
        ],
        volumes: [
            hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
        ]) {
            timeout(time: projectConfig.timeout, unit: 'SECONDS') {
                withEnv(projectConfig.environment) {
                    wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
                        nextClosure(variables)
                    }
                }
            }
        }
    }
}
