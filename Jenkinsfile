@Library('devsecops-shared-lib') _
pipeline {
	agent any
	tools {
		maven 'mvn'
	}
	parameters {
		choice choices: ['create', 'delete'], description: 'Creating or deleting ECR repo', name: 'ecr_action'
		choice choices: ['create', 'delete'], description: 'Creating or deleting EKS cluster', name: 'eksctl_action'
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
				retry(count: 3) {
					withDockerRegistry([credentialsId: 'docker-login', url: '']) {
						script {
							img = docker.build('buggyapp')
						}
					}
				}
			}
		}
		// stage('ECR Registry Action') {
		// 	steps {
		// 		script {
		// 			ecr_action()
		// 		}
		// 	}
		// }
		stage('Docker Push') {
			steps {
				retry(count: 3) {
					docker.withRegstry('https://636181284446.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:devopsrole') {
						img.push('latest')
					}
				}
		}
	// 	stage('SAST Analysis: SonarCloud') {
	// 		steps {
	// 			script {
	// 				sast_sonarcloud(organization: 'buggyapp-devsecops', projectkey: '0xsudo_DevSecOps')
	// 			}
	// 		}
	// 	}
	// 	stage('SCA Analysis: Snyk') {
	// 		steps {
	// 			script {
	// 				sca_synk()
	// 			}
	// 		}
	// 	}
	// 	stage('EKS Cluster Action') {
	// 		steps {
	// 			script {
	// 				eks_action(clustername: 'devsecops-buggy-app', region: 'us-east-1', nodegroupname: 'linux-buggy-app', nodes: 2, instancetype: 't2.micro', tag: 'buggy-app', kubernetesversion: 1.25)
	// 			}
	// 		}
	// 	}
	// 	stage('EKS Cluster Connection') {
	// 		steps{
	// 			script {
	// 				eks_connect(clustername: 'devsecops-buggy-app', region: 'us-east-1')
	// 			}
	// 		}
	// 	}
	// 	stage('Deployment & Service Action') {
	// 		steps {
	// 			script {
	// 				deploy_action(namespace: 'devsecops')
	// 			}				
	// 		}
	// 	}
		stage('Wait for Deployment on EKS') {
			steps {
				script {
					static_sleep(periodseconds: 60)
				}
			}
		}
	// 	stage('DAST Analysis: OWASP ZAP') {
	// 		steps {
	// 			script {
	// 				dast_owaspzap(zapport: 9090, namespace: 'devsecops', zapreport: '""$BUILD_NUMBER""_DAST_ZAP_buggyapp.html')
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