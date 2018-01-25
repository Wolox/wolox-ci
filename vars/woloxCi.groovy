@Library('wolox-ci')
import com.wolox.parser.ConfigParser;
import com.wolox.*;

def call(String yamlName) {
    def yaml = readYaml file: yamlName;

    // load project's configuration
    ProjectConfiguration projectConfig = ConfigParser.parse(yaml);

    // build the image specified in the configuration
    def customImage = docker.build("${projectConfig.projectName}:${env.BUILD_ID}", "--file ${projectConfig.dockerfile} .");

    // adds the last step of the build.
    def closure = buildSteps(projectConfig, customImage);

    // each service is a closure that when called it executes its logic and then calls a closure, the next step.
    projectConfig.services.each {

        closure = "${it.getVar()}"(projectConfig, closure);
    }

    // we execute the top level closure so that the cascade starts.
    closure([:]);
}
