def container

pipeline {
    agent any

    environment {
        BRANCH        = "${env.BRANCH_NAME}"
        COMMIT_HASH   = "${env.GIT_COMMIT}"
        JENKINS_BUILD = "${env.GIT_COMMIT}"

        gcpKeyId       = 'cromulence-srvcacc-jsonAccessKey'
        gcpProjectName = "cromulence"
        garRegion      = "europe-west2"
    }

    stages {
        stage('Build project') {
            steps {
                withCredentials([file(credentialsId: gcpKeyId, variable: 'GC_KEY')]) {
                    withEnv(["GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY}"]) {
                        script {

                            container = docker
                                .image("gradle:7.6.4-jdk11")
                                .run(""
                                	+ "-u gradle "
                                	+ "-v \"${env.WORKSPACE}:/home/gradle/project\" "
                                	+ "-w /home/gradle/project ",                                
                                	"gradle "
                            	    + "--debug "  
                                	+ "--no-daemon " 
                                	+ "-PgitCommit=${COMMIT_HASH} " 
                                	+ "-PjenkinsBuild=${JENKINS_BUILD} " 
                                	+ "clean check test jacocoTestReport generatePomFileForMavenPublication copyBintrayTemplate publish" 	
                                )

                            sh "echo test container exit code \$(docker wait ${container.id})"
                            sh "exit \$(docker wait ${container.id})"

                           //  withGradle {
                           //     sh "./gradlew --debug --no-daemon -PgitCommit=${COMMIT_HASH} -PjenkinsBuild=${JENKINS_BUILD} clean check sourceJar javadocJar jacocoFullReport jacocoMerge generatePomFileForMavenPublication copyBintrayTemplate"
                           // }
                        }
                    }
                }
            }
        }

        // stage('Run Tests') {
        //     steps {
        //         script {
        //             withCredentials([file(credentialsId: gcpKeyId, variable: 'GC_KEY')]) {
        //                 withEnv(["GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY}"]) {
        //                     withGradle {
        //                        sh "./gradlew --no-daemon -PgitCommit=${COMMIT_HASH} -PjenkinsBuild=${JENKINS_BUILD} test"
        //                    }
        //                }
        //            }
        //        }
        //     }
        // }

        // stage('Publish') {
        //     steps {
        //         script {
        //             withCredentials([file(credentialsId: gcpKeyId, variable: 'GC_KEY')]) {
        //                 withEnv(["GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY}"]) {
        //                     withGradle {
        //                         sh "./gradlew --no-daemon -PgitCommit=${COMMIT_HASH} -PjenkinsBuild=${JENKINS_BUILD} publish"
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // }
    }

    post {
        failure {
            script {
                sh "docker logs ${container.id}"
            }
        }
    }
}
