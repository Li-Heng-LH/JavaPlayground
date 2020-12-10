# Primitive ways to create and execute JAR 


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
* In root directory, execute `jar cf TryJar.jar output`, wanted to package the whole output,   
  But will package all directory structure and .DS_Store as well. 
* In root directory, execute `jar cf TryJar.jar output/me/liheng/*.class`  
  We will see "output/me/liheng/TryJar.class" in TryJar.jar.  
  Then, it will fail to execute me.liheng.TryJar.

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

### Create Executable JAR without external lib (Manifest.txt) ###
* The Jar tool automatically puts a default manifest with the pathname META-INF/MANIFEST.MF into any JAR file you create.
* To modify the manifest, you must first prepare a text file containing the information you wish to add to the manifest.
* The basic command has this format:   _jar cfm jar-file manifest-addition input-file(s)_
* m means merge
* manifest-addition is the name of the existing text file whose contents you want to **add** to the contents of JAR file's manifest.
* _Manifest.txt_ : `Main-Class: me.liheng.TryJar`
* `jar cfm TryJar.jar Manifest.txt -C output me/liheng/TryJar.class`
* `java -jar TryJar.jar`

&nbsp;

### Create Executable JAR without external lib (No Manifest.txt) ###
* `jar cfe TryJar.jar me.liheng.TryJar -C output me/liheng/TryJar.class`  
  The 'e' flag (for 'entrypoint') creates or overrides the manifest's Main-Class attribute. 
* `java -jar TryJar.jar`

&nbsp;

### Create Non-Executable JAR with external lib ###
* `javac -cp lib/* -d output me/liheng/TryJar.java`
* `jar cf TryJar.jar -C output me/liheng/TryJar.class`
* `java -cp lib/*:TryJar.jar me.liheng.TryJar`

&nbsp;

### Create Executable JAR with external lib (Need Manifest.txt) ###
* `javac -cp lib/* -d output me/liheng/TryJar.java`
* _Manifest.txt_ : `Main-Class: me.liheng.TryJar` and `Class-Path: lib/commons-lang3-3.8.1.jar`
* `jar cfm TryJar.jar Manifest.txt -C output me/liheng/TryJar.class`
* `java -jar TryJar.jar`

&nbsp;

&nbsp;
----
### Useful links ###
* [Creating a JAR File](https://docs.oracle.com/javase/tutorial/deployment/jar/build.html)
* [Viewing the Contents of a JAR File](https://docs.oracle.com/javase/tutorial/deployment/jar/view.html)
* [Run a Java Application from the Command Line](https://www.baeldung.com/java-run-jar-with-arguments)
* [Guide to Creating and Running a Jar File in Java](https://www.baeldung.com/java-create-jar)
