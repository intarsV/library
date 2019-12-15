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
                   sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar'
         }
     }
}
