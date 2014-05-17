package org.grails.plugin.firebase

import groovy.util.logging.Log4j

@Log4j
class GroovyFirebaseSupport {

    static def doWithSpring = { application ->
        def firebase = application.config.grails.plugin.firebase.base
        def _url = !firebase.contains('http') ? "https://${firebase}.firebaseio.com" : firebase ?: ''

        if(!_url) {
            log.debug "Url missing in config"
        }

        // Register as a bean
        firebaseRef(GroovyFirebase, _url)
    }
}
