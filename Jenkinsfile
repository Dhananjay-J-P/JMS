pipeline {
  agent any
  stages {
    stage('Cleanup') {
      steps {
        echo "${params.MANAGER_EMAIL_ID} World!"
      }
    }
  }
  parameters {
    string(defaultValue: 'dhananjay.patade@skillnetinc.com', description: 'hi how are u', name: 'MANAGER_EMAIL_ID')
  }
}