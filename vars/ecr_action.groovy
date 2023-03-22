def call(Map config = [:]) {
    if (params.ecr_action == 'create') {
	    sh 'aws ecr create-repository --repository-name ${config.name}'
    } else {
	    sh'aws ecr delete-repository --repository-name ${config.name} --force'
    }
}