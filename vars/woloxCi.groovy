@Library('wolox-ci')
import com.wolox.parser.ConfigParser;
import com.wolox.*;

def call(String yamlName) {
    def yaml = readYaml file: yamlName;

    ProjectConfiguration projectConfig = ConfigParser.parse(yaml);

    def customImage = docker.build("${projectConfig.projectName}:${env.BUILD_ID}", "--file ${projectConfig.dockerfile} .");

    def closure = buildSteps(projectConfig, customImage);

    projectConfig.services.each {

        closure = "${it.getVar()}"(projectConfig, closure);
    }

    closure([:]);
}
