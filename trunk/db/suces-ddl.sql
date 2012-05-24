/*
-- Suces Database Schema
-- Version 0.1

-- Copyright (c) 2012, Sofia University, Faculty of Mathematics and Informatics
-- All rights reserved.

-- Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

--  * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
--  * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
--  * Neither the name of MySQL AB nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `suces` ;
CREATE SCHEMA IF NOT EXISTS `suces` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
SHOW WARNINGS;
USE `suces` ;


DROP TABLE IF EXISTS `suces`.`courses_grade` ;
DROP TABLE IF EXISTS `suces`.`grade` ;
DROP TABLE IF EXISTS `suces`.`courses_specialty` ;
DROP TABLE IF EXISTS `suces`.`specialty` ;
DROP TABLE IF EXISTS `suces`.`course_assessment` ;
DROP TABLE IF EXISTS `suces`.`courses` ;
DROP TABLE IF EXISTS `suces`.`lecturer` ;
DROP TABLE IF EXISTS `suces`.`semester` ;
DROP TABLE IF EXISTS `suces`.`user_roles` ;
DROP TABLE IF EXISTS `suces`.`roles` ;
DROP TABLE IF EXISTS `suces`.`users` ;

-- -----------------------------------------------------
-- Table `suces`.`users`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`users` (
  `user_email` VARCHAR(64) NOT NULL COMMENT 'used for login name' ,
  `password` VARCHAR(128) NOT NULL COMMENT 'hash value of password' ,
  `create_date` DATETIME NOT NULL ,
  `last_login_date` DATETIME NULL ,
  PRIMARY KEY (`user_email`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`roles`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`roles` (
  `role_name` VARCHAR(64) NOT NULL COMMENT 'user role name' ,
  `description` VARCHAR(256) NULL ,
  PRIMARY KEY (`role_name`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`users_roles`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`users_roles` (
  `user_email` VARCHAR(64) NOT NULL ,
  `role_name` VARCHAR(64) NOT NULL ,
  PRIMARY KEY (`user_email`, `role_name`) ,
  CONSTRAINT `fk_urole_users`
    FOREIGN KEY (`user_email` )
    REFERENCES `suces`.`users` (`user_email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_urole_roles`
    FOREIGN KEY (`role_name` )
    REFERENCES `suces`.`roles` (`role_name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_urole_users` ON `suces`.`users_roles` (`user_email` ASC) ;

SHOW WARNINGS;
CREATE INDEX `fk_urole_roles` ON `suces`.`users_roles` (`role_name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`semester`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`semester` (
  `semester_id` INT NOT NULL AUTO_INCREMENT ,
  `semester_name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`semester_id`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `semester_name_UNIQUE` ON `suces`.`semester` (`semester_name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`lecturer`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`lecturer` (
  `lecturer_id` INT NOT NULL AUTO_INCREMENT ,
  `lecturer_name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`lecturer_id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`courses`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`courses` (
  `course_id` INT NOT NULL AUTO_INCREMENT ,
  `year` INT NULL ,
  `semester_semester_id` INT NOT NULL ,
  `workload` INT NULL ,
  `lecturer_lecturer_id` INT NOT NULL ,
  `course_annotation` MEDIUMTEXT NULL ,
  `preliminary_requirements` MEDIUMTEXT NULL ,
  `examination_form` MEDIUMTEXT NULL ,
  `summary` MEDIUMTEXT NULL ,
  `literature` MEDIUMTEXT NULL ,
  `course_name` VARCHAR(255) NOT NULL ,
  `rating` DOUBLE NULL ,
  `rating_counter` INT NOT NULL DEFAULT 0 ,  
  PRIMARY KEY (`course_id`) ,
  CONSTRAINT `fk_courses_semester1`
    FOREIGN KEY (`semester_semester_id` )
    REFERENCES `suces`.`semester` (`semester_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_lecturer1`
    FOREIGN KEY (`lecturer_lecturer_id` )
    REFERENCES `suces`.`lecturer` (`lecturer_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_courses_semester1` ON `suces`.`courses` (`semester_semester_id` ASC) ;

SHOW WARNINGS;
CREATE INDEX `fk_courses_lecturer1` ON `suces`.`courses` (`lecturer_lecturer_id` ASC) ;

SHOW WARNINGS;
CREATE UNIQUE INDEX `course_name_UNIQUE` ON `suces`.`courses` (`course_name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`course_assessment`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `suces`.`course_assessment` (
  `course_assessment_id` INT NOT NULL AUTO_INCREMENT ,
  `course_presentation` INT NULL DEFAULT NULL ,
  `course_understanding` INT NULL DEFAULT NULL ,
  `course_attitude` INT NULL DEFAULT NULL ,
  `students_attitude` INT NULL DEFAULT NULL ,
  `assessment_correctness` INT NULL DEFAULT NULL ,
  `course_organization` INT NULL DEFAULT NULL ,
  `course_usefullness` INT NULL DEFAULT NULL ,
  `course_in_plan` INT NULL DEFAULT NULL ,
  `course_difficulty` INT NULL DEFAULT NULL ,
  `course_engagements` INT NULL DEFAULT NULL ,
  `course_rated` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Stores if user has rated a course' ,  
  `course_rating` DOUBLE NULL ,  
  `courses_course_id` INT NOT NULL ,
  `users_user_email` VARCHAR(64) NOT NULL ,
  PRIMARY KEY (`course_assessment_id`) ,
  CONSTRAINT `fk_course_assessment_courses1`
    FOREIGN KEY (`courses_course_id` )
    REFERENCES `suces`.`courses` (`course_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_assessment_users1`
    FOREIGN KEY (`users_user_email` )
    REFERENCES `suces`.`users` (`user_email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_course_assessment_courses1` ON `suces`.`course_assessment` (`courses_course_id` ASC) ;

SHOW WARNINGS;
CREATE INDEX `fk_course_assessment_users1` ON `suces`.`course_assessment` (`users_user_email` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`specialty`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`specialty` (
  `specialty_id` INT NOT NULL AUTO_INCREMENT ,
  `specialty_name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`specialty_id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`courses_specialty`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`courses_specialty` (
  `courses_course_id` INT NOT NULL ,
  `specialty_specialty_id` INT NOT NULL ,
  PRIMARY KEY (`courses_course_id`, `specialty_specialty_id`) ,
  CONSTRAINT `fk_courses_has_specialty_courses1`
    FOREIGN KEY (`courses_course_id` )
    REFERENCES `suces`.`courses` (`course_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_has_specialty_specialty1`
    FOREIGN KEY (`specialty_specialty_id` )
    REFERENCES `suces`.`specialty` (`specialty_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_courses_has_specialty_specialty1` ON `suces`.`courses_specialty` (`specialty_specialty_id` ASC) ;

SHOW WARNINGS;
CREATE INDEX `fk_courses_has_specialty_courses1` ON `suces`.`courses_specialty` (`courses_course_id` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`grade`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`grade` (
  `grade_id` INT NOT NULL AUTO_INCREMENT ,
  `grade_number` INT NOT NULL ,
  PRIMARY KEY (`grade_id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`courses_grade`
-- -----------------------------------------------------

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`courses_grade` (
  `courses_course_id` INT NOT NULL ,
  `grade_grade_id` INT NOT NULL ,
  PRIMARY KEY (`courses_course_id`, `grade_grade_id`) ,
  CONSTRAINT `fk_courses_has_grade_courses1`
    FOREIGN KEY (`courses_course_id` )
    REFERENCES `suces`.`courses` (`course_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_has_grade_grade1`
    FOREIGN KEY (`grade_grade_id` )
    REFERENCES `suces`.`grade` (`grade_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_courses_has_grade_grade1` ON `suces`.`courses_grade` (`grade_grade_id` ASC) ;

SHOW WARNINGS;
CREATE INDEX `fk_courses_has_grade_courses1` ON `suces`.`courses_grade` (`courses_course_id` ASC) ;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
