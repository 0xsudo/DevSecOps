def call(Map config = [:]) {
    if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		sh 'eksctl create cluster --name ${config.clustername} --region ${config.region} --zones ${config.region}a,${config.region}b --nodegroup-name ${config.nodegroupname} --nodes ${config.nodes} --instance-types ${config.instancetype} --tags "app=${config.tag}" --version ${config.kubernetesversion}'
	} else {
		// deleting the cluster directly created a race condition btwn node groups and cluster, decided to do it in two steps
		sh 'eksctl delete nodegroup --name linux-buggy-app --cluster ${config.clustername} --region ${config.region}'
		sh 'eksctl delete cluster --name ${config.clustername} --region ${config.region} --force'
	}
}