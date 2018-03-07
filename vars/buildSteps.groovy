import com.wolox.*;
import com.wolox.steps.Step;

def call(List<Step> steps, def dockerImage) {
    return { variables ->
        
        def links = variables.collect { k, v -> "--link ${v.id}:${k}" }.join(" ")
        dockerImage.inside(links) {
            steps.each { step ->
                stage(step.name) {
                    step.commands.each { command ->
                        sh command
                    }
                }
            }
        }
    }
}
