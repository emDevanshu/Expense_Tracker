// This is a JenkinsFile that will be used to build the project

pipeline {
    agent any
    options {
        skipDefaultCheckout()
    }
    tools {
        maven "mvn"
        nodejs "node"
    }

    environment {
        RENDER_API_KEY = credentials('render-api-key')
        RENDER_BACKEND_SERVICE_ID = 'srv-cv2udl2j1k6c739pp0lg'
        RENDER_BACKEND_DEPLOY_HOOK = "https://api.render.com/deploy/${RENDER_BACKEND_SERVICE_ID}?key=HH45VpzmZPA"
        RENDER_FRONTEND_SERVICE_ID = 'srv-d02k9ajuibrs73avrthg'
        RENDER_FRONTEND_DEPLOY_HOOK = "https://api.render.com/deploy/${RENDER_FRONTEND_SERVICE_ID}?key=TbPZe9yi_PI"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: 'Git token', url: 'https://github.com/emDevanshu/Expense_Tracker.git'
            }
        }
        stage('Build') {
            parallel {
                stage('Java') {
                    steps {
                        dir('expense-tracker-service') {
                            sh 'mvn clean install'
                        }
                    }
                }

                stage('Angular') {
                    steps {
                        dir('expense-tracker-ui') {
                            sh 'npm install'
                            sh './node_modules/.bin/ng build --configuration production'
                        }
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'cd expense-tracker-service && mvn test'
                }
            }
        }

        stage('Sonar') {
            steps {
                dir('expense-tracker-service') {
                    withSonarQubeEnv('sonarqube-25.4.0.105899') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }

            post {
                success {
                    script {
                        timeout(time: 1, unit: 'MINUTES') {
                            def qualityGate = waitForQualityGate()
                            if (qualityGate.status != 'OK') {
                                error "SonarQube Quality Gate failed: ${qualityGate.status}"
                            } else {
                                echo "SonarQube analysis passed."
                            }
                        }
                    }
                }
                failure {
                    echo "SonarQube analysis failed during execution."
                }
            }
        }
        stage('Deploy to Render') {
            steps {
                script {

//                    def changedFiles = sh(script: 'git diff --name-only HEAD HEAD~1', returnStdout: true).trim();
//                    echo "Changed files:\n${changedFiles}"
                    def changedFiles = sh(script: 'git diff --name-only HEAD HEAD~1', returnStdout: true).split('\n');
                    echo "Changed files:\n${changedFiles.join('\n')}"

                    def backendChanged = changedFiles.any {
                        it.startsWith("expense-tracker-service/") || it == "Dockerfile" || it == "Jenkinsfile"
                    }

                    def frontendChanged = changedFiles.any {
                        it.startsWith("expense-tracker-ui/") || it == "Dockerfile" || it == "Jenkinsfile"
                    }

                    if(backendChanged) {
                        echo "Changes detected in backend. Deploying backend....."
                        def backendResponse = httpRequest(
                                url: "${RENDER_BACKEND_DEPLOY_HOOK}",
                                httpMode: 'POST',
                                validResponseCodes: '200:299'
                        )
                        echo "Render Backend API Response: ${backendResponse}"
                    } else {
                        echo "No backend changes detected. Skipping backend deployment."
                    }

                    if(frontendChanged) {
                        echo "Changes detected in frontend. Deploying frontend....."
                        def frontendResponse = httpRequest(
                                url: "${RENDER_FRONTEND_DEPLOY_HOOK}",
                                httpMode: 'POST',
                                validResponseCodes: '200:299'
                        )
                        echo "Render Frontend API Response: ${frontendResponse}"
                    } else {
                        echo "No frontend changes detected. Skipping frontend deployment."
                    }
                }
            }
        }
    }

    post {
        success {
            // Actions after the build succeeds
            echo 'Build was successful!'
        }
        failure {
            // Actions after the build fails
            echo 'Build failed. Check logs.'
        }
    }
}
