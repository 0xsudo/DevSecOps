def call(String stagename) {
    if ('${stagename}' == 'Git Checkout') {
        if (env.BRANCH_NAME == 'main') {
			git credentialsId: 'jenkins_pk', url: 'git@github.com:0xsudo/DevSecOps.git'
		}
    }
}