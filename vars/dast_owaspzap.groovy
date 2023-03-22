def call(Map config = [:]) {
    retry(count: 3) {
		if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
			sh 'zap.sh -cmd -port ${config.zapport} -quickurl http://$(kubectl get services/buggy-app --namespace ${config.namespace} -o json| jq -r ".status.loadBalancer.ingress[] | .hostname") -quickprogress -quickout ${WORKSPACE}/${config.zapreport}'
		    archiveArtifacts(artifacts: '${config.zapreport}')
		}
	}
}