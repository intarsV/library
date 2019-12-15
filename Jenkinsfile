pipeline {
    agent any

     tools {
          // Install the Maven version configured as "M3" and add it to the path.
          maven "maven_3.6.3"
     }

     stages {
         stage('Compile') {
             steps {
                 sh 'mvn clean compile'
             }
         }
         stage('Testing'){
             steps {
                 sh 'mvn test'
             }
         }
         stage('Deploy'){
             steps {
                  sh 'mvn clean package'
             }
             post {// If Maven was able to run the tests, even if some of the test
                   // failed, record the test results and archive the jar file.
                  success {
                        junit '**/target/surefire-reports/TEST-*.xml'
                        archiveArtifacts 'target/*.jar'
                  }
             }
         }
         stage('SonarQube analysis') {
             def scannerHome = tool 'SonarScanner 4.0';
             withSonarQubeEnv() { // If you have configured more than one global server connection, you can specify its name
               sh "${scannerHome}/bin/sonar-scanner"
             }
           }
     }
}
