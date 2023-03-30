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
				script{
					docker_build()
				}
			}
		}
		stage('ECR Registry Action') {
			steps {
				script {
					ecr_action()
				}
			}
		}
		stage('Docker Push') {
			steps {
				script {
					docker_push()
				}
			}
		}
	// 	stage('SAST Analysis: SonarCloud') {
	// 		steps {
	// 			script {
	// 				sast_sonarcloud()
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
	// 				eks_action()
	// 			}
	// 		}
	// 	}
	// 	stage('EKS Cluster Connection') {
	// 		steps{
	// 			script {
	// 				eks_connect()
	// 			}
	// 		}
	// 	}
	// 	stage('Deployment & Service Action') {
	// 		steps {
	// 			script {
	// 				deploy_action()
	// 			}				
	// 		}
	// 	}
		// stage('Wait for Deployment on EKS') {
		// 	steps {
		// 		script {
		// 			static_sleep()
		// 		}
		// 	}
		// }
	// 	stage('DAST Analysis: OWASP ZAP') {
	// 		steps {
	// 			script {
	// 				dast_owaspzap()
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