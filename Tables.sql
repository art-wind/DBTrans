CREATE SCHEMA `room` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `room`.`room` (
  `knno` INT NOT NULL,
  `kcno` INT NOT NULL,
  `ccno` INT NOT NULL,
  `kdname` INT NULL,
  `exptime` DATETIME NULL,
  `papername` VARCHAR(45) NULL,
  PRIMARY KEY (`knno`, `kcno`, `ccno`));

