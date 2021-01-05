# Java Memory Management
Learning from the course: [Java Memory Management](https://www.linkedin.com/learning-login/share?forceAccount=false&redirect=https%3A%2F%2Fwww.linkedin.com%2Flearning%2Fjava-memory-management%3Ftrk%3Dshare_ent_url)

## Some Learning Notes ##

### JVM Stack and Heap ###
* Heap is like a huge area for storing data. 
* Can think of the heap being all the memory for the application except for the data on the stack. 
* In an application there is one heap which is shared across all threads, and a number of stacks, one for each thread. 
As shown below.  
![java_memory](docs/%20java_memory.png)
* Heap is a large amount of memory space compared to stacks.
* Heap makes it easy to pass objects around threads or code blocks. 

&nbsp;

* **All objects are stored on the heap.**
* **Stacks store local primitive variables, and object references.**

&nbsp;

### Code walkthrough ###
![memory_illustration](docs/memory_illustration.png)

&nbsp;

### Pass by Value ###
* values are copied when passing into functions. 
* object references are copied when passing into functions. 
* The `final` keyword:   
  * **variable can only be assigned once**. 
  * once it has been assigned, it cannot be altered. 
* The can and cannot of `final`: 
  * Can change value from uninitialised to having a value. 
  * Can change state of object. 
  * Cannot reassign.

&nbsp;

### Escaping References with collections ###
* This happens when we have methods that return pointer to the internal object.   
  In this case, getCustomers() that returns private records.
* Solution 1: Make internal object Iterable. 
  * Issue: remove() method of iterator, which still mutates.
* Solution 2: Return a copy of the internal object. 
  * Issue: Still can mutate the underlying object of the original collection. 
  * As the copy is a shallow copy. 
  * also, cause confusion: ppl may think we are dealing with the original copy. 
* Solution 3: Return immutable collections: `unmodifyableMap`, `unmodifyableList`. 

&nbsp;

### Escaping References by returning copy of custom object ###
* However, this can be confusing as client may think he is changing the original copy. 

&nbsp;

### Escaping References by returning a read-only version ###
* Interface that only has getter methods.

&nbsp;

### Escaping References ###
* Is fine if return type is primitive or String, or the class is immutable/read-only.
* If class is mutable, make class implement a read-only interface.

&nbsp;

### Garbage Collection Intro ###
* Stack is efficient. Java can manage it easily. 
* Some languages allow users to choose to create objects on either stack or heap.
* Java does not. It tries to provide a single, clean way of doing things. 
* Actually, modern VM is very efficient and clever. If it detects the object created is not going to be shared,
VM will in fact create the object in stack.   
  But in general, all objects are stored on the heap. Stacks store local primitive variables, and object references.
* VM tries to make the most efficient choice for us. So our code will generally run in an optimised way. 

&nbsp;

### String Pool ###
* There is no harm for 2 variables to point to the same String literal object in the heap, 
* because **String is immutable**. 
* JVM puts Strings into a pool, and reuse them whenever it can. 
* In general, this only happens with String literals, and concated Strings.   
  Does not apply to Strings that are calculated from something else. 
* String's `intern()` method: force VM to place String in the pool. 

&nbsp;

### Garbage Collection Eligibility ###
* In C or C++, we need to state when an object is no longer needed, by calling a method like free().
* In VB, need to set the object to null. 
* If programmer did not release the memory, the memory will never be released.   
  Even when programme finishes, the memory will still be in use.   
  The only way to free it up is to restart computer. 
  This will result in memory leak. 
* Java avoids memory leaks by:   
  * Running on a virtual machine.   
  Assuming JVM is implemented correctly, memory leaks in OS-level should be impossible. 
  * Adopting GC strategy. 
  
* Any object on the heap that cannot be **reached** through a reference from the stack is eligible for garbage collection. 
* Note: it is incorrect to just say "unreferenced" object. 
* **It is possible for a referenced object to be garbage**.   
  For example, referenced objects in an unreachable collection.   
  Another example, circular references. 

&nbsp;

### gc() and finalize() ###
* In general GC should be treated as an automated process. 
* gc() will tell JVM to run GC process, but there is no guarantee that the VM will do. 
* gc() may be useful when we need to compare 2 methods. Call gc() to ensure clean start env. 

&nbsp;

&nbsp;
----
### Useful links ###
* [Copying a HashMap in Java](https://www.baeldung.com/java-copy-hashmap)


