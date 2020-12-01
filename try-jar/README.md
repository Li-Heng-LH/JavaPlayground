# Primitive way to create and execute JAR 


## Some Learning Notes ##

### Create Non-Executable JAR without external lib ###
1. **Create class files**.
* `javac -d output me/liheng/TryJar.java`. Note: Package directory structure me/liheng/ will be auto set up in *out* directory. 
2. **Bundle class files into a Non-Executable JAR**.
* `jar cf TryJar.jar -C output me/liheng/TryJar.class`. 
* Alternatively, cd into output/, `jar cf TryJar.jar me/liheng/*.class`
3. **Execute Non-Executable JAR**
* `java -cp TryJar.jar me.liheng.TryJar`.

&nbsp;

### Non-Executable JAR VS Executable JAR ###

&nbsp;

&nbsp;
----
### Useful links ###
* []()