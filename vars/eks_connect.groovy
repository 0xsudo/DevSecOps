def call(Map config = [:]) {
    if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		sh 'aws eks update-kubeconfig --region ${config.region} --name ${config.clustername}'
		sh 'sleep 120'
	}
}