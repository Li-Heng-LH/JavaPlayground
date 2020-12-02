# Primitive way to create and execute JAR 


## Some Learning Notes ##

### JAR, Non-Executable JAR, Executable JAR ###
* Usually, it's convenient to bundle many Java class files into a single archive file.
* A JAR file can have one entry point set in its manifest file.   
In this case, the JAR file is an executable JAR. 
The main class has to be included in that JAR file.

&nbsp;

### Create Non-Executable JAR without external lib ###
1. **Create class files**.
* `javac -d output me/liheng/TryJar.java`. Note: Package directory structure me/liheng/ will be auto set up in *out* directory. 
2. **Bundle class files into a Non-Executable JAR**.
* `jar cf TryJar.jar -C output me/liheng/TryJar.class`. 
* Alternatively, cd into output/, `jar cf TryJar.jar me/liheng/*.class`
3. **Execute Non-Executable JAR**
* `java -cp TryJar.jar me.liheng.TryJar`.

&nbsp;

### What happens if I did not take care of the directory structure? ###
* In root directory, execute `jar cf TryJar.jar output/me/liheng/*.class`
* We will see "output/me/liheng/TryJar.class" in TryJar.jar.
* Then, it will fail to execute me.liheng.TryJar.

&nbsp;

### Creating a JAR File ###
* jar cf jar-file input-file(s)
* c means create
* f means output go to a file rather than to stdout

&nbsp;

### Viewing the Contents of a JAR File ###
* jar tf jar-file
* t means table of contents
* f means file name is specified on command line

&nbsp;


&nbsp;
----
### Useful links ###
* [Creating a JAR File](https://docs.oracle.com/javase/tutorial/deployment/jar/build.html)
* [Viewing the Contents of a JAR File](https://docs.oracle.com/javase/tutorial/deployment/jar/view.html)
* [Run a Java Application from the Command Line](https://www.baeldung.com/java-run-jar-with-arguments)