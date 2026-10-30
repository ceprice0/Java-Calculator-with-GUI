pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Downloading source code...'
                sleep 5
                checkout scm
            }
        }

        stage('Clean') {
            steps {
                echo 'Cleaning old build files...'
                sleep 5
                sh 'rm -f *.class *.jar || true'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling Java application...'
                sleep 10
                sh 'javac Calculator.java'
            }
        }

        stage('Package') {
            steps {
                echo 'Creating JAR file...'
                sleep 10
                sh 'jar cf CalculatorBuild.jar *.class'
            }
        }

        stage('Verify') {
            steps {
                echo 'Verifying build output...'
                sleep 5
                sh 'ls -la'
            }
        }
    }
}