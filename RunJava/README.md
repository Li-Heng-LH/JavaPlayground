#  RunJava
To test running java through cmd line arguments. 

## Some Learning Notes ##

### Intellij ###
* Run from Intellij will produce "out" folder. 

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
execute `java me.liheng.App`, or `java me/liheng/App`.
* Works with java 8 and 10. 

&nbsp;



&nbsp;
----
### Useful links ###
* [What does “Could not find or load main class” mean?](https://stackoverflow.com/questions/18093928/what-does-could-not-find-or-load-main-class-mean)
* [Setting the Class Path](https://docs.oracle.com/javase/7/docs/technotes/tools/windows/classpath.html)
* [How Classes are Found](https://docs.oracle.com/javase/7/docs/technotes/tools/findingclasses.html)
