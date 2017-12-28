pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        ansiColor(colorMapName: 'xterm') {
          sh '${tool name: \'sbt-0.13.13\', type: \'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation\'}/bin/sbt compile test'
        }
        
      }
    }
  }
}