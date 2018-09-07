@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        /* Build elasticsearch image */
        docker.image("docker.elastic.co/elasticsearch/elasticsearch:${version}").withRun("-p 9200:9200 -p 9300:9300 -e \"discovery.type=single-node\"") { elasticsearch ->
            withEnv(["ELASTICSEARCH_URL=http://elasticsearch:9200"]) {
                variables.elasticsearch = elasticsearch;
                nextClosure(variables)
            }
        }
    }
}
