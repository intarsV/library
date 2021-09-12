node {

    def mvnHome = tool 'maven_3.6.3';

    stage('Checkout') {
        git 'https://github.com/intarsV/library'
    }

//     stage('Compile&Test') {
//         sh "${mvnHome}/bin/mvn clean compile test"
//     }
//
// 	stage('Integration Test') {
//         sh "${mvnHome}/bin/mvn -B -DskipUT=true -DskipIT=false verify"
//     }
//
//     stage('SonarQube analysis') {
//     def scannerHome = tool 'sonar';
//     withSonarQubeEnv('SonarCloud') { // If you have configured more than one global server connection, you can specify its name
//       sh "${scannerHome}/bin/sonar-scanner"
//          }
//     }
//
// 	stage('Build') {
//         sh "${mvnHome}/bin/mvn -B -DskipUT=true -DskipIT=true package"
//     }

     stage('Deploy') {
         sh "docker container stop library || true"
         sh "docker build -f Dockerfile -t library target/"
         sh 'docker images -q -f dangling=true | | xargs --no-run-if-empty docker rm -f | xargs --no-run-if-empty docker rmi -f'
         sh "docker run -d -p 80:8080 --name library --rm library:latest"
    }
}