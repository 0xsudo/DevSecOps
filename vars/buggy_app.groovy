def call(String stagename) {
    if ('${stagename}' == 'Git Checkout') {
        if (env.BRANCH_NAME == 'main') {
			git credentialsId: 'jenkins_pk', url: 'git@github.com:0xsudo/DevSecOps.git'
		}
    }
    
    else if ('${stagename}' == 'Docker Build') {
        retry(count: 3) {
			withDockerRegistry([credentialsId: 'docker-login', url: '']) {
				script {
					if (params.ecr_action == 'create') {
						docker_image=docker.build('buggy-app')
					}
				}
			}
		}
    }
}