# Gradle Learning 

## Some Learning Notes ##

### POM ###
* project object: 
  * 1-1 relationship with build.gradle
  * this object is assigned to 'project' reference variable
  
* task object:
  * project -> task -> action
  * project is a collection of tasks
  * task is a collection of actions 
  * but we usually work on task level
  * usually there is only a single action per task

&nbsp;

### Gradle commands ###
* `gradle project` : to see the project
* `gradle dependencies` : to see a list of dependencies
* `gradle dependencies --configuration compile` : list compile dependencies
* `gradle dependencies --configuration testCompile` : list testCompile dependencies

&nbsp;

&nbsp;
----
### Useful links ###
* 


