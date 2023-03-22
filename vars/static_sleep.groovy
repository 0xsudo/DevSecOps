def call(Map param = [:]) {
    if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		sh 'sleep ${param.periodseconds}; echo "Deployment ready for DAST analysis on EKS"'
	}
}