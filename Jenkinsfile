
pipeline {
  
  
  agent any
  parameters{
    string(defaultValue: 'dhananjay.patade@skillnetinc.com', description: 'hi how are u', name: 'MANAGER_EMAIL_ID')
      }
  stages {
    
    stage('Cleanup') {
      steps {
       echo "${params.MANAGER_EMAIL_ID} World!"
      }
    }
  }
}
