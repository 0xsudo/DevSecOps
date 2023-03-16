pipeline {
	agent any
	tools {
		maven 'mvn'
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
		stage('Docker Build') {
			steps {
				withDockerRegistry([credentialsId: 'docker-login', url: '']) {
					script {
						docker_image=docker.build('buggy-app')
					}
				}
			}
		}
		stage('Create ECR Registry') {
			steps {
				script {
					sh 'aws ecr delete-repository --repository-name asg-buggy'
					sh 'aws ecr create-repository --repository-name asg-buggy'
				}
			}
		}
		// stage('Docker Push') {
		// 	steps {
		// 		script {
		// 			docker.withRegistry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
		// 			docker_image.push('latest')
		// 		}
		// 	}
		// }
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