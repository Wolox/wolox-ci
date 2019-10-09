@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def version, def nextClosure) {
    return { variables ->
        /* Build postgres image */
        podTemplate(label: 'somelabel', containers: [
            containerTemplate(
                name: 'postgres',
                image: "postgres:${version}",
                envVars: [
                    envVar(key: 'DB_USERNAME', value: 'postgres'),
                    envVar(key: 'DB_PASSWORD', value: ''),
                    envVar(key: 'DB_HOST', value: 'localhost'),
                    envVar(key: 'DB_PORT', value: '5432')
                ])
        ],
        volumes: [
            hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
        ]) {
            withEnv(['DB_USERNAME=postgres', 'DB_PASSWORD=', "DB_HOST=localhost", "DB_PORT=5432"]) {
                nextClosure(variables)
            }
        }
    }
}
