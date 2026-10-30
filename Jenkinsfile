pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                sh 'rm -f *.class'
            }
        }

        stage('Compile') {
            steps {
                sh 'javac Calculator.java'
            }
        }

        stage('Verify') {
            steps {
                sh 'ls -la'
            }
        }

        stage('Package') {
            steps {
                sh 'jar cf CalculatorBuild.jar *.class'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: '*.jar'
            }
        }
    }
}