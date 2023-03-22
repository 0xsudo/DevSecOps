def call(Map param = [:]) {
    retry(count: 3) {
		if (params.ecr_action == 'create') {
			docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:${param.region}:${param.iamrole}') {
				docker_image.push('${param.tag}')
			}
		}
	}
}