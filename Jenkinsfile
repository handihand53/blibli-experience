pipeline {
    agent {
        label "dockerworker"
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Build image') {
            steps {
                sh 'docker build -t my-app .'
            }
        }
        stage('Run app') {
            steps {
                sh 'docker run my-app -p 8080:8185'
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }
}
