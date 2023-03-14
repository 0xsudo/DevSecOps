pipeline {
	agent any
	tools {
		maven 'mvn'
	}
	stages {
		stage('Compile and run Sonar Analysis') {
			steps {
				sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=DevSecOps -Dsonar.organization="Kaoka Kelvin" -Dsonar.host.url=https://sonarcloud.io -Dsdonar.login=6dbf93d137851b4f44d1a41a54cc71f56576b737'
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
}