def call(Map param = [:]) {
    if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		sh 'eksctl create cluster --name ${param.clustername} --region ${param.region} --zones ${param.region}a,${param.region}b --nodegroup-name ${param.nodegroupname} --nodes ${param.nodes} --instance-types ${param.instancetype} --tags "app=${param.tag}" --version ${param.kubernetesversion}'
	} else {
		// deleting the cluster directly created a race condition btwn node groups and cluster, decided to do it in two steps
		sh 'eksctl delete nodegroup --name linux-buggy-app --cluster ${param.clustername} --region ${param.region}'
		sh 'eksctl delete cluster --name ${param.clustername} --region ${param.region} --force'
	}
}