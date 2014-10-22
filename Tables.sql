CREATE SCHEMA `room` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `room`.`room` (
  `kdno` INT NOT NULL,
  `kcno` INT NOT NULL,
  `ccno` INT NOT NULL,
  `kdname` INT NULL,
  `exptime` DATETIME NULL,
  `papername` VARCHAR(45) NULL,
  PRIMARY KEY (`knno`, `kcno`, `ccno`));
CREATE TABLE `room`.`Student` (
  `registno` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `kdno` INT NULL,
  `kcno` INT NULL,
  `ccno` INT NULL,
  `seat` INT NULL,
  PRIMARY KEY (`registno`),
  INDEX `kcno_idx` (`kcno` ASC),
  INDEX `kdno_idx` (`kdno` ASC),
  INDEX `ccno_idx` (`ccno` ASC),
  CONSTRAINT `kcno`
    FOREIGN KEY (`kcno`)
    REFERENCES `room`.`room` (`kcno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `kdno`
    FOREIGN KEY (`kdno`)
    REFERENCES `room`.`room` (`kdno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ccno`
    FOREIGN KEY (`ccno`)
    REFERENCES `room`.`room` (`ccno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);