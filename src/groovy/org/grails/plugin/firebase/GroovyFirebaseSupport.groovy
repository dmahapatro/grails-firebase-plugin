package org.grails.plugin.firebase

import com.firebase.security.token.TokenGenerator
import groovy.util.logging.Log4j
import org.json.JSONException
import org.json.JSONObject

@Log4j
class GroovyFirebaseSupport {

    static def doWithSpring = { application ->
        String firebase = application.config.grails.plugin.firebase.base
        String secretKey = application.config.grails.plugin.firebase.secretKey

        def _url = !firebase.contains('http') ? "https://${firebase}.firebaseio.com" : firebase ?: ''

        if(!_url) {
            log.debug "Url missing in config"
        }

        // Generate a new secure JWT
        JSONObject arbitraryPayload = new JSONObject()
        try {
            arbitraryPayload.put("username", "foouser")
            arbitraryPayload.put("isAdmin",true)
        } catch (JSONException e) {
            log.error e
        }

        TokenGenerator tokenGenerator = new TokenGenerator(secretKey);
        String token = tokenGenerator.createToken(arbitraryPayload);

        // Register as a bean
        firebaseRef(GroovyFirebase, _url, token)
    }
}
