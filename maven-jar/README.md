#  Explore JARs created by Maven


## Some Learning Notes ##

### maven.compiler.source and maven.compiler.target ###
* Maven will use the jdk in the computer to compile. 
* Can check the version of jdk maven is using : `mvn -version`
* If jdk is at version 8, and maven.compiler.source, maven.compiler.target is 11,   
  build will fail. 

&nbsp;

### Create Non-Executable JAR without external lib ###
* `mvn clean package`
* JAR is in _target_ directory
* `java -cp maven-jar-1.0-SNAPSHOT.jar me.liheng.MavenJar`

&nbsp;

### Create Executable JAR without external lib ###
* Add _mainClass_ to _maven-jar-plugin_
* `mvn clean package`
* `java -jar maven-jar-1.0-SNAPSHOT.jar`

&nbsp;

&nbsp;
----
### Useful links ###
* [Guide to Creating and Running a Jar File in Java](https://www.baeldung.com/java-create-jar)
