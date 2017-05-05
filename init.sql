-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema assignment3
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `assignment3` ;

-- -----------------------------------------------------
-- Schema assignment3
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `assignment3` DEFAULT CHARACTER SET utf8 ;
USE `assignment3` ;

-- -----------------------------------------------------
-- Table `assignment3`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `fullName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `assignment3`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`Role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `assignment3`.`Patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`Patient` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fullName` VARCHAR(45) NOT NULL,
  `idCardSeries` VARCHAR(45) NOT NULL,
  `idCardNumber` VARCHAR(45) NOT NULL,
  `personalNumericalCode` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `personalNumericalCode_UNIQUE` (`personalNumericalCode` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `assignment3`.`User_has_Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`User_has_Role` (
  `User_id` INT NOT NULL,
  `Role_id` INT NOT NULL,
  PRIMARY KEY (`User_id`, `Role_id`),
  INDEX `fk_User_has_Role_Role1_idx` (`Role_id` ASC),
  INDEX `fk_User_has_Role_User_idx` (`User_id` ASC),
  CONSTRAINT `fk_User_has_Role_User`
    FOREIGN KEY (`User_id`)
    REFERENCES `assignment3`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Role_Role1`
    FOREIGN KEY (`Role_id`)
    REFERENCES `assignment3`.`Role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `assignment3`.`Doctor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`Doctor` (
  `id` INT NOT NULL,
  INDEX `fk_Doctor_User1_idx` (`id` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Doctor_User1`
    FOREIGN KEY (`id`)
    REFERENCES `assignment3`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `assignment3`.`WorkingHour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`WorkingHour` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `startHour` INT NOT NULL,
  `endHour` INT NOT NULL,
  `dayOfWeek` INT NOT NULL,
  `doctorId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_WorkingHour_Doctor1_idx` (`doctorId` ASC),
  CONSTRAINT `fk_WorkingHour_Doctor1`
    FOREIGN KEY (`doctorId`)
    REFERENCES `assignment3`.`Doctor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `assignment3`.`Consultation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`Consultation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `patientId` INT NOT NULL,
  `startsAt` DATETIME NOT NULL,
  `endsAt` DATETIME NOT NULL,
  `doctorId` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Consultation_Patient1_idx` (`patientId` ASC),
  INDEX `fk_Consultation_Doctor1_idx` (`doctorId` ASC),
  CONSTRAINT `fk_Consultation_Patient1`
    FOREIGN KEY (`patientId`)
    REFERENCES `assignment3`.`Patient` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Consultation_Doctor1`
    FOREIGN KEY (`doctorId`)
    REFERENCES `assignment3`.`Doctor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `assignment3`.`Notification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment3`.`Notification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `seen` TINYINT(1) NULL,
  `reference` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Notification_Consultation1_idx` (`reference` ASC),
  CONSTRAINT `fk_Notification_Consultation1`
    FOREIGN KEY (`reference`)
    REFERENCES `assignment3`.`Consultation` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


  
DELIMITER //
DROP TRIGGER IF EXISTS insert_doctor_trigger//
CREATE TRIGGER insert_doctor_trigger
AFTER INSERT ON `user_has_role`
FOR EACH ROW

BEGIN
    DECLARE doctor_role INT; 
    SET doctor_role = (SELECT id FROM assignment3.role WHERE name="doctor");
    
    if NEW.Role_id = doctor_role THEN
      INSERT INTO doctor(`id`) VALUES (NEW.User_id);
    END IF;
END//
DELIMITER ;

INSERT INTO `assignment3`.`user` (`username`, `password`, `fullName`) VALUES ('admin', 'admin', 'Administrator');
INSERT INTO `assignment3`.`user` (`username`, `password`, `fullName`) VALUES ('doctor', 'doctor', 'Doctor');
INSERT INTO `assignment3`.`user` (`username`, `password`, `fullName`) VALUES ('secretary', 'secretary', 'Secretary');

INSERT INTO `assignment3`.`role` (`name`) VALUES ('admin');
INSERT INTO `assignment3`.`role` (`name`) VALUES ('doctor');
INSERT INTO `assignment3`.`role` (`name`) VALUES ('secretary');

INSERT INTO `assignment3`.`user_has_role` (`User_id`, `Role_id`) VALUES ('1', '1');
INSERT INTO `assignment3`.`user_has_role` (`User_id`, `Role_id`) VALUES ('2', '2');
INSERT INTO `assignment3`.`user_has_role` (`User_id`, `Role_id`) VALUES ('3', '3');

INSERT INTO `assignment3`.`workinghour`(`startHour`,`endHour`,`dayOfWeek`,`doctorId`) VALUES
('8', '17', '4', '2'),
('8', '17', '3', '2'),
('8', '17', '1', '2'),
('8', '17', '2', '2'),
('8', '12', '5', '2'),
('14', '17', '5', '2');

INSERT INTO `assignment3`.`patient` (`fullName`,`idCardSeries`,`idCardNumber`,`personalNumericalCode`,`address`) VALUES
('Zeic Naomi Ioana','KZ','113720','1970208307432','Str. Observatorului Nr. 93'),
('Tarce Diana Mariana','DP','365983','1971105415851','Str. Dambovitei Nr. 46'),
('Aionitoaie Alexandru - Mihai','GG','712124','1970508054166','Str. Bucuresti Nr. 93'),
('Muntian Razvan Catalin','CJ','141630','1970402203036','Str. Dorobantilor Nr. 31'),
('Daian Dragos Teodor','SX','298765','2940814275551','Str. Cernei Nr. 15'),
('Savan Catalin','SM','360382','1970312359144','Str. Farbicii de Zahar Nr. 138'),
('Moldovan Cristian','VS','190900','2970420432067','Str. Dambovitei Nr. 112'),
('Matei Vladut Ionut','KT','572777','2941220074247','Str. Bucuresti Nr. 128'),
('Chendris Calin - Andrei','KT','969001','2960821225241','Str. Dorobantilor Nr. 71'),
('Pop Patric Virgil','DX','619840','1960421402404','Str. Dorobantilor Nr. 18'),
('Stupinean Andrada Raluca','RT','138627','2940309164312','Str. Cernei Nr. 141'),
('Stan David Mihai','HD','475562','2951215109924','Str. Dambovitei Nr. 125'),
('Neamtu Bogdan - Costel','ZV','344006','2970907466101','Str. Bucuresti Nr. 83'),
('Lepinzan Bogdan Daniel','SV','539113','1960408351875','Str. Farbicii de Zahar Nr. 39'),
('Rad Simion Marius','RR','759394','2960626422196','Str. Dambovitei Nr. 39');