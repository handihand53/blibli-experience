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
                sh 'docker run -p 8080:8185 blibliexperience'
            }
        }
    }
    post {
        always {
            deleteDir()
        }
    }
}
