# Introduction #

This wiki provides a log of all developer activities. As the team is "distributed" team members are required to update their status regularly

# 20 Apr 2012 #
Krasi:
  * create initial skeleton page for course information
  * put the course information items in wiki

# 22 Apr 2012 #
Staskata:
  * Registration form - required validators with custom messages are added.
  * Link to register page is added on top right corner in UI.

# 23 Apr 2012 #
Krasi:
  * created the model for courses - when executing the DDLs there are some strange messages with the index names...need to investigate it...however tables are created successfully
Staskata:
  * Registration form - custom email validator implemented - regex and uniqueness validation
  * No time left for DDL and cyrillic issue with DB

# 25 Apr 2012 #
Staskata:
  * Registration form - Custom validator for confirm password UI component added.
  * Registration status page added.
  * TODO - Look how to preserve view state when some validation fails.

# 26 Apr 2012 #
Staskata:
  * DDL & DML SQL scripts are corrected for suces DB.
  * Two cmd scripts are added for creating suces DB.
  * registration form - UI components are ordered in table.

# 28 Apr 2012 #
Staskata:
  * Scrum mastering - clean obsolete issues, create Release Plan, Knowledge Base wikis, assign issues to owners
  * Login and registration work with password digests
  * [issue 6](https://code.google.com/p/su-ces/issues/detail?id=6) done
  * Start research for unit testing and mocking approaches

# 29 Apr 2012 #
Staskata:
  * [issue 29](https://code.google.com/p/su-ces/issues/detail?id=29) done
  * Scrum mastering - create issues for implementing unit tests for all done user stories.

# 30 Apr 2012 #
Krasi:
  * implement course preview view
  * started admin view for courses

# 1 May 2012 #
Staskata:
  * DevCheatSheet wiki updated - Mvn command properties added for skipping unit tests.

# 2 May 2012 #
Staskata:
  * [issue 32](https://code.google.com/p/su-ces/issues/detail?id=32) done

# 3 May 2012 #
Krasi:
  * Fix feedback form after applied refactoring to the entities
  * recreated the local dev DB. Scripts are OK. Still have the problem with Cyrilic letters
  * I'll prepare DML scripts for the rest of the tables for the next time

# 4 May 2012 #
Krasi:
  * Implement course admin view - need to make some improvements tomorrow

# 5 May 2012 #
Staskata:
  * SessionBean - Checking for user roles implemented
  * Unit test retrieving course by id in CoursePerristence class
Krasi:
  * updated DML scripts with data for courses
  * implemented course administration view
  * started making navigation to feedback form - I'll finish it tomorrow

# 6 May 2012 #
Staskata
  * Scrum mastering - added definition of done and capacity definition, review iteration issues
  * help to Krasi for [issue 36](https://code.google.com/p/su-ces/issues/detail?id=36)

Krasi:
  * finished admin view [issue 26](https://code.google.com/p/su-ces/issues/detail?id=26)
  * made connection from course to feedbackForm
  * fix [issue 36](https://code.google.com/p/su-ces/issues/detail?id=36) (thanks to Staskata for the help)
  * completed user story 5 -> [issue 5](https://code.google.com/p/su-ces/issues/detail?id=5)

# 18 May 2012 #
Staskata
  * Data model changes - course name, course overall assessment added.
  * Changing encoding in MySQL server & DB.
  * Course profile page works with a course name.
  * NOTE: Execute DML script through MySQL workbench or other client (not through command prompt)

# 20 May 2012 #
Krasi
  * Updated new DB model
  * created course search page view
  * made link between selected course and the preview page for it

# 21 May 2012 #
Staskata
  * DB model updated - course rating properties added
  * NOTE: Dev DB should be recreated
  * NOTE: Cannot commit DB model because Google Code reject it. Try to commit it later.
  * Rating functionality implemented in course profile page (course.xhtml)

# 24 May 2012 #
Staskata
  * [issue 30](https://code.google.com/p/su-ces/issues/detail?id=30): Navigation implemented
  * [issue 8](https://code.google.com/p/su-ces/issues/detail?id=8): Course rating start reimplementing - particular user can change or cancel their course rating

Krasi
  * [issue 18](https://code.google.com/p/su-ces/issues/detail?id=18): fix problems with lecturer information

# 25 May 2012 #
Staskata
  * [issue 8](https://code.google.com/p/su-ces/issues/detail?id=8): done
  * NOTE: Recreate dev DB
Krasi
  * [issue 18](https://code.google.com/p/su-ces/issues/detail?id=18): user story implemented
  * update DML with two additional lecturer names

# 26 May 2012 #
Staskata
  * Implementing unit test for CourseAssessmentPersistence
  * Index and constrants duplicate names are fixed in _suces_ DB

# 27 May 2012 #
Staskata
  * Implementing unit tests for CoursePersistence