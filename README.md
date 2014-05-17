Grails Firebase Plugin
----------------------

[Firebase](https://www.firebase.com/) is an API to store and sync data in realtime. 

When data changes, apps built with Firebase update instantly across every device including web and mobile.
Firebase-powered apps work offline. Data is synchronized instantly when app regains connectivity.

[Data Structure](https://www.firebase.com/docs/data-structure.html) in firebase is designed to be represented by a specific URL and the client can register a listener/callback to accomplish an event when a particular element has changed/added/removed. This plugin is a grails wrapper over the Firebase Java client to accomplish the callback functionalitly on modification of data. 

Firebase is not only targeted for client apps but also for servers. [This blog shows](https://www.firebase.com/blog/2013-03-25-where-does-firebase-fit.html) shows where in a technology stack firebase fits in.

Installation
------------

```groovy
plugins {
    compile ':firebase:0.0.1'
}
```

Usage
-----

This plugin registers a bean `firebaseRef` which extends [Firebase](https://www.firebase.com/docs/java-api/javadoc/index.html) and implements methods in a Groovier way.

**Steps:**

1. Sign up in Firebase.com to create a Firebase Data Structure.
2. Use the data structure id `abcd-fire-7000` from the URL for example (https://abcd-fire-7000.firebaseio.com) in the below config setting in `Config.groovy`

**Example:**

```groovy
grails {
    plugin {
        firebase {
            base = "abcd-fire-7000"
        }
    }
}
```
    
To use firebase inject `firebaseRef` in any of the grails artifact:


```groovy
//Service class
def firebaseRef

def addValue(){
    firebaseRef.child("first").value = "John"
    firebaseRef.child("last").value = "Doe"

    // Value Event Listener
    // Closure callback takes two parameters
    // 1. The value that was change (null in case of error)
    // 2. Errors if any during update (null in case of success)
    firebaseRef.on('value'){ data, error ->
        println "first name is ${data.value}"
    }
}

def method() {

    // Writing data as a child
    firebaseRef.value  = [
            address: [
                    street: '123 Main Street',
                    apt: 123,
                    city: 'Columbus',
                    zip: 43230,
                    state: 'OH',
                    country: 'USA'
            ]
    ]
    
    // Child Event Listener for add event
    // Closure callback takes three parameters
    // 1. Map representation of the child that was added (null in case of error)
    // 2. Previous name of child (null in case of addition)
    // 2. Errors if any during update (null in case of success)
    firebaseRef.onChildAdded { data, previous, error ->
        println "Add callback"
        println data
        println previous
        println error
    }

    // Child Event Listener for change event
    // Closure callback takes three parameters
    // 1. Map representation of the child that was changed (null in case of error)
    // 2. Previous name of child (null in case of addition)
    // 2. Errors if any during update (null in case of success)
    firebaseRef.on('childChanged'){ data, previous, error ->
        println "Change callback"
        println data
        println previous
        println error
    }

    // Child Event Listener for remove event
    // Closure callback takes three parameters
    // 1. Map representation of the child that was removed (null in case of error)
    // 2. Previous name of child (null in case of addition)
    // 2. Errors if any during update (null in case of success)
    firebaseRef.on('childRemoved'){ data, previous, error ->
        println "Remove callback"
        println data
        println previous
        println error
    }
    
    //Additional ways to use for Child Events
    firebaseRef.onChildChanged{ }
    firebaseRef.onChildRemoved{ }
    
    //Additional ways to used for Value Event
    firebaseRef.onValue { }
    
    //Listener for single value event (only applicable for value)
    firebaseRef.once { }
}
```
    
    
TODO:
-----

 - Add transactional feature
 - Add authentication
 - Add offline/online mode
 - Add GORM features

License
-------
APACHE 2.0
