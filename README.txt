This application is an example of using Struts 2, Spring, and Hibernate together.

The application uses Maven to manage it's Jar dependencies.

The application was created using Eclipse 3.5 with the Maven 2 plugin.
The JDK used was JVM 1.6 on the Mac.

The application uses an in memory database (HSQL) that is setup 
by the Spring applicationContext.xml.  See files schema.sql and
test-data.sql in src/main/resources for the schema created 
during the application's startup and the initial records
inserted into the table's database.

You can import this project into Eclipse by using Eclipse's
File - Import feature.

The application can be run inside Eclipse by right clicking on the 
project name and then click on Run As - Run on server.

The application was run on the Tomcat 6 web server.

You can also run the application using Maven.
Navigate in a terminal (command) window to the project's root
folder (the folder where pom.xml resides).

Issue these commands:

mvn clean
mvn test (All 7 tests should pass successfully).
mvn jetty:run

When you see [INFO] Started Jetty Server open a web browser and navigate to
http://localhost:8080/Struts2_Spring_Hibernate_Example/index.action.

To stop the Jetty web server type CTRL-C in the terminal window.

References:

Hibernate Made Easy, Cameron McKenzie, 2008
Spring Recipes, Gary Mak, 2008
Spring Framework Reference Documentation 3.0, http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/

