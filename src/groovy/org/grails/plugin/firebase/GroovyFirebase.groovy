package org.grails.plugin.firebase

import com.firebase.client.ChildEventListener
import com.firebase.client.DataSnapshot
import com.firebase.client.Firebase
import com.firebase.client.FirebaseError
import com.firebase.client.GenericTypeIndicator
import com.firebase.client.ValueEventListener
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j

@InheritConstructors
@Log4j
class GroovyFirebase extends Firebase {

    public on(String type, Closure callback) {
        switch(type){
            case 'value':
                onValue callback
                break
            case ['childAdded', 'childChanged', 'childRemoved']:
                onChild type, callback
                break
            default:
                log.error "No event callback registered"
        }
    }

    public onValue(Closure callback){
        addValueEventListener registerValueEventListener(callback)
    }

    private onChild(String type, Closure callback){
        addChildEventListener registerChildEventListener(type, callback)
    }

    public onChildAdded(Closure callback){
        addChildEventListener registerChildEventListener('childAdded', callback)
    }

    public onChildChanged(Closure callback){
        addChildEventListener registerChildEventListener('childChanged', callback)
    }

    public onChildRemoved(Closure callback){
        addChildEventListener registerChildEventListener('childRemoved', callback)
    }

    public once(Closure callback) {
        addListenerForSingleValueEvent registerValueEventListener(callback)
    }

    private ValueEventListener registerValueEventListener(Closure callback){
        new ValueEventListener() {
            @Override
            void onDataChange(DataSnapshot dataSnapshot) {
                callback.delegate = this
                callback.resolveStrategy = Closure.DELEGATE_FIRST
                callback(dataSnapshot, null)
            }

            @Override
            void onCancelled(FirebaseError firebaseError) {
                callback.delegate = this
                callback.resolveStrategy = Closure.DELEGATE_FIRST
                callback(null, firebaseError)
            }
        }
    }

    private ChildEventListener registerChildEventListener(String type, Closure callback){
        new ChildEventListener() {
            @Override
            void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if(type == 'childAdded'){
                    invokeCallback(this, dataSnapshot, previousChildName, null, callback)
                }
            }

            @Override
            void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                if(type == 'childChanged'){
                    invokeCallback(this, dataSnapshot, previousChildName, null, callback)
                }
            }

            @Override
            void onChildRemoved(DataSnapshot dataSnapshot) {
                if(type == 'childRemoved'){
                    invokeCallback(this, dataSnapshot, null, null, callback)
                }
            }

            @Override
            void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            void onCancelled(FirebaseError firebaseError) {
                invokeCallback(this, null, null, firebaseError, callback)
            }
        }
    }

    private invokeCallback(ChildEventListener listener, DataSnapshot dataSnapshot,
                           String previousChildName, FirebaseError firebaseError, Closure callback){
        Map<String, Object> childData = [:]

        if (dataSnapshot) {
            // Convert generic child to Map representation
            GenericTypeIndicator<Map<String, Object>> t = new GenericTypeIndicator<Map<String, Object>>() {}
            childData = dataSnapshot.getValue(t)
        }

        callback.delegate = listener
        callback.resolveStrategy = Closure.DELEGATE_FIRST
        callback(childData, previousChildName, firebaseError)
    }
}