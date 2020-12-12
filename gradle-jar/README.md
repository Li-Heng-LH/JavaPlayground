#  Explore JARs created by Gradle


## Some Learning Notes ##

### Gradle Revision ###
* Everything in Gradle sits on top of two basic concepts: projects and tasks.
* Every Gradle build is made up of one or more projects. Each project is made up of one or more tasks.   
  A task might be compiling some classes, creating a JAR, generating Javadoc, or publishing some archives to a repository.
* The gradle command looks for a file called build.gradle in the current directory.
* We call this build.gradle file a build script, although strictly speaking it is a build configuration script. 
* Initialization  
  During the initialization phase, Gradle determines which projects are going to take part in the build, and creates a Project instance for each of these projects.
* Configuration  
  During this phase the project objects are configured. 
* Execution  
  Gradle determines the subset of the tasks, created and configured during the configuration phase, to be executed.   
  **The subset is determined by the task name arguments passed to the gradle command and the current directory.**   
  Gradle then executes each of the selected tasks.
* **settings.gradle is executed during the initialization phase**.   
  A multi-project build must have a settings.gradle file in the root project of the multi-project hierarchy. It is required because the settings file defines which projects are taking part in the multi-project build. 
  For a single-project build, a settings file is optional. 
* `gradle build` task compiles, tests, and assembles the code into a JAR file. 

&nbsp;

### Gradle Common tasks ###
* assemble - Assembles the outputs of this project.
* build - Assembles and tests this project.
* clean - Deletes the build directory.
* jar - Assembles a jar archive containing the main classes.
* testClasses - Assembles test classes.

* javadoc - Generates Javadoc API documentation for the main source code.

* dependencies - Displays all dependencies declared in root project.
* projects - Displays the sub-projects of root project.
* tasks - Displays the tasks runnable from root project.

* check - Runs all checks.
* test - Runs the unit tests.

&nbsp;

### Some tricks ###
* To see what tasks are available to run, `gradle tasks`
* To see the details of running a task, use `-i` or `--info` log level flag.
* Example: `gradle build -i` :  
  _Tasks to be executed: [task ':compileJava', task ':processResources', task ':classes', task ':jar', task ':assemble', task ':compileTestJava', task ':processTestResources', task ':testClasses', task ':test', task ':check', task ':build']_

&nbsp;

### Create Non-Executable JAR without external lib ###
* `gradle build`
* JAR is in `build`  -->  `libs`
* `java -cp gradle-jar-1.0-SNAPSHOT.jar me.liheng.GradleJar`

&nbsp;

### Create Executable JAR without external lib ###
* In _build.gradle_, configure the jar task in order to add entry class in manifest. 
* `gradle build`
* `java -jar gradle-jar-1.0-SNAPSHOT.jar` 

&nbsp;

### Create Non-Executable JAR with external lib ###
* `gradle build`
* _java -cp gradle-jar-1.0-SNAPSHOT.jar:PATH_TO_LIBS me.liheng.GradleJar_

&nbsp;

### Create Fat JAR with external lib ###
* Basically, a fat jar (also known as uber-jar) is a self-sufficient archive 
which contains both classes and dependencies needed to run an application.
* 

&nbsp;

&nbsp;
----
### Useful links ###
* [Creating runnable JAR with Gradle](https://stackoverflow.com/questions/21721119/creating-runnable-jar-with-gradle)
* [Creating a Fat Jar in Gradle](https://www.baeldung.com/gradle-fat-jar)
