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
         stage('Integration tests'){
                      steps {
                          sh 'mvn test -DskipUT=true -DskipIT=false'
                      }
                  }
         stage('Deploy'){
             steps {
                  sh 'mvn clean package'
             }
         }
	}
}