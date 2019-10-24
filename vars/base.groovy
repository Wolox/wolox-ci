@Library('wolox-ci')
import com.wolox.*;

def call(ProjectConfiguration projectConfig, def _, def nextClosure) {
 return {
  variables ->
  timeout(time: projectConfig.timeout, unit: 'SECONDS') {
   withCredentials([string(credentialsId: 'client_secret_json', variable: 'TOKEN')]) {
    withEnv(projectConfig.environment << "SPREADSHEET_CLIENT_SECRET=$TOKEN") {
     wrap([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
      nextClosure(variables)
     }
    }
   }
  }
 }
}
