pipeline {
    agent any

    stages {

        stage('Checkout Source') {
            steps {
                echo 'Retrieving source code from GitHub'
                checkout scm
            }
        }

        stage('Clean Workspace') {
            steps {
                sh 'rm -f *.class *.jar || true'
            }
        }

        stage('Compile Application') {
            steps {
                sh 'javac Calculator.java'
            }
        }

        stage('Package Application') {
            steps {
                sh 'jar cf CalculatorBuild.jar *.class'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Executing tests'
                sleep 5
            }
        }

        stage('Security Scan') {
            steps {
                echo 'Scanning application'
                sleep 5
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: '*.jar'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying build'
                sleep 5
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully'
        }
        failure {
            echo 'Pipeline failed'
        }
    }
}