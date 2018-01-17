@Library('wolox-ci')
import com.wolox.parser.ConfigParser;
import com.wolox.*;

def call(String yamlName) {
    def yaml = readYaml file: yamlName;

    ProjectConfiguration projectConfiguration = ConfigParser.parse(yaml);

    def customImage = docker.build("tottus-api:${env.BUILD_ID}");

    def closure = steps(projectConfiguration, customImage);

    projectConfig.services.each {

        closure = "${it.getVar()}"(projectConfiguration, closure);
    }

    closure([:]);
}