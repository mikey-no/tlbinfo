TLB (Template Like Blah) Information Micro Service
=====

**Aims**

Provide an template API application to clients needing information on Template Like Blah. Besides being a template. 

1)	Need a reliable programmatically friendly way to share this information
2)	Need a “hello world” micro service application to test other technologies and ways of working
3)	It needs to be cheaper clients to obtain the blah information than parsing the human readable excel file the information etc
4)	Reduce the data quality problems from not having an easy way to access high integrity Blah information

Requirements

***Provide:***

1.	an API allowing queries for TLB names, TLB Id, TLB Short names to supply the full details of the TLB in JSON form
2.	Be supplied with TLB data at build time in JSON form used in 1.
3.	Show the full list of TLBs in human readable form (just to sanity check the service is working)

**Further Work**
1.	Make this run in  docker container to ease configuration management and deployment
2.	Re-factor code base to match correct organisation structure to java package structure (not com.example)
3.	Improve the gitignore to exclude the eclipse things
4.	Drop requirement 3 above when we are happy this is working to save resources

**Build**
The applications is built using the following:

* Play framework – 2.6.15

* Scala Organisation – 2.12.6

* SBT – 1.1.6 (using mavern)

* OS - Windows 10

* Eclipse - Eclipse Scala IDE: (http://scala-ide.org/)

* Ebean – 11.15.x

* H2 – 1.4 (in memory database)

**Build notes**

[For my notes on creating the application](https://github.com/mikey-no/tlbinfo/blob/master/docs/BUILD.md)

Things I found helpful in addtion to what I found on the play framework and other websites.

**Licence**

Not for commercial use.
Author: mikey-no
