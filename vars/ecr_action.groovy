def call() {
    if (params.ecr_action == 'create') {
	    sh 'aws ecr create-repository --repository-name buggy-app'
    } else {
	    sh'aws ecr delete-repository --repository-name buggy-app --force'
    }
}