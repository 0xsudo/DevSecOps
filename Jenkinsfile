@Library('devsecops-shared-lib') _
pipeline {
	agent any
	tools {
		maven 'mvn'
	}
	parameters {
		choice choices: ['create', 'delete'], description: 'Creating or deleting ECR repo', name: 'ecr_action'
		// choice choices: ['create', 'delete'], description: 'Creating or deleting EKS cluster', name: 'eksctl_action'
		// string defaultValue: 'buggy-app', description: 'Name for our application', name: 'buggy_app', trim: true
		// string defaultValue: 'devsecops', description: 'Name for our namespace', name: 'namespace', trim: true
	}

	stages {
		stage('Git Checkout') {
			steps {
				script {
					git_chkout('Git Checkout')
				}
			}
		}
		stage('Docker Build') {
			steps {
				script {
					docker_build(name: 'buggy-app')
				}
			}
		}
		stage('ECR Registry Action') {
			steps {
				script {
					ecr_action(name: 'buggy-app')
				}
			}
		}
		stage('Docker Push') {
			steps {
				script {
					docker_push(tag: 'latest', region: 'us-east-1', iamrole: 'devopsrole')
				}
			}
		}
		stage('SAST Analysis: SonarCloud') {
			steps {
				script {
					sast_sonarcloud(organization: 'buggyapp-devsecops', projectkey: '0xsudo_DevSecOps')
				}
			}
		}
		stage('SCA Analysis: Snyk') {
			steps {
				script {
					sca_synk()
				}
			}
		}
		stage('EKS Cluster Action') {
			steps {
				script {
					eks_action(clustername: 'devsecops-buggy-app', region: 'us-east-1', nodegroupname: 'linux-buggy-app', nodes: 2, instancetype: 't2.micro', tag: 'buggy-app', kubernetesversion: 1.25)
				}
			}
		}
		stage('EKS Cluster Connection') {
			steps{
				script {
					eks_connect(clustername:'devsecops-buggy-app', region: 'us-east-1')
				}
			}
		}
		stage('Deployment & Service Action') {
			steps {
				script {
					deploy_action(namespace: 'devsecops')
				}				
			}
		}
	// 	stage('Wait for Deployment on EKS') {
	// 		steps {
	// 			script {
	// 				if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
	// 					sh 'sleep 180; echo "Deployment ready for DAST analysis on EKS"'
	// 				}
	// 			}
	// 		}
	// 	}
	// 	stage('DAST Analysis: OWASP ZAP') {
	// 		steps {
	// 			script {
	// 				retry(count: 3) {
	// 					if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
	// 					sh 'zap.sh -cmd -port 9090 -quickurl http://$(kubectl get services/buggy-app --namespace devsecops -o json| jq -r ".status.loadBalancer.ingress[] | .hostname") -quickprogress -quickout ${WORKSPACE}/DAST_ZAP_buggyapp.html'
	// 					archiveArtifacts(artifacts: 'DAST_ZAP_buggyapp.html')
	// 					}
	// 				}
	// 			}
	// 		}
	// 	}
	}
	post {
		always {
			sh 'docker logout'
		}
	}
}