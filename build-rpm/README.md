# Maven RPM plugin 

## Some Learning Notes ##

### Build Lifecycle ###
* 3 built-in build lifecycles:
  * clean
  * default
  * site

&nbsp;

### Phase ###
* A Build Lifecycle is Made Up of Phases. 
* Clean Lifecycle has 3 phases. 
* Default Lifecycle has 20+ phases.
* Site Lifecycle has 4 phases. 

&nbsp;

### Plugin Goals ###
* A Phase is Made Up of Plugin Goals.
* Depending on what we want to build, a Phase may be bound to different goals. 

&nbsp;

### how to assign tasks to each of those build phases ###
1. Set the packaging of the project  
  For example, the jar packaging will bind certain goals to build phases of the default lifecycle
2. Configure plugins in your project  
  The goals that are configured will be added to the goals already bound to the lifecycle from the packaging selected.  
  For example, the Modello plugin b**inds by default its goal modello:java to the generate-sources phase**.   
  Can also specify in <phase>. 
  
&nbsp;

### maven-assembly-plugin ###
* Need to define phase
* pom.xml of the project is not included into the FAT JAR.
* Note: when the phase is defined to be package, the plugin is added to the goals already bound to package phase (maven-jar-plugin)
* Note: so, **maven-jar-plugin is executed first, then maven-assembly-plugin.** 

&nbsp;

### rpm-maven-plugin ###
* Only with plugin definition, without defining executions and specifying goal, rpm-maven-plugin is not executed. 
* After configured with executions, an RPM is generated from the project whenever the project is packaged. 
* No need to define a phase
* Again, maven-jar-plugin runs first, then rpm-maven-plugin. 

&nbsp;

&nbsp;
----
### Useful links ###
* []()