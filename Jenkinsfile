pipeline {
    agent any

    environment {
        scannerHome = tool 'SonarCloud';
    }

     tools {
          // Install the Maven version configured as "M3" and add it to the path.
          maven "maven_3.6.3"
    }

     stages {
         stage('Deploy'){
             steps {
                  sh 'mvn clean package -DskipUT=true -DskipIT=true'
             }
         }
         stage('QualityGate'){
             steps{
                  sh 'mvn clean verify -P sonar'
            }
         }
	}
}