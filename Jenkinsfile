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
                script{
                    checkout([$class: 'GitSCM', branches: [[name: 'main']],
                              doGenerateSubmoduleConfigurations: false,
                              extensions: [[$class: 'CleanCheckout']],
                              submoduleCfg: [],
                              userRemoteConfigs: [[credentialsId: 'Git token', url: 'https://github.com/emDevanshu/Expense_Tracker.git']]
                    ])
                }
//                git branch: 'main', credentialsId: 'Git token', url: 'https://github.com/emDevanshu/Expense_Tracker.git'
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
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Sonar') {
            steps {
                withSonarQubeEnv('sonarqube-25.2.0') {
                    sh 'mvn sonar:sonar'
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
        }
        failure {
            // Actions after the build fails
            echo 'Build failed. Check logs.'
        }
    }
}