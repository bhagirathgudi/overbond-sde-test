# SDE Online Assessment

This project is developed using java as programming language and gradle as a build tool to build a fat jar.

To build this project on local, following are the requirements:
- JDK 8
- SHELL/CMD 

To build this project, go to the root directory and run the command:
```./gradlew clean build```

- This will download all the dependencies and builds a jar file which can be found at build/libs directory.
- Run the jar on local machine using ```java -jar sde-test-1.1.0-SNAPSHOT.jar input.json output.json```.

To run the application using docker, run the following commands in project directory:
- ```docker build . -t sde-test```
- ```docker run sde-test input.json output.json```

First command will build the docker image, first by building a jar from the source code and then adding it to entry point.
Second command will run the docker image.

### Exceptions
- NoArgumentsException : The application needs two arguments to run. So, this exception will be thrown if zero arguments or less than zero are provided.
- IOException : This exception is thrown if a invalid json file is provided as input to the application.
