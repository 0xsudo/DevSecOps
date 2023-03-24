def call() {
    retry(count: 3) {
		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
			script {
				if (params.ecr_action == 'create') {
					img = docker.build('buggyapp')
					docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
						img.push('latest')
					}
				}
			}
		}
	}
}