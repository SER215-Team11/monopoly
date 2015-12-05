#Monopoly [![Build Status](https://travis-ci.org/SER215-Team11/monopoly.svg)](https://travis-ci.org/SER215-Team11/monopoly)
A Monopoly clone written in Java for SER 215.

We do not own the rights to Monopoly. This project is exclusively for
educational purposes.

Monopoly is a Maven project. More information on how to build and run this program can be found in the Wiki. To compile the project, run the following in the root directory.
```
mvn install
```

To run the client, go into the client directory and run the following.
```
mvn exec:java -Dexec.mainClass=io.github.ser215_team11.monopoly.client.App
```

To create a self-contained jar file, run the following in the client directory. The result will be in the target directory.
```
mvn package
```
