def container

def dockerArgs
def gradleArgs

pipeline {
  agent any

  environment {
    BRANCH        = "${env.BRANCH_NAME}"
    COMMIT_HASH   = "${env.GIT_COMMIT}"
    JENKINS_BUILD = "${env.GIT_COMMIT}"

    gcpKeyId       = 'cromulence-srvcacc-jsonAccessKey'
    gcpProjectName = "cromulence"
    garRegion      = "europe-west2"

    testPropertiesId = "imgur-api-test-properties"
  }

  stages {
    stage('Setup project') {
      steps {
        script {
          dockerArgs = [
            "--privileged=true ",
            "--user root ",
            "--volumes-from jenkins-worker-a-1 ",
            "-w ${env.WORKSPACE} "
          ].join(" ")
          gradleArgs = [
            "gradle ",
            "--stacktrace ",
            "--no-daemon ",
            "-PgitCommit=${COMMIT_HASH} " ,
            "-PjenkinsBuild=${JENKINS_BUILD} "
          ].join(" ")
        }
      }
    }

    stage('Build project') {
      steps {
        withCredentials([file(credentialsId: gcpKeyId, variable: 'GC_KEY'), file(credentialsId: testPropertiesId, variable: 'TEST_PROPERTIES')]) {
          withEnv(["GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY}"]) {
            script {
              container = docker
                .image("gradle:7.6.4-jdk11")
                .run(
                  dockerArgs + " -e GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY} -v ${TEST_PROPERTIES}:${env.WORKSPACE}/api/src/test/resources/test.properties",
                  gradleArgs + " clean check build"
                )
              sh "echo test container exit code \$(docker wait ${container.id})"
              sh "exit \$(docker wait ${container.id})"
            }
          }
        }
      }
    }

    stage('Test project') {
      steps {
        withCredentials([file(credentialsId: gcpKeyId, variable: 'GC_KEY'), file(credentialsId: testPropertiesId, variable: 'TEST_PROPERTIES')]) {
          withEnv(["GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY}"]) {
            script {


              container = docker
                .image("gradle:7.6.4-jdk11")
                .run(
                  dockerArgs + " -e GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY} -v ${TEST_PROPERTIES}:${env.WORKSPACE}/api/src/test/resources/test.properties",
                  gradleArgs + " test jacocoTestReport"
                )

              sh "echo test container exit code \$(docker wait ${container.id})"
              sh "exit \$(docker wait ${container.id})"
            }
          }
        }
      }
    }

    stage('Publish project') {
      steps {
        withCredentials([file(credentialsId: gcpKeyId, variable: 'GC_KEY')]) {
          withEnv(["GOOGLE_APPLICATION_CREDENTIALS=${GC_KEY}"]) {
            script {
              container = docker
                .image("gradle:7.6.4-jdk11")
                .run(
                  dockerArgs,
                  gradleArgs + " publish"
                )

              sh "echo test container exit code \$(docker wait ${container.id})"
              sh "exit \$(docker wait ${container.id})"
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
