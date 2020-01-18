pipeline {
    agent any



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
                  sh 'mvn clean package -DskipUT=true -DskipIT=true'
             }
         }
         stage('QualityGate'){
             steps{
             withSonarQubeEnv('SonarCloud') { // If you have configured more than one global server connection, you can specify its name
               sh "sonar -Dsonar.organizationKey=intarsv -Dsonar.organization=intarsv -Dsonar.host.url=https://sonarcloud.io -Dsonar.projectKey=intarsV_library -Dsonar.login=7c1f871436271d48067e3be36b3e999ebef28ab2 -Dsonar.java.binaries=target/classes -Dsonar.sources=src/main/java -Dsonar.testExecutionReportPaths=coverage/sonar-cloud-reporter.xml"
             }
            }
         }
	}
}