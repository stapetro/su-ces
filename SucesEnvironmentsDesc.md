# Introduction #

The wiki's goal is to provide detailed description for all environments.

# Development Environment #

  * MySql Community Server 5.5.22 (http://dev.mysql.com/downloads/mysql/)
  * MySql Workbench 5.2.38 CE (http://dev.mysql.com/downloads/workbench/)
  * Apache Tomcat 7.0.26
  * Apache Maven 3.0.4
  * JVM Version 1.7.0\_03-b05
  * Eclipse Juno M5 (http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/junom5)

# Production Environment #
Jelastic PaaS (http://jelastic.com/)

  * MySql 5.5
  * Apache Tomcat 7.x
  * ?Jenkins? (//TODO Kristiyan may give more info on that)

# Project Directory Structure #

  * [{$SUCES\_HOME}/]
    * [branches/]
    * [tags/]
    * [wiki/]
    * [trunk/]
      * [conf/]
      * [db/]
      * [docs/]
      * [scripts/]
        * [dev/]
        * [prod/]
        * [shared/]
      * [su-ces/]
        * [su-ces-model/]
        * [su-ces-web/]
    * [workspaces/]

  * _$SUCES\_HOME_ - project home directory
  * _branches_ -
  * _tags_ -
  * _wiki_ - project documentation
  * _trunk_ - project development mainline
  * _conf_ - configuration files (mvn, tomcat, mysql, etc.)
  * _db_ - ddl/dml SQL scripts
  * _docs_ - documentation to supplement wikis
  * _scripts_ - automation scripts
  * _scripts/dev_ - automation scripts for development
  * _scripts/prod_ - automation scripts for production
  * _scripts/shared_ - same valid automation scripts for both production and development
  * _su-ces_ - parent maven project
  * _su-ces/su-ces-model_ - maven module for data model
  * _su-ces/su-ces-model_ - maven module for webapp
  * _workspaces_ - store eclipse workspaces (SHOULD NOT be under version control)