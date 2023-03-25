def call() {
    retry(count: 3) {
		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
			script {
				if (params.ecr_action == 'create') {
					sh 'docker build -t buggyapp .'
					sh 'docker tag buggyapp 636181284446.dkr.ecr.us-east-1.amazonaws.com/buggyapp:latest'
					docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
						sh 'docker push 636181284446.dkr.ecr.us-east-1.amazonaws.com/buggyapp:latest'
					}
				}
			}
		}
	}
}