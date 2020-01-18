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

             withSonarQubeEnv('SonarCloud') { // If you have configured more than one global server connection, you can specify its name
               sh "${scannerHome}/bin/sonar-scanner -Dsonar.organizationKey=intarsv -Dsonar.organization=intarsv -Dsonar.host.url=https://sonarcloud.io -Dsonar.projectKey=intarsV_library -Dsonar.login=7c1f871436271d48067e3be36b3e999ebef28ab2 -Dsonar.java.binaries=target/classes -Dsonar.sources=src/main/java"
             }
            }
         }
	}
}