def call(Map config = [:]) {
    retry(count: 3) {
		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
			script {
				if (params.ecr_action == 'create') {
					img = docker.build(${config.name})
					docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
						img.push('${config.tag}')
					}
				}
			}
		}
	}
}