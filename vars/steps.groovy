@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def dockerImage) {
    return { variables ->
        List<Step> steps = projectConfig.steps
        def links = variables.collect { k, v -> "--link ${v.id}:${k}" }.join(" ")
        dockerImage.inside(links) {
            steps.each { step ->
                // def key = k.replace("_", " ");
                // key = key.split(" ").collect { it.capitalize() }.join(" ")
                stage(step.name) {
                    step.commands.each { command ->
                        sh command
                    }
                }
            }
        }
    }
}