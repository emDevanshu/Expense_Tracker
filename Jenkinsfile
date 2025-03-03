// This is a JenkinsFile that will be used to build the project

pipeline {
    agent any
    tools {
        maven "mvn"
    }

    stages {
//     Checkout stage will be used to checkout the code from the repository
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'Git token', url: 'https://github.com/emDevanshu/Expense_Tracker.git'
            }
        }
        stage('Build') {
            steps {
                script {
                    sh "mvn clean install"
                }
            }
        }
        stage('Test') {
            steps {
                // Run tests with Maven
                script {
                    sh 'mvn test'
                }
            }
        }
//        stage('Deploy to Tomcat') {
//            steps {
//                deploy adapters: [tomcat9(credentialsId: 'Tomcat_deployer', path: '', url: 'http://localhost:8081/')], contextPath: null, war: '**/*.war'
//            }
//        }
    }

    post {
        success {
            // Actions after the build succeeds
            echo 'Build was successful!'
            echo 'Deployed to Tomcat!'
        }
        failure {
            // Actions after the build fails
            echo 'Build failed. Check logs.'
            echo 'Deployment to Tomcat failed.'
        }
    }
}