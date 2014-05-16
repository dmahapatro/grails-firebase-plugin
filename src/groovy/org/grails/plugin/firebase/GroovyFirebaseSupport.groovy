package org.grails.plugin.firebase

class GroovyFirebaseSupport {

    static def doWithSpring = { application ->
        def firebase = application.config.grails.plugin.firebase.base
        def _url = !firebase.contains('http') ? "https://${firebase}.firebaseio.com" : firebase ?: ''

        if(!_url) {
            println "Url missing in config"
        }

        firebaseRef(GroovyFirebase, _url)
    }
}
