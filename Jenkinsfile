// This is a JenkinsFile that will be used to build the project

pipeline {
    agent any
    tools {
        maven "mvn"
    }

    environment {
        // Set the Render API Key as an environment variable for secure authentication
        RENDER_API_KEY = credentials('render-api-key') // Replace with your Jenkins credentials ID
        RENDER_SERVICE_ID = 'srv-cv2udl2j1k6c739pp0lg' // Replace with your Render service ID
//        GITHUB_REPO = 'https://github.com/emDevanshu/Expense_Tracker.git' // Replace with your GitHub repository URL
//        GITHUB_TOKEN = credentials('Git token') // Replace with your GitHub token credential ID
        RENDER_DEPLOY_HOOK = "https://api.render.com/deploy/${RENDER_SERVICE_ID}?key=HH45VpzmZPA" // Replace with your actual Render deploy hook URL
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

        stage('Deploy to Render') {
            steps {
                script {
                    // Trigger Render deployment using the deploy hook URL
                    def response = httpRequest(
                            url: "${RENDER_DEPLOY_HOOK}",
                            httpMode: 'POST',
                            validResponseCodes: '200:299' // Ensure only valid responses trigger success
                    )

                    // Print the API response (for debugging)
                    echo "Render API Response: ${response}"
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