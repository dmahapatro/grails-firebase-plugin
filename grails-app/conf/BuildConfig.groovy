grails.project.work.dir = 'target'

grails.project.dependency.resolution = {
    inherits("global") { }
    log "warn"
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        compile 'com.firebase:firebase-client:1.0.12'
        compile 'com.firebase:firebase-token-generator:1.0.4'
    }

    plugins {
        build(":release:3.0.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}