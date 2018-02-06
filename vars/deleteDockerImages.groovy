@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig) {
    def reference = projectConfig.dockerConfiguration.reference();
    try {
        sh "docker images --filter 'reference=${reference}*' --format \"{{.Tag}} {{.ID}}\" | sort -n | sed '\$d' | awk '{ print \$2 }' | xargs --no-run-if-empty docker rmi -f"

    } catch(ignored) {
        // this would make the entire popeline fail. We don't want that
        println ignored
    }
}
