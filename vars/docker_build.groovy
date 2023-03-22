def call(Map param = [:]) {
    retry(count: 3) {
		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
			script {
				if (params.ecr_action == 'create') {
					docker_image=docker.build('${param.name}')
				}
			}
		}
	}
}