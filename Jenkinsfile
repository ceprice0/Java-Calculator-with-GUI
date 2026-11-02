pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    environment {
        IMAGE_NAME = 'calculator'
        IMAGE_TAG  = "${env.BUILD_NUMBER}"
    }

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Checkout Source') {
            steps {
                echo 'Retrieving source code from GitHub'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -B -DskipTests package'
                archiveArtifacts artifacts: 'target/calculator.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG -t $IMAGE_NAME:latest .'
            }
        }

        stage('Deploy (headless run)') {
            steps {
                echo 'Running the packaged calculator in headless mode as a smoke test'
                sh 'docker run --rm $IMAGE_NAME:$IMAGE_TAG 6 / 3'
            }
        }
    }

    post {
        success {
            echo "Build ${env.BUILD_NUMBER} completed successfully"
        }
        failure {
            echo "Build ${env.BUILD_NUMBER} failed - check the test reports"
        }
    }
}
