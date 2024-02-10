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

                            docker
                                .image("gradle")
                                .run(""
                                	+ "--rm "
                                	+ "-u gradle "
                                	+ "-v \"$PWD\":/home/gradle/project "
                                	+ "-w /home/gradle/project "
                                	+ "gradle "
                            	    + (""
                            	        + "--debug " 
                                	    + "--no-daemon " 
                                	    + "-PgitCommit=${COMMIT_HASH} " 
                                	    + "-PjenkinsBuild=${JENKINS_BUILD} " 
                                	+ "clean check test jacocoFullReport generatePomFileForMavenPublication copyBintrayTemplate publish" 	
                                )

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

        stage('Publish') {
            steps {
                script {
                    withCredentials([file(credentialsId: gcpKeyId, variable: 'GC_KEY')]) {
                        withEnv(["GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY}"]) {
                            withGradle {
                                sh "./gradlew --no-daemon -PgitCommit=${COMMIT_HASH} -PjenkinsBuild=${JENKINS_BUILD} publish"
                            }
                        }
                    }
                }
            }
        }
    }
}
