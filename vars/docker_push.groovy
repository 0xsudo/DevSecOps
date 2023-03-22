def call(Map config = [:]) {
    retry(count: 3) {
		if (params.ecr_action == 'create') {
			docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
				${config.image}.push('${config.tag}')
			}
		}
	}
}