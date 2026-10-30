pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile Java') {
            steps {
                sh 'javac Calculator.java'
            }
        }

        stage('List Files') {
            steps {
                sh 'ls -la'
            }
        }
    }
}