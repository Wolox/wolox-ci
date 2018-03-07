import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        /* Build redis image */
        docker.image("redis:${version}").withRun() { redis ->
            withEnv(["REDIS_URL=redis://redis"]) {
                variables.redis = redis;
                nextClosure(variables)
            }
        }
    }
}
