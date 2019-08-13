pipeline {
    environment {
        registry = '10.77.18.10:8082'
        registryCredential = 'NEXUS_JENKINS'
    }
    agent {
        docker {
            image '10.77.18.10:8082/odm/su-rules-deploy:jdk-8-alpine' 
            args '-w /usr/src'
        }
    }
    stages {
        stage('Build') { 
            steps {
                // sh 'mvn -B -DskipTests clean package' 
                sh 'hostname && uname -a'
                sh 'pwd'
                sh 'ls -la'
                sh 'ls -la /tmp/'
                sh 'echo $USER'
                sh 'env'
                // sh 'ls -la ~/.m2/repository/com/ibm/rules'
                sh 'mvn clean install'
            }
        }
    }
}