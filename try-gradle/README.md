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
* To build, `gradle build`
* `gradle -q project` : to see the project
* `gradle dependencies` : to see a list of dependencies
* `gradle dependencies --configuration compile` : list compile dependencies
* `gradle dependencies --configuration testCompile` : list testCompile dependencies
* To clean, `gradle clean`

&nbsp;

### Gradle wrapper ###
* a thin layer around gradle
* checks to see if the required version of gradle is installed. Install automatically if not.
* then pass command to real gradle

&nbsp;

### Gradle view tool ###
* Refresh to fetch jars
* Plus to attach as gradle project

&nbsp;

### Gradle tasks ###
* By running `gradle build`, other tasks required to run before it will run. 
* To run the showDate task: `gradle showDate`
* `gradle tasks --all` will show all tasks defined, including those not assigned a group.

&nbsp;

&nbsp;
----
### Useful links ###
* 


