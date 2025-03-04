// This is a JenkinsFile that will be used to build the project

pipeline {
    agent any
    tools {
        maven "mvn"
    }

    environment {
        RENDER_API_KEY = credentials('render-api-key')
        RENDER_SERVICE_ID = 'srv-cv2udl2j1k6c739pp0lg'
        RENDER_DEPLOY_HOOK = "https://api.render.com/deploy/${RENDER_SERVICE_ID}?key=HH45VpzmZPA"
    }

    stages {
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

        stage('Sonarqube') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy to Render') {
            steps {
                script {
                    // Trigger Render deployment using the deploy hook URL
                    def response = httpRequest(
                            url: "${RENDER_DEPLOY_HOOK}",
                            httpMode: 'POST',
                            validResponseCodes: '200:299'
                    )
                    echo "Render API Response: ${response}"
                }
            }
        }
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