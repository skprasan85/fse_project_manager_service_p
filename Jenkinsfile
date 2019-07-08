pipeline { 
     agent any 
     tools {  
         maven 'Maven' 
 		jdk 'JDK8' 
     } 
     triggers { 
         pollSCM('H/5 * * * *') 
     } 
 

     options { 
         buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '20')) 
     } 
     
   stages 
 	{ 
 	   stage('Checkout from SCM GIT') { 
 		   steps { 
 			     checkout([$class: 'GitSCM', branches: [[name: '*/master']],  
 				      doGenerateSubmoduleConfigurations: false, extensions: [],  
 				      submoduleCfg: [],  
 				      userRemoteConfigs: [[url: 'https://github.com/saranya1803/fse_project_manager_service-1.git']]]) 
 			     echo 'Git checkout succeeded' 
 		   } 
 	   } 
 
 	   stage('Build & Analyse') { 
 		   steps { 
 			bat "mvn clean install sonar:sonar" 
 		   } 
 	   } 
 	   stage('Publish Junit Report & Archive') { 
 		   steps { 
 				archiveArtifacts artifacts: 'project-manager-service/target/*.jar', fingerprint: true 
 				junit '**/TEST-*.xml' 
 			        step([$class: 'JacocoPublisher',  
 				      execPattern: '**/*.exec', 
 				      classPattern: '**/classes', 
 				      sourcePattern: '**/src/main/java', 
 				      exclusionPattern: '**/src/test*' 
 				]) 
 		   } 
 		} 
 	} 
 } 