def call(Map param = [:]) {
    retry(count: 3) {
		if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
			sh 'zap.sh -cmd -port ${param.zapport} -quickurl http://$(kubectl get services/buggy-app --namespace ${param.namespace} -o json| jq -r ".status.loadBalancer.ingress[] | .hostname") -quickprogress -quickout ${WORKSPACE}/${param.zapreport}'
		    archiveArtifacts(artifacts: '${param.zapreport}')
		}
	}
}