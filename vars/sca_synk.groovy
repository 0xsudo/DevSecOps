def call() {
    retry(count: 3) {
		if (params.ecr_action == 'create') {
			withCredentials([string(credentialsId: 'SNYK_TOKEN', variable: 'SNYK_TOKEN')]) {
				sh 'mvn snyk:test -fn'
			}
		}
	}
}