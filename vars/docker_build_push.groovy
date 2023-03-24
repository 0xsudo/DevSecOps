def call() {
    retry(count: 3) {
		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
			script {
				if (params.ecr_action == 'create') {
					sh 'docker build buggyapp .'
					sh 'docker tag buggyapp https://636181284446.dkr.ecr.us-east-1.amazonaws.com/buggyapp:latest'
					sh 'docker push https://636181284446.dkr.ecr.us-east-1.amazonaws.com/buggyapp:latest'
				}
			}
		}
	}
}