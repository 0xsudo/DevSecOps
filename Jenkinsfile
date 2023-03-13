pipeline {
	agent any
	tools {
		maven 'maven'
	}
	stages {
		stage('Compile and run Sonar Analysis') {
			steps {
				sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=buggyapp -Dsonar.organization=buggyapp -Dsonar.host.url=https://sonarcloud.io -Dsdonar.login=key'
		}
		}
		stage('Synk SCA Analysis') {
			steps {
				withCredentials([string(credentialsId: 'SYNK_TOKEN', variable: 'SYNK_TOKEN')]) {
					sh 'mvn synk:test -fn'
				}
			}
		}
		stage('Build Docker Image') {
			steps {
				withDockerRegistry([credentialsId: 'docker', url: '']) {
					script {
						app = docker.build('asg')
					}
				}
			}
		}
		stage('Push Docker Image') {
			steps {
				script {
					docker.withRegistry('aws registry', 'ecr:region:aws-creds') {
						app.push('latest')
					}
				}
			}
		}
		stage('Kubernetes Deployment') {
			steps {
				withKubeConfig([credentialsId: 'kubeconfig file']) {
					sh 'kubectl delete all --all -n namespace'
					sh 'kubectl apply -f deployment.yaml --namespace namespace'
				}
			}
		}
		stage('Wait before testing') {
			steps {
				sh 'sleep 180; echo "Application deployed on k8s, ready for DAST analysis"'
			}
		}
		stage('OWASP ZAP DAST Analysis') {
			steps {
				withKubeConfig([credentialsId: 'kubeconfig file']) {
					sh 'zap.sh -cmd -quickurl http://(kubectl get services/asgbuggy --namespace namespace -o json | jq -r ".status.loadBalancer.ingress[] | .hostname") -quickprogress -quickout ${WORKSAPCE}/DAST_report.html'
					archiveArtifacts artifacts: 'DAST_report.html'
				}
			}
		}
	}
}

// pipeline {
//   agent any
//   tools { 
//         maven 'Maven_3_5_2'  
//     }
//    stages{
//     stage('CompileandRunSonarAnalysis') {
//             steps {	
// 		sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=asgbuggywebapp -Dsonar.organization=asgbuggywebapp -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=932558e169d66a8f1d1adf470b908a46156f5844'
// 			}
//     }

// 	stage('RunSCAAnalysisUsingSnyk') {
//             steps {		
// 				withCredentials([string(credentialsId: 'SNYK_TOKEN', variable: 'SNYK_TOKEN')]) {
// 					sh 'mvn snyk:test -fn'
// 				}
// 			}
//     }

// 	stage('Build') { 
//             steps { 
//                withDockerRegistry([credentialsId: "dockerlogin", url: ""]) {
//                  script{
//                  app =  docker.build("asg")
//                  }
//                }
//             }
//     }

// 	stage('Push') {
//             steps {
//                 script{
//                     docker.withRegistry('https://145988340565.dkr.ecr.us-west-2.amazonaws.com', 'ecr:us-west-2:aws-credentials') {
//                     app.push("latest")
//                     }
//                 }
//             }
//     	}
	   
// 	stage('Kubernetes Deployment of ASG Bugg Web Application') {
// 	   steps {
// 	      withKubeConfig([credentialsId: 'kubelogin']) {
// 		  sh('kubectl delete all --all -n devsecops')
// 		  sh ('kubectl apply -f deployment.yaml --namespace=devsecops')
// 		}
// 	      }
//    	}
	   
// 	stage ('wait_for_testing'){
// 	   steps {
// 		   sh 'pwd; sleep 180; echo "Application Has been deployed on K8S"'
// 	   	}
// 	   }
	   
// 	stage('RunDASTUsingZAP') {
//           steps {
// 		    withKubeConfig([credentialsId: 'kubelogin']) {
// 				sh('zap.sh -cmd -quickurl http://$(kubectl get services/asgbuggy --namespace=devsecops -o json| jq -r ".status.loadBalancer.ingress[] | .hostname") -quickprogress -quickout ${WORKSPACE}/zap_report.html')
// 				archiveArtifacts artifacts: 'zap_report.html'
// 		    }
// 	     }
//        } 
//   }
// }
