pipeline {
	agent any
	tools {
		maven 'mvn'
	}
	parameters {
		// choice(
		// 	name: 'ecr_action',
		// 	choices: ['create', 'delete'],
		// 	description: 'Creating or deleting ECR Repo'
		// )
		choice(
			name: 'eksctl_action',
			choices: ['create', 'delete'],
			description: 'Creating or deleting EKS cluster'
		)
		// text(
		// 	name: 'our_app',
		// 	defaultValue: 'buggy-app',
		// 	description: 'Name for our application'
		// )
	}

	stages {
		stage('Git Checkout') {
			steps{
				git branch: 'main', credentialsId: 'jenkins_pk', url: 'git@github.com:0xsudo/DevSecOps.git'
			}
		}
		// stage('Compile and Run Sonar Analysis') {
		// 	steps {
		// 		withCredentials([string(credentialsId: 'SONAR_TOKEN', variable: 'SONAR_TOKEN')]) {
		// 			sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=0xsudo_DevSecOps -Dsonar.organization=buggyapp-devsecops -Dsonar.host.url=https://sonarcloud.io' //-Dsonar.login=sonar_token

		// 		}
		// 	}
		// }
		// stage('SCA Snyk Analysis') {
		// 	steps {
		// 		withCredentials([string(credentialsId: 'SNYK_TOKEN', variable: 'SNYK_TOKEN')]) {
		// 			sh 'mvn snyk:test -fn'
		// 		}
		// 	}
		// }
		// stage('Docker Build') {
		// 	steps {
		// 		withDockerRegistry([credentialsId: 'docker-login', url: '']) {
		// 			script {
		// 				docker_image=docker.build('buggy-app')
		// 			}
		// 		}
		// 	}
		// }
		// stage('Create ECR Registry') {
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
		// 			docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
		// 			docker_image.push('latest')
		// 		}
		// 	}
		// }
		stage('Create EKS Cluster') {
			steps {
				script {
					if (params.eksctl_action == 'create') {
						sh 'eksctl create cluster --name devsecops-buggy-app --region us-east-1 --zones us-east-1a --nodegroup-name linux-buggy-app --nodes 2 --instance-types t2.nano --spot --tags "app=buggy-app" --version 1.25'
					} else {
						sh 'aws cloudformation delete-stack --stack-name eksctl-devsecops-buggy-app-cluster --region us-east-1'
						// deleting the cluster directly created a race condition btwn node groups and cluster, decided to delete the cloudformation template instead
						// sh 'eksctl delete nodegroup --name linux-buggy-app --cluster devsecops-buggy-app --region us-east-1'
						// sh 'eksctl delete cluster --name devsecops-buggy-app --region us-east-1 --force'
					}
				}
			}
		}
		stage('Connect to EKS Cluster') {
			steps{
				script {
					if (params.eksctl_action == 'create') {
						sh 'aws eks update-kubeconfig --region us-east-1 --name devsecops-buggy-app'
					}
				}
			}
		}
		// stage('Synk SCA Analysis') {
		// 	steps {
		// 		withCredentials([string(credentialsId: 'SYNK_TOKEN', variable: 'SYNK_TOKEN')]) {
		// 			sh 'mvn synk:test -fn'
		// 		}
		// 	}
		// }
		// stage('Build Docker Image') {
		// 	steps {
		// 		withDockerRegistry([credentialsId: 'docker', url: '']) {
		// 			script {
		// 				app = docker.build('asg')
		// 			}
		// 		}
		// 	}
		// }
		// stage('Push Docker Image') {
		// 	steps {
		// 		script {
		// 			docker.withRegistry('aws registry', 'ecr:region:aws-creds') {
		// 				app.push('latest')
		// 			}
		// 		}
		// 	}
		// }
		// stage('Kubernetes Deployment') {
		// 	steps {
		// 		withKubeConfig([credentialsId: 'kubeconfig file']) {
		// 			sh 'kubectl delete all --all -n namespace'
		// 			sh 'kubectl apply -f deployment.yaml --namespace namespace'
		// 		}
		// 	}
		// }
		// stage('Wait before testing') {
		// 	steps {
		// 		sh 'sleep 180; echo "Application deployed on k8s, ready for DAST analysis"'
		// 	}
		// }
		// stage('OWASP ZAP DAST Analysis') {
		// 	steps {
		// 		withKubeConfig([credentialsId: 'kubeconfig file']) {
		// 			sh 'zap.sh -cmd -quickurl http://(kubectl get services/asgbuggy --namespace namespace -o json | jq -r ".status.loadBalancer.ingress[] | .hostname") -quickprogress -quickout ${WORKSAPCE}/DAST_report.html'
		// 			archiveArtifacts artifacts: 'DAST_report.html'
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