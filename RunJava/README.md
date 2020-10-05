#  RunJava
To test running java through cmd line arguments. 

## Some Learning Notes ##

### Intellij ###
* Run from Intellij will produce "out" folder. 
* A typical Java IDE has support for running Java applications in the IDE JVM itself or in a child JVM. 
* These are generally immune from this particular exception: Could not find or load main class,  
because the IDE uses its own mechanisms to construct the runtime classpath, identify the main class and create the java command line.
* A **working directory** in Intellij IDEA: 
  *  **If you have any code that creates relative files or directories, it will be relative to this directory**. 
  * This directory specifies the directory IDEA will read the POM from.

&nbsp;

### The java Command ###
* The java command starts a Java application. 
It does this by starting the Java Virtual Machine (JVM), loading the specified class, and calling that class's main() method. 
* java [options] mainclass [args ...]
* java [options] -jar jarfile [args ...]

&nbsp;

### java Command options ###
* Standard Options for Java: 
Options guaranteed to be supported by all implementations of the Java Virtual Machine (JVM). 
They're used for common actions, such as 
  * checking the version of the **JRE** 
  * setting the class path 
  * enabling verbose output, and so on.
* Extra Options for Java: General purpose options that are specific to the Java HotSpot Virtual Machine. 
They aren't guaranteed to be supported by all JVM implementations, and are subject to change. 
**These options start with -X**. 

&nbsp;

## Run class from out ##

### How Classes are Found ###
* The **Java launcher**, **java**, initiates the Java virtual machine. 
The virtual machine searches for and loads classes in this order:
  * Bootstrap classes
  * Extension classes
  * User classes: **You identify the location of these classes using the -classpath option on the command line** 
  (the preferred method) or by using the CLASSPATH environment variable.

&nbsp;

### How the Java Launcher Finds User Classes ###
* To find user classes, the launcher refers to the user class path 
-- a list of directories, JAR archives, and ZIP archives which contain class files.
* **If the class com.mypackage.MyClass is stored under /myclasses, 
then /myclasses must be in the user class path and the full path to the class file must be 
/myclasses/com/mypackage/MyClass.class.**
* Classes can be stored either in directories (folders) or in archive files. 

&nbsp;

### Classpath ###
* **The class path is the path that the Java Runtime Environment (JRE) searches for classes and other resource files**.
* class search path (more commonly known by the shorter name, "class path") 
* You only need to set the class path when you want to load a class that's 
  * (a) not in the current directory or in any of its subdirectories, and 
  * (b) not in a location specified by the extensions mechanism.
* Using the -classpath option is preferred over setting CLASSPATH environment variable 
because you can set it individually for each application 
without affecting other applications and without other applications modifying its value.

&nbsp;

### Understanding the class path and package names ###
* **Java classes are organized into packages which are mapped to directories in the file system**. 
* Suppose you want the Java runtime to find a class named `Cool.class` in the package `utility.myapp`. 
If the path to that directory is C:\java\MyClasses\utility\myapp, 
**you would set the class path so that it contains `C:\java\MyClasses`**.
* To run that app, you could use the following JVM command:
`java -classpath C:\java\MyClasses utility.myapp.Cool`
* **When the app runs, the JVM uses the class path settings 
to find any other classes defined in the utility.myapp package 
that are used by the Cool class.**
* Note that the entire package name is specified in the command. 
It is not possible, for example, to set the class path so it contains `C:\java\MyClasses\utility` 
and use the command `java myapp.Cool`. The class would not be found.

&nbsp;

### Run ###
* To run the App class from cmd line, navigate to directory just above 'me',   
  * `java me.liheng.App`
  * `java me/liheng/App`
  *  At JavaPlayground directory, `java -cp RunJava/out/production/classes me.liheng.App`
* java me.liheng.App -cp RunJava/out/production/classes will NOT work. 

&nbsp;



&nbsp;
----
### Useful links ###
* [What does “Could not find or load main class” mean?](https://stackoverflow.com/questions/18093928/what-does-could-not-find-or-load-main-class-mean)
* [Setting the Class Path](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/classpath.html)
* [How Classes are Found](https://docs.oracle.com/javase/7/docs/technotes/tools/findingclasses.html)
* [What is a working directory in Intellij IDEA](https://stackoverflow.com/questions/19838334/what-is-a-working-directory-in-intellij-idea#:~:text=3%20Answers&text=This%20is%20the%20directory%20that,be%20relative%20to%20this%20directory.)
* [The java Command](https://docs.oracle.com/en/java/javase/13/docs/specs/man/java.html#standard-options-for-java)
