def call() {
    retry(count: 3) {
		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
			script {
				if (params.ecr_action == 'create') {
					img = docker.build('buggyapp')
				}
			}
		}
	}
}