pipeline {
	agent any
	tools {
		maven 'mvn'
	}
	parameters {
		choice(
			name: 'ecr_action',
			choices: ['create', 'delete'],
			description: 'Creating or deleting ECR Repo'
		)
		choice(
			name: 'eksctl_action',
			choices: ['create', 'delete'],
			description: 'Creating or deleting EKS cluster'
		)
		string(
			name: 'buggy_app',
			defaultValue: 'buggy-app',
			description: 'Name for our application'
		)
		string(
			name: 'namespace',
			defaultValue: 'devsecops',
			description: 'Name for our namespace'
		)
	}

	stages {
		stage('Git Checkout') {
			steps{
				if (env.BRANCH_NAME == 'main') {
					git credentialsId: 'jenkins_pk', url: 'git@github.com:0xsudo/DevSecOps.git'
				}
			}
		}
		// stage('Docker Build') {
		// 	steps {
		// 		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
		// 			script {
		// 				if (params.ecr_action == 'create') {
		// 					docker_image=docker.build('buggy-app')
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		// stage('ECR Registry Action') {
		// 	steps {
		// 		script {
		// 			if (params.ecr_action == 'create') {
		// 				sh 'aws ecr create-repository --repository-name buggy-app'
		// 			} else {
		// 				sh'aws ecr delete-repository --repository-name buggy-app --force'
		// 			}
		// 		}
		// 	}
		// }
		// stage('Docker Push') {
		// 	steps {
		// 		script {
		// 			if (params.ecr_action == 'create') {
		// 				docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
		// 					docker_image.push('latest')
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		// stage('SAST Analysis: SonarCloud') {
		// 	steps {
		// 		script {
		// 			if (params.ecr_action == 'create') {
		// 				withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
		// 					sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=0xsudo_DevSecOps -Dsonar.organization=buggyapp-devsecops -Dsonar.host.url=https://sonarcloud.io' //-Dsonar.login=sonar_token
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		// stage('SCA Analysis: Snyk') {
		// 	steps {
		// 		script {
		// 			if (params.ecr_action == 'create') {
		// 				withCredentials([string(credentialsId: 'SNYK_TOKEN', variable: 'SNYK_TOKEN')]) {
		// 					sh 'mvn snyk:test -fn'
		// 				}
		// 			}
		// 		}
		// 	}
		// }
		// stage('EKS Cluster Action') {
		// 	steps {
		// 		script {
		// 			if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		// 				sh 'eksctl create cluster --name devsecops-buggy-app --region us-east-1 --zones us-east-1a,us-east-1b --nodegroup-name linux-buggy-app --nodes 2 --instance-types t2.nano --tags "app=buggy-app" --version 1.25'
		// 			} else {
		// 				// deleting the cluster directly created a race condition btwn node groups and cluster, decided to do it in two steps
		// 				sh 'eksctl delete nodegroup --name linux-buggy-app --cluster devsecops-buggy-app --region us-east-1'
		// 				sh 'eksctl delete cluster --name devsecops-buggy-app --region us-east-1 --force'
		// 			}
		// 		}
		// 	}
		// }
		stage('EKS Cluster Connection') {
			steps{
				script {
					if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
						sh 'aws eks update-kubeconfig --region us-east-1 --name devsecops-${params.buggy_app}'
						sh 'sleep 120'
					}
				}
			}
		}
		stage('Deployment & Service Creation') {
			steps {
				script {
					retry(count: 3){
						if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
							sh 'kubectl delete namespace ${params.namespace}'
							sh 'kubectl create namespace ${params.namespace}'
							sh 'kubectl apply -f deployment.yaml --namespace ${params.namespace}'
						}
					}
				}				
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

		// stage('Wait for deployment on EKS') {
		// 	steps {
		// 		script {
		// 			if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		// 				sh 'sleep 180; echo "Deployment ready for DAST analysis on EKS"'
		// 			}
		// 		}
		// 	}
		// }
		// stage('DAST Analysis: OWASP ZAP') {
		// 	steps {
		// 		script {
		// 			retry(count: 3) {
		// 				if (params.eksctl_action == 'create' && params.ecr_action == 'create') {
		// 				sh 'zap.sh -cmd -port 9090 -quickurl http://$(kubectl get services/buggy-app --namespace devsecops -o json| jq -r ".status.loadBalancer.ingress[] | .hostname") -quickprogress -quickout ${WORKSPACE}/DAST_ZAP_buggyapp.html'
		// 				archiveArtifacts(artifacts: 'DAST_ZAP_buggyapp.html')
		// 				}
		// 			}
		// 		}
		// 	}
		// }
	}
	post {
		always {
			sh 'docker logout'
		}
	}
}