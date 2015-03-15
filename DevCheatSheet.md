# Introduction #

List of **commands** used during project _development_.

# Maven #
## Generic Commands ##

_mvn clean install_ - installs artifact to your local repo

_mvn tomcat7:deploy_ - deploys to tomcat7, executed from webapp directory

_mvn tomcat7:undeploy_ - undeploy

_mvn compile_ - compiles project

_mvn clean_ - cleans _target_ dir

_mvn package_ - build the project

_mvn dependency:resolve -Dclassifier=javadoc_ - attempt to download the Javadocs

_mvn clean test_ - run unit tests

_mvn clean test-compile_ - only compile test sources

_mvn `<goal>` -DskipTests_ - skip the tests via command line

_mvn `<goal>` -Dmaven.test.skip=true_ - If you absolutely must, you can also use the maven.test.skip property to skip compiling the tests. maven.test.skip is honored by Surefire, Failsafe and the Compiler Plugin.

_mvn surefire-report:report_ - generates unit tests report

_mvn site_ - creates project description site

## Attach source & javadoc to artifacts (jars) ##

Configure m2eclipse Maven plugin for Eclipse with the following steps: Window > Preferences > Maven and checking the "_Download Artifact Sources_" and "_Download Artifact JavaDoc_" options

# MySql #

_mysqld --console --verbose_ - starts mysql server

_mysqladmin -u `<user>` -p shutdown_ - stops mysql server, where 

&lt;user&gt;

_is a particular user_

_mysql -u root -p --force < `<sqlscript>`_ - executes SQL script, where 

&lt;sqlscript&gt;

_is SQL scripts file_

_mysqldump --routines --databases `<db_name>` --result-file=`<filePath>`_ - dumps MySQL DB to file, where 

<db\_name>

_is DB instance name,_

&lt;filePath&gt;

_is absolute path to a file._

_mysqldump --skip-triggers --compact --no-create-info --databases `<db_name>` --result-file=`<filePath>`_ - dumps only data (DML)

_mysqldump --routines --no-data --databases `<db_name>` --result-file=`<filePath>`_ - dumps only DDL

# References #

## Maven ##

  * [Skipping Test](http://maven.apache.org/plugins/maven-surefire-plugin/examples/skipping-test.html)
  * [Generate Surefire Report](http://maven.apache.org/plugins/maven-surefire-report-plugin/usage.html)