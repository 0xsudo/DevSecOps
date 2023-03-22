def call(Map param = [:]) {
    if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		sh 'aws eks update-kubeconfig --region ${param.region} --name ${param.clustername}'
		sh 'sleep 120'
	}
}