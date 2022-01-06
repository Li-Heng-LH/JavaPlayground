#  Try RxJava


## Some Learning Notes ##

### Reactive Extensions ###
* Reactive Extensions, ReactiveX, Rx
* Basically, asynchronous programming. 
* RxJava is the Java implementation of this concept. 
* RxJava provides Java API for asynchronous programming with observable streams.

&nbsp;

### Concepts ###
* **Observable** represents any object that can get data from a data source   
  and whose state may be of interest in a way that other objects may register an interest
* An **observer** is any object that wishes to be notified when the state of another object changes
* In Rx, an observer will never be called with an item out of order    
  or called before the callback has returned for the previous item.
* Observable Categories:  
  * Unbounded list of events (e.g UI events)
  * Single Variable (e.g title change after data update)
  * Tasks of code (e.g network tasks)
  * Bounded array (list of User ID's to process)

&nbsp;

### Observables ### 
* `Observable<T>`: Emits 0 or n items and terminates with success or error event.
* `Flowable<T>`: Emits 0 or n items and terminates with success or error event.   
  Supports backpressure, which allows to control how fast a source emits items.
* The Observable type is lazy, meaning it does nothing until it is subscribed to. 
* Because Observable is lazy, Observables can be reused. 
* Observable represents a flowing sequence of events. 
* Observable is push-based, meaning that it decides when to produce values. 
* Observable<T> can actually produce 3 types of events: 
  1. values of type T,
  2. completion event, 
  3. error event
* Every Observable can emit an arbitrary number of values optionally followed by completion **or** error (but not both) 
* Because of asynchronous nature of Observable, they emit error event, instead of exceptions. 
* 

&nbsp;

&nbsp;
----
### Useful links ###
* [Using RxJava 2 - Tutorial](https://www.vogella.com/tutorials/RxJava/article.html)
* [Introduction to RxJava](https://www.baeldung.com/rx-java)
* [RxJava: Design Patterns for Android Developers](https://www.linkedin.com/learning-login/share?forceAccount=false&redirect=https%3A%2F%2Fwww.linkedin.com%2Flearning%2Frxjava-design-patterns-for-android-developers%3Ftrk%3Dshare_ent_url%26shareId%3DTGqj2CQTRGmKC2tkmpaLSA%253D%253D)
