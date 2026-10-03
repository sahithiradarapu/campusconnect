pipeline {
  agent any
  environment { IMAGE = "campusconnect:${BUILD_NUMBER}" }
  stages {
    stage('Checkout') {
      steps { git branch: 'main', url: 'https://github.com/<your-team>/campusconnect.git' }
    }
    stage('Build')     { steps { sh 'mvn clean package -DskipTests' } }
    stage('Unit Test') { steps { sh 'mvn test' } }
    stage('Selenium')  { steps { sh 'mvn verify -Pselenium' } }
    stage('Docker Build') {
      // Build inside Minikube's Docker daemon so the cluster can use the image locally
      steps { sh 'eval $(minikube docker-env) && docker build -t $IMAGE .' }
    }
    stage('Deploy') {
      steps {
        sh 'kubectl apply -f k8s/'
        sh 'kubectl set image deployment/campusconnect app=$IMAGE'
        sh 'kubectl rollout status deployment/campusconnect'
      }
    }
  }
  post {
    success { echo 'Pipeline succeeded' }
    failure { echo 'Pipeline failed' }
  }
}
