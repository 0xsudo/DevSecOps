def call(Map config = [:]) {
    retry(count: 3) {
		if (params.ecr_action == 'create') {
			docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:${config.region}:${config.iamrole}') {
				docker_image.push('${config.tag}')
			}
		}
	}
}