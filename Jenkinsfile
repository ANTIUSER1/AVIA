pipeline {
    environment {
        registry = '10.77.18.10:8082'
        registryCredential = 'NEXUS_JENKINS'
        ODM_HOST = '172.20.207.70'
        ODM_PORT = '9090'
        ODM_USER = 'resAdmin'
        ODM_PASSWORD = 'resAdmin'
    }
    agent {
        docker {
            image '10.77.18.10:8082/odm/su-rules-deploy:jdk-8-alpine' 
            args '-u root:root'
        }
    }
    options {
        timestamps()
    }
    post {
        failure {
            updateGitlabCommitStatus name: 'build', state: 'failed'
        }
        success {
            updateGitlabCommitStatus name: 'build', state: 'success'
        }
    }
    stages {
        stage('Build') {
            steps {
                echo 'Current environment:'
                sh 'env'
                sh 'mvn -B -DskipTests clean package' 
                sh 'ant -f deploy-test.xml DeployRulesArchive'
            }
        }
    }
}