pipeline {
    agent any

     tools {
          // Install the Maven version configured as "M3" and add it to the path.
          maven "maven_3.6.3"
    }

     stages {
         stage('Compile & test') {
             steps {
                 sh 'mvn clean compile test'
             }
         }
         stage('Integration tests'){
                      steps {
                          sh 'mvn verify -DskipUT=true -DskipIT=false'
                      }
                  }
         stage('Deploy'){
             steps {
                  sh 'mvn clean package'
             }
         }
	}
}