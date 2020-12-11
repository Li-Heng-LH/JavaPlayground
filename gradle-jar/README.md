#  Explore JARs created by Gradle


## Some Learning Notes ##

### gradle build ###
* `gradle build` task compiles, tests, and assembles the code into a JAR file. 

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


&nbsp;
----
### Useful links ###
* [Creating runnable JAR with Gradle](https://stackoverflow.com/questions/21721119/creating-runnable-jar-with-gradle)
