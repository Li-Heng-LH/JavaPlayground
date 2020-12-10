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

### JAR with External Lib: Usual build ###
* Launch java with classpath
* External jars are in .m2 directory
* `mvn clean package`
* `java -cp /Users/liheng/.m2/repository/org/apache/commons/commons-lang3/3.11/commons-lang3-3.11.jar:/Users/liheng/.m2/repository/org/apache/commons/commons-math3/3.6.1/commons-math3-3.6.1.jar:maven-jar-1.0-SNAPSHOT.jar me.liheng.MavenJar`

&nbsp;

### JAR with External Lib: Use Apache Maven Archiver ###
* _maven-jar-plugin_  --> _archive_  --> _manifest_  : 
* `<mainClass>me.liheng.MavenJar</mainClass>`
* `<addClasspath>true</addClasspath>`
* `<classpathPrefix>/Users/liheng/.m2/repository/</classpathPrefix>`
* `<classpathLayoutType>repository</classpathLayoutType>`
* `mvn clean package`
* `java -jar maven-jar-1.0-SNAPSHOT.jar`

&nbsp;

&nbsp;
----
### Useful links ###
* [Guide to Creating and Running a Jar File in Java](https://www.baeldung.com/java-create-jar)
* [Apache Maven Archiver: Set Up The Classpath](https://maven.apache.org/shared/maven-archiver/examples/classpath.html)
* [How to Create an Executable JAR with Maven](https://www.baeldung.com/executable-jar-with-maven)
* [Apache Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/index.html)
