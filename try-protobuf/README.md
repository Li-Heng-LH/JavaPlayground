# Protobuf 

## Some Learning Notes ##

### How to compile using maven plugin ###
* Need `protobuf-java` as dependency. Compiled java code need package com.google.protobuf. 
* Need `os-maven-plugin` as extension.
* Need `protobuf-maven-plugin` as plugin
* Can run `mvn protobuf:compile` or   
  `mvn org.xolstice.maven.plugins:protobuf-maven-plugin:0.6.1:compile`
* IDE will look for classpath in target directory.

&nbsp;


&nbsp;
----
### Useful links ###
* [Compile protocol buffers using maven](https://blog.knoldus.com/compile-protocol-buffers-using-maven/)
* [Maven Protocol Buffers Plugin](https://www.xolstice.org/protobuf-maven-plugin/index.html)
* [Introduction to Google Protocol Buffer](https://www.baeldung.com/google-protocol-buffer)

