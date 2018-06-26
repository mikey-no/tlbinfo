Build Notes DRAFT!

1.	Using Play Java Seed

```sbt new playframework/play-java-seed.g8```
```
a.	name:	tlbinfo
b.	organisation: com.example (default)
c.	scala_version: 2.12.6 (default)
d.	play_version: 2.6.15 (default)
e.	sbt_vesion: 1.1.6 (default)
```
2.	```cd .\tlbinfo```
3.	compile and run the application
a.	```sbt run```
b.	….wait…
4.	check the application is working
a.	web browser: http://localhost:9000/
b.	(if relevant) – ignore warnings about old cookies from a previous play application

 success

5.	configure to work with eclipse (see: https://www.playframework.com/documentation/2.6.x/IDE)

```<projectroot>\project\plugins.sbt```
 

6.	Force compilation edit build.sbt

```<projectroot>\build.sbt```

Save changes

7.	Do a compile
a.	```sbt compile```
8.	Do eclipse (to add the info to the project for ecplise to make use of)
a.	```sbt eclipse```

 


9.	Add a gitignore
```<projectroot>\.gitignore```
```
logs
project/project
project/target
target
tmp
dist
.cache

*.class

build
.classpath
.project
.settings
org.scala-ide.sdt.core/META-INF/MANIFEST.MF
org.scala-ide.sdt.update-site/site.xml
```
10.	Initialise version control with 
```<projectroot>\```
```
git
git init
```
11.	Git add all files and commit
```
git add .
git commit –m “Initial commit”
```
12.	Import into Eclipse image 
Click Finish
13.	Prove eclipse and sbt and play are working, edit 

```<projectroot>\app\views\index.scala.html```
 
All text “TLB Info”
14.	Set sbt running for auto compilation
~run
Check in a browser
 Success
15.	Add ebean to the project as our ORM
<project root>\project\plugins.sbt
Add
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.1.3")

see: https://github.com/playframework/play-ebean#releases

Add
```
lazy val myProject = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
```
To
```
<project root>\build.sbt
```
Add seeting to the conf file
```
<project root>\application.conf
```
Use H2 in memory database

16.	Set up the TLB Model (as in data model) add the models to the class path
```
<application root>\app\models\TLB.java
```
And the BaseModel.java

17.	Setup plugins
```
<application root>\project\plugins.sbt
```
Add
```
// Set up for Ebean
// https://github.com/playframework/play-ebean#releases
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "4.1.3")
```
18.	Get error messages for a while the application refuses to compile as it says it cannot find “import io.ebean.Model;”
19.	Run ```sbt clean```
20.	Run ```sbt ~run```
21.	Get error message again a few times
22.	Quit the project
23.	Run another with very similar configuration, with success (doubt your sanity)
24.	Try this project again.
25.	```sbt clean```
a.	Just for luck
26.	Run the project with success
27.	Update the welcome message on the index page
 
28.	Check all code into git
```
git add .
git commit -m "working basic ebean configuration and model added (no repository or use of the model)"
```
-We are not able to use the database model yet.

29.	Add in the repository stuff. This is where the Ebean demo project on play framework site puts the model logic such as the non-blocking database access.

30.	Add a new package
 

31.	Click finish

32.	Add in the “thing” to tell Akka where to get configuration settings and the name of the thread to run DB operations on.
 
33.	Add the class to use the (H2) Database
34.	Note about the blocking call to the database I have left in:

I find it useful to have this when first developing an application just in case making DB calls work asynchronously gets confusing.
Using this in production or at the end of a development sprint would be very bad as one of the main parts of a well behaved micro service is to scale linearly and making blocking calls does not allow this.

35.	Add a simple web page to show the TLB in a list.

36.	Added the other forms and controllers to add TLBs and download the data in Json or see the results in a human readable form… 
37.	The end!
