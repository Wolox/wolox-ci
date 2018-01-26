@Library('wolox-ci')
import com.wolox.parser.ConfigParser;
import com.wolox.*;

def call(String yamlName) {
    def yaml = readYaml file: yamlName;

    def buildNumber = Integer.parseInt(env.BUILD_ID)

    // load project's configuration
    ProjectConfiguration projectConfig = ConfigParser.parse(yaml, buildNumber);

    // build the image specified in the configuration
    def customImage = docker.build("${projectConfig.projectName}:${env.BUILD_ID}", "--file ${projectConfig.dockerfile} .");

    // adds the last step of the build.
    def closure = buildSteps(projectConfig, customImage);

    // each service is a closure that when called it executes its logic and then calls a closure, the next step.
    projectConfig.services.each {

        closure = "${it.getVar()}"(projectConfig, closure);
    }

    // we execute the top level closure so that the cascade starts.
    try {
        closure([:]);
    } finally{
        try {
            def firstImage = sh(
                script: "docker images --filter 'reference=${projectConfig.projectName}:*' --format \"{{.Tag}}\" | sort -n | head -1",
                returnStdout: true
            );
            firstImage = Integer.parseInt(firstImage.trim());
            println firstImage
            for(int i = firstImage; i < buildNumber; i++) {
                try {
                    sh "docker images --filter 'reference=${projectConfig.projectName}:${i}' -q | xargs --no-run-if-empty docker rmi -f"
                } catch(ignored) {
                    println ignored
                }
            }
        } catch(ignored) {
            println ignored
            //we don't fail for this exception
        }
    }
}
