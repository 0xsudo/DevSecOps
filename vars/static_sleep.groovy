def call(Map config = [:]) {
    if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		sh 'sleep ${config.periodseconds}; echo "Deployment ready for DAST analysis on EKS"'
	}
}