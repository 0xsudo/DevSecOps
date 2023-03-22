def call(Map param = [:]) {
    if (params.ecr_action == 'create') {
	    sh 'aws ecr create-repository --repository-name ${param.name}'
    } else {
	    sh'aws ecr delete-repository --repository-name ${param.name} --force'
    }
}