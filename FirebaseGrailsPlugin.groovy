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
    def description = 'Grails plugin for Firebase Client'
    def documentation = "http://grails.org/plugin/firebase"
    def license = "APACHE"
    //def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]
    //def issueManagement = [ system: "Github", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]
    //def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def doWithWebDescriptor = { xml ->    }

    def doWithSpring = {
        GroovyFirebaseSupport.doWithSpring.delegate = delegate
        GroovyFirebaseSupport.doWithSpring(application)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
