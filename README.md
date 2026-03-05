# Java Worker Example

## Overview:
This project demonstrates using the Java Worker with Omnis Studio 11.2 to perform basic image manipulation. An Omnis Studio window allows you to open an image, then rotate and scale it (both of which are not functions available with Omnis). The output image is also shown.

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
5. Open the included Omnis library (available as both .lbs and JSON)

The Omnis application will open the demo window on startup, and you should be able to explore the image transformation code and see it working.

### Adding OmnisCalls
OmnisCalls is distributed as part of your Omnis installation, and is not available from Maven Central. Since its a dependency needed for compilation, it must be made available to Maven. Best practice is to add the jar file to your local maven repository. This allows the Java project to compile correctly, without distributing OmnisCalls alongside it.  
The following command is what I used to accomplish this:  
`mvn install:install-file -Dfile="omnis-writeable-files-folder\javaworker\OmnisCalls\OmnisCalls.jar" -DgroupId="net.omnis" -DartifactId="OmnisCalls" -Dversion="1.0.0" -Dpackaging="jar"`

Maven does include a mechanism to package jar files with the project directly, however it's deprecated and not recommended for use. An example is included in the pom.xml for your interest.

## Extending with your own code
This example is designed as a starting point, to be later extended with your own code. The following notes outline some suggestions for extending this worker, or writing your own code.

If you're looking to extend the capabilities of this worker (e.g. rescaling images to specific dimensions), these are the general steps you would follow.
1) Implement the function in [ImageProcessing.java](/javaworkerexample/src/main/java/au/com/logicaldevelopments/javaworkerexample/ImageProcessing.java). This could either be writing a new function, or updating the existing function to accept additional parameters.
2) Update [Main.java](/javaworkerexample/src/main/java/au/com/logicaldevelopments/javaworkerexample/Main.java) to allow you to test your changes.
3) Once the new changes work correctly, implement support for it in [WorkerInterface.java](/javaworkerexample/src/main/java/au/com/logicaldevelopments/javaworkerexample/WorkerInterface.java). Again, you could implement a new function call (must have the same parameter and return value, but a different name) or update the logic of the existing function to switch depending on the map parameters available.
4) Now update the Omnis classes to match. You will need to modify oImageWorker and oImageHandler to validate and set your new parameters in the worker call.

If your looking to write your own worker with a completely different purpose, the process is different. I'll assume you're adapting this project instead of starting from scratch for the steps below.
1) Integrate the Java code you are adding with this project.
   1) If you have the source files, include them directly amongst the other Java classes
   2) If you are using a module from Maven Central (most open source code), add it to the dependencies section in [pom.xml](/javaworkerexample/pom.xml)
   3) If you are integrating a pre-built module, add it to your local repository in the same way as the OmnisCalls module, and then add it to your Maven dependencies section
2) Create your 'core' class which you can interface with, and a 'main' class to test it with. Verify the code you've written runs correctly
3) Design your worker interface, what parameters do you want, what are the method names?
4) Update the Omnis side to call your new worker interface (if its been renamed you will need to follow that through on the Omnis side as well)
5) Integrate the worker functionality with your application!