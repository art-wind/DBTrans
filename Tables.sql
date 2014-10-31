CREATE SCHEMA `DBTransfer` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `DBTransfer`.`room` (
  `kdno` VARCHAR(45) NOT NULL,
  `kcno` INT NOT NULL,
  `ccno` INT NOT NULL,
  `kdname` VARCHAR(45) NULL,
  `exptime` VARCHAR(45) NULL,
  `papername` VARCHAR(45) NULL);
CREATE TABLE `DBTransfer`.`student` (
  `registno` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `kdno` VARCHAR(45) NULL,
  `kcno` INT NULL,
  `ccno` INT NULL,
  `seat` INT NULL);
DROP TABLE `DBTransfer`.`student`;
DROP TABLE `DBTransfer`.`room`;