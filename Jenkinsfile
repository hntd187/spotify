node {
   stage('Git') {
      git 'https://github.com/hntd187/spotify.git'
   }
   stage('Build') {
       def builds = [:]
       builds['scala'] = {
           // assumes you have the sbt plugin installed and created an sbt installation named 'sbt-0.13.13'
           sh "${tool name: 'sbtrunner', type: 'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation'}/bin/sbt compile test"
       }
     parallel builds
   }
   stage('Results') {
      junit '**/target/test-reports/*.xml'
   }
}
