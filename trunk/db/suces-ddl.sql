-- Suces Database Schema
-- Version 0.1

-- Copyright (c) 2012, Sofia University, Faculty of Mathematics and Informatics
-- All rights reserved.

-- Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

--  * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
--  * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
--  * Neither the name of MySQL AB nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `suces` ;
CREATE SCHEMA IF NOT EXISTS `suces` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `suces` ;

-- -----------------------------------------------------
-- Table `suces`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `suces`.`users` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT COMMENT 'user id' ,
  `email` VARCHAR(64) NULL COMMENT 'used for login name' ,
  `password` VARCHAR(128) NOT NULL COMMENT 'hash value of password' ,
  `create_date` DATETIME NOT NULL ,
  `last_login_date` DATETIME NOT NULL ,
  PRIMARY KEY (`user_id`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `log_name_U` ON `suces`.`users` (`email` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `suces`.`roles` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`roles` (
  `role_id` INT NOT NULL ,
  `role_name` VARCHAR(64) NULL COMMENT 'user role name' ,
  `description` VARCHAR(256) NULL ,
  PRIMARY KEY (`role_id`) )
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE UNIQUE INDEX `role_name_U` ON `suces`.`roles` (`role_name` ASC) ;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `suces`.`users_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `suces`.`users_roles` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `suces`.`users_roles` (
  `user_id` INT NOT NULL ,
  `role_id` INT NOT NULL ,
  PRIMARY KEY (`role_id`, `user_id`) ,
  CONSTRAINT `fk_users_roles_users`
    FOREIGN KEY (`user_id` )
    REFERENCES `suces`.`users` (`user_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_roles_roles`
    FOREIGN KEY (`role_id` )
    REFERENCES `suces`.`roles` (`role_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE INDEX `fk_uroles_users` ON `suces`.`users_roles` (`user_id` ASC) ;

SHOW WARNINGS;
CREATE INDEX `fk_uroles_roles` ON `suces`.`users_roles` (`role_id` ASC) ;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
