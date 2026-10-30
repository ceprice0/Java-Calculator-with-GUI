pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Downloading source code...'
                sleep 15
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                echo 'Cleaning workspace...'
                sleep 15
                sh 'rm -f *.class *.jar || true'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling Calculator.java...'
                sleep 20
                sh 'javac Calculator.java'
            }
        }

        stage('Package') {
            steps {
                echo 'Building JAR file...'
                sleep 20
                sh 'jar cf CalculatorBuild.jar *.class'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sleep 15
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                sleep 15
            }
        }
    }
}