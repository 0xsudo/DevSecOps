def call(Map config = [:]) {
    retry(count: 3) {
		if (params.ecr_action == 'create') {
			withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
				sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=${config.projectkey} -Dsonar.organization=${config.organization} -Dsonar.host.url=https://sonarcloud.io' //-Dsonar.login=sonar_token
			}
		}
	}
}