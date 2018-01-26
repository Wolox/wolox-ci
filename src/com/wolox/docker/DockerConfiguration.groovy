package com.wolox.docker;

import com.wolox.ProjectConfiguration;

class DockerConfiguration {

    ProjectConfiguration projectConfiguration;

    def imageName() {
        "${reference()}:${tag()}".toLowerCase();
    }

    def baseName() {
        "${projectConfiguration.projectName}".toLowerCase();
    }

    def  reference() {
        def env = projectConfiguration.env;
        "${baseName()}-${env.BRANCH_NAME}".toLowerCase();
    }

    def  tag() {
        def env = projectConfiguration.env;
        "${env.BUILD_ID}".toLowerCase();
    }
}
