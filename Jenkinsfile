pipeline {
    environment {
        registry = '10.77.18.10:8082'
        registryCredential = 'NEXUS_JENKINS'
    }
    agent {
        docker {
            image '10.77.18.10:8082/odm/su-rules-deploy:jdk-8-alpine' 
            // args '-v /root/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') { 
            steps {
                // sh 'mvn -B -DskipTests clean package' 
                sh 'hostname'
                sh 'pwd'
                sh 'ls -la'
                sh 'mvn clean install'
            }
        }
    }
}