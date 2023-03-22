def call(Map param = [:]) {
    retry(count: 3) {
		if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
            sh 'chmod a+x ../namespace_test.sh'
			sh '../namespace_test.sh'
			sh 'kubectl apply -f deployment.yaml --namespace ${param.namespace}'
		}
	}
    // alternatively
	// stage('Kubernetes Deployment') {
	// 	steps {
	// 		withKubeConfig([credentialsId: 'kubeconfig file']) {
	// 			sh 'kubectl delete namespace namespace'
	// 			sh 'kubectl apply -f deployment.yaml --namespace namespace'
	// 		}
	// 	}
}