pipeline {
  agent any
  stages {
    properties([parameters([string(defaultValue: 'dhananjay.patade@skillnetinc.com', description: '', name: 'MANAGER_EMAIL_ID')]), pipelineTriggers([])])
    stage('Cleanup') {
      steps {
        bat 'echo \'DJ\''
      }
    }
  }
}
