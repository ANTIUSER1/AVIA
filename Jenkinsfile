pipeline {
    environment {
        registry = '10.77.18.10:8082'
        registryCredential = 'NEXUS_JENKINS'
    }
    agent {
        docker {
            hostname '10.77.18.10:8082'
            image 'odm/su-rules-deploy:jdk-8-alpine' 
            args '-v /root/.m2:/root/.m2'
            pullCredentialsId 'NEXUS_JENKINS'
        }
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}