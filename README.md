# Java Worker Example

## Overview:
This project demonstrates using the Java Worker with Omnis Studio 11.2 to perform basic image manipulation. Within Omnis a window allows you to open a image, then rotate and scale it (both of which are not functions available with Omnis). The output image is also shown.

![Demo screenshot](/media/DemoScreenshot.png)

## Dependencies:
- Java 17 or above
- Maven
- Omnis Studio 11.2

## Installation
1. Ensure all dependencies are installed
2. Add OmnisCalls to your local maven repository (see section below)
3. Run `mvn package` in the javaworkerexample directory
4. Copy target/javaworkerexample-1.0.jar and target/lib to omnis-writeable-files-folder/javaworker/JavaWorkerExample
5. Open the included Omnis library
6. Use the program to import and transform an image

### Adding OmnisCalls
OmnisCalls is distributed as part of your Omnis installation, and is not available from Maven Central. Since its a dependency needed for compilation, it must be made available to Maven. Best practice is to add the jar file to your local maven repository. This allows the Java project to compile correctly, without distributing OmnisCalls alongside it.  
The following command is what I used to accomplish this:  
`mvn install:install-file -Dfile="omnis-writeable-files-folder\javaworker\OmnisCalls\OmnisCalls.jar" -DgroupId="net.omnis" -DartifactId="OmnisCalls" -Dversion="1.0.0" -Dpackaging="jar"`


Maven does include a mechanism to package jar files with the project directly, however it's deprecated.