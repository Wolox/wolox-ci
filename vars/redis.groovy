@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        /* Build redis image */
        podTemplate(label: 'redis', containers: [
            containerTemplate(
                name: 'postgres',
                image: "redis:${version}")
        ],
        volumes: [
            hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
        ]) {
            container("redis").withRun() { redis ->
                withEnv(["REDIS_URL=redis://redis"]) {
                    variables.redis = redis;
                    nextClosure(variables)
                }
            }
        }
    }
}
