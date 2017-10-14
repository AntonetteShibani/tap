[![Build Status](https://travis-ci.org/uts-cic/tap.svg?branch=master)](https://travis-ci.org/uts-cic/tap) [![https://img.shields.io/badge/license-Apache%202.0-blue.svg](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
                                                                                                                                                   
# Text Analytics Pipeline (TAP)

This is version 3 of TAP redesigned to use Athanor Server and to provide a GraphQL interface for AWA3.

TAP is the server side code for a full stack: [AWA](http://awa.uts.edu.au), a web application for Academic Writing Analytics. The application was developed by the [Connected Intelligence Centre](http://utscic.edu.au), with the original software developed by [Andrew Gibson](http://GitHub.com/andrewresearch).
 
 Originally constructed as a REST API, this repo is a re-write of TAP using [Play Framework](https://www.playframework.com), [Sangria GraphQL](http://sangria-graphql.org) and [GraphiQL](https://github.com/graphql/graphiql).

# Quick Start

A summary of the start up steps are :

1. Get a copy of the project source code
2. Download sbt and/or the play framework if they are not already installed on your system
3. Run the project using sbt run or Play
4. Connect to localhost:9000 via the browser

# Products and Tools that are required to build and run TAP

The following products are required to build and run TAP, and to help you contribute to the TAP
project.
They are basically the tools required to run an SBT play application.
Other dependencies are also specified in build.sbt and will be automatically
downloaded during the first build.

Many of the tools/products will be downloaded during the build as build.sbt dependencies
or indirectly by other products.
Perhaps you want to **start with cloning and building TAP, then refer to the list in
this section in response to errors to see what you might be missing**.

1. Git

    You will need the Git source control command line tools and/or a Git gui
    interface in order to clone and contribute to the project.
    These are available either through your software installation tool such as Synaptic or
    from the [Git website](https://git-scm.com).

2. sbt

    sbt will build the project and download much of the needed software for us, but if it is not
    on our system already, we need to download it first.
    Even if sbt is already installed, we may need to use a different level of sbt to do the build.

    Once you downloaded the code, position in the ~/tap directory, and issue the following command :

        sbt about

    in the ~/tap directory.
    You should get the following output :

        [info] This is sbt 0.13.16
        [info] The current project is {file:~/tap/}tap 3.0.4or
        [info] The current project is built against Scala 2.12.3

    If the sbt version is different from than 0.13.16, wait until the first compile is done, and issue
    the check again as the sbt build should download the correct version for you.
    If not, download the the 0.13.16 version that is used in the project from the [sbt website](http://www.scala-sbt.org/)

3. Scala

    The Scala compiler version used to compile the code is 2.12.3.
    This should match the run-time Scala version as Scala run-time is not
    backward compatible.
    This should be taken care of by the build.sbt file on the first compile.

    You can issue the following command to check your Scala version:

        scala -version

    This is the expected output:

        Scala code runner version 2.12.3 -- Copyright 2002-2017, LAMP/EPFL and Lightbend, Inc.:

4. Java SE Development Kit (JDK) and Java run-time (JRE)

    Check that you have an up to date version of the JRE.
    It should be at least 1.8 (The build is tested using 1.8), otherwise you have to update
    the Java run time on your machine.

    Issue:

        java -version

    The JDK is also needed to compile java files in the project, and should also be at the 1.8
    level. Issue :

        javac -version

    If the JDK or JRE are missing or at an older level, the 1.8 level can be downloaded
    from [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

    Installing the JDK should take care of installing the JRE also.

    You should provide enough memory for the JDK and JRE to build and run this project.
    The following values are suggested :

       -Xmx2048M -Xss1M

5. Intellij IDEA

    You do not have to use this, as you can build of the command line and use your favourite editors,
    or use a different IDE.
    It is however the preferred IDE for this project.
    If you use a different IDE you have to ensure any additional IDE files are added to the
    .gitignore file and are not added to the project.

    The Intellij IDE Community Edition is free and can be downloaded from [INTELLIJ-CE](https://www.jetbrains.com/idea/download/index.html#section=linux)

6. The Play Framework

    This can be downloaded from the [Play Framework website](https://playframework.com).

    Press on the Download 2.6.6 button and follow the appropriate instructions for your
    operating system.

# Getting a local copy of TAP

1. Clone the github repository to your local repository copy on your PC.
Position to the directory where you want the local repository placed
(Your home directory is fine) and issue the following command.
This will create a copy of the repository in the tap directory.

        git clone https://github.com/uts-cic/tap.git

2. The following jar files should also be placed in the lib directory:
   (Note that the models jar is relatively large at around 750M so it might take
   some time to download depending on your internet speed).


        [tap-models-jar](https://s3-ap-southeast-2.amazonaws.com/dev-tap-cic-uts/cc.factorie.app.nlp.all-models-1.0.0.jar)
        [tap-factorie-jar](https://s3-ap-southeast-2.amazonaws.com/dev-tap-cic-uts/nlpfactorie_2.12-0.1.jar)

    Copy the files to the lib directory:

        cd ~/tap
        mkdir -p lib
        cd lib
        wget https://s3-ap-southeast-2.amazonaws.com/dev-tap-cic-uts/cc.factorie.app.nlp.all-models-1.0.0.jar
        wget https://s3-ap-southeast-2.amazonaws.com/dev-tap-cic-uts/nlpfactorie_2.12-0.1.jar

# Project Structure

Although TAP is a Play project, the project structure is that of a Maven/sbt project
and does not follow the Play default project structure.

The build.sbt file indicates that we are using an alternative project structure to Play.

        disablePlugins(PlayLayoutPlugin)
        PlayKeys.playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value

For more details, see the "Default SBT Layout" section in the "Anatomy of a Play Application"
chapter in the Play 2.6 Documentation:

        [play-project-anatomy](https://playframework.com/documentation/2.6.x/Anatomy)



# Running of the Command Line

Although using an IDE such as Intellij IDEA might be easier, starting with
a command line build might be better, since you do not have to also deal
with an IDE set-up, especially if the IDE is not familiar to you.
It also gives you an alternative way of doing the build.
INTELLIJ IDEA set-up and build instructions are also included, so you
can start there, and skip the command Line build for now if you wish.


## Building TAP

1. Move to the directory where you placed the tap product. Assuming it is
in the home directory, you should see a build.sbt file containing instruction
on how the build is done, as well as this README.md file:

        cd ~/tap

2. Compile the product.

     The first compile will be very slow as dependencies have to be fetched and placed
     in the ~/.ivy2 directory.

         sbt compile

     The following compile warnings seem to be safe to ignore, but ensure the
     last compile ends with [SUCCESS] :

        com.typesafe.akka:akka-stream_2.12:2.5.6 is selected over {2.5.4, 2.5.3, 2.4.19}
        com.typesafe.akka:akka-actor_2.12:2.5.6 is selected over {2.5.4, 2.4.19}
        com.google.guava:guava:22.0 is selected over 19.0
        ...srcc/main/scala/handlers/GraphQlHandler.scala:42: match may not be exhaustive.
        ...
        there were 8 feature warnings; re-run with -feature for details
        two warnings found^[[0m
        Total time: 11 s, completed Oct 7, 2017 12:39:37 PM^[[0m


      If you get the following compile errors, it means the jars that TAP relies are not
      being accessed from the lib directory.
      Ensure the missing jars are installed, as explained in the
      "Getting a local copy of TAP" section.

        import cc.factorie.app.nlp._
        ~/tap/src/main/scala/au.edu.utscic.tap/nlp/factorie/Annotator.scala:28: not found: value DocumentAnnotatorPipeline

## Running TAP

1. Move to the directory where you placed the tap product. Assuming it is
in the home directory:

        cd ~/tap

2. Ensure the JVM has enough memory to run the application.

        export _JAVA_OPTIONS="-Xmx2048M -Xss1M"

3. You can either run TAP using either of the following methods:

    - Using sbt:

        sbt run

    - Using play:
        play

        If you get an exception in the SBtParser as in the following lines :

            java.lang.UnsupportedOperationException: Position.start on class scala.reflect.internal.util.OffsetPosition
            at scala.reflect.internal.util.Position.start(Position.scala:114)
            at sbt.internals.parser.SbtParser.sbt$internals$parser$SbtParser$$convertStatement$1(SbtParser.scala:148)

        This means Play is picking up an old SBT Parser that cannot deal with parsing the latest layout of the SBT files
        as explained in this exchange: [play-parse-bug](https://github.com/sbt/sbt/issues/1739)

        In order to avoid this error, and run the application with play, you should update play to use the 2.10.4
        Parser which is the minimum level parser that fixes the error.
        This update will take a long time to run, but is a one off.

            SBT_SCALA_VERSION=2.10.4 play update

        You then have to run play always specifying the SBT Scala version as described in the next step.

    - Using play, but determining the SBT Scala version

          This is necessary if you want to run play and need to avoid the SbtParser exception.

              SBT_SCALA_VERSION=2.10.4 play run

    After you run the TAP Server, you should see the following lines, indicating that the server is
    running and listening to requests :

        Picked up _JAVA_OPTIONS: -Xms512M -Xmx2048M -Xss1M
        [info] Loading global plugins from ~/.sbt/0.13/plugins
        [info] Loading project definition from ~/tap/project
        [info] Set current project to tap (in build file:~/tap/)
        --- (Running the application, auto-reloading is enabled) ---
        [info] p.c.s.AkkaHttpServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

    You can now connect to the server by typing the following in your browser:

        http://localhost:9000/

    The product should start and you can use it.
    You should see the following in your browser.

        TAP Server with GraphQL
        Use the graphiql IDE here

    To end the server, close your browser session then type return in your console
    where the server is running.
    The following message should appear on the console.

        [info] p.c.s.AkkaHttpServer - Stopping server...

# Setting the TAP project in Intellij IDEA

An IDE provides a more convenient and efficient way of doing development.
The Intellij IDEA is preferred because of its Scala support, and relative ease
of use.

1. Check that the Scala and SBT plugins are installed.
   Install them if they are missing.

         File-Settings-Plugins

2. Import the project into Intellij IDEA

         File - New - Project from Existing sources

         Navigate to the build.sbt file in the tap directory where you cloned the project source code.


3. Ensure the JVM has enough memory to work with (-Xmx2048M -Xss1M -Xmx512M)

         File - Settings - Other Settings - SBT - VM Parameters

4. Add the library dependencies

         File - Project Structure - Modules

         Point to and add the jars you imported into the lib directory:

         cc.factorie.app.nlp.all-models-1.0.0.jar
         nlpfactorie_2.12-0.1.jar

5. Run Tap

        Run - Run Tap


### Documentation

For more detailed documentation on both TAP API and AWA client components, see the current [docs](//).

### Submitting bugs and suggestions

Please use [GitHub issues](../../issues) to notify us of a bug or to request a new feature. Before adding a new request, *please* search the existing issues to see if there is one the same or similar to yours. If so, add a [reaction](//github.com/blog/2119-add-reactions-to-pull-requests-issues-and-comments) (like :+1: or :-1:) to the issue and post any additional relevant comments that will be helpful.

### Contributing

Contributions to the code are welcome. Please read [CONTRIBUTING](CONTRIBUTING.md) for more information.

### License

 > &copy; University of Technology Sydney 2016-2017
 >
   > Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
   >
   > [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
   >
   > Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
