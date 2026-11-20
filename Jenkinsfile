pipeline {
    agent any

    tools {
        jdk 'JDK-17'
        maven 'Maven-3.9'
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {

        stage('Checkout Source') {
            steps {
                checkout scm
            }
        }

        stage('Verify Environment') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn -B clean verify'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/calculator.jar',
                                 fingerprint: true
            }
        }
    }

    post {
        success {
            echo "Build ${BUILD_NUMBER} completed successfully"
        }

        failure {
            echo "Build ${BUILD_NUMBER} failed"
        }
    }
}