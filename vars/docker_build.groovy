def call(Map config = [:]) {
    retry(count: 3) {
		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
			script {
				if (params.ecr_action == 'create') {
					docker.build('${config.image}')
				}
			}
		}
	}
}