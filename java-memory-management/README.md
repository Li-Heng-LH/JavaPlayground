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



&nbsp;
----
### Useful links ###
* 


