import org.grails.plugin.firebase.GroovyFirebaseSupport

class FirebaseGrailsPlugin {
    def version = "0.0.1"
    def grailsVersion = "2.3 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Firebase"
    def author = "Dhiraj Mahapatro"
    def authorEmail = "dmahapatro@netjets.com"
    def description = 'Grails plugin for Firebase'
    def documentation = "https://github.com/dmahapatro/grails-firebase-plugin"
    def license = "APACHE"
    def issueManagement = [ system: "Github", url: "https://github.com/dmahapatro/grails-firebase-plugin" ]
    def scm = [ url: "https://github.com/dmahapatro/grails-firebase-plugin" ]

    def doWithSpring = {
        GroovyFirebaseSupport.doWithSpring.delegate = delegate
        GroovyFirebaseSupport.doWithSpring(application)
    }
}
