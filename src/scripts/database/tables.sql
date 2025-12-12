CREATE TABLE `currency_data`.`price_track` (
  `price` DOUBLE NOT NULL,
  `time` BIGINT(10) NOT NULL,
  `date` DATETIME NOT NULL,
  `symbol` VARCHAR(20) NOT NULL,
  `type` VARCHAR(10) NULL,
  `kind` VARCHAR(45) NULL,
  `variation` VARCHAR(10) NULL,
  `direction` VARCHAR(10) NULL,
  `price_trackcol` VARCHAR(45) NULL,
  `millis_diff` BIGINT(10) NULL,
  `t_id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`t_id`),
  UNIQUE INDEX `id_UNIQUE` (`t_id` ASC) VISIBLE);

CREATE TABLE `currency_data`.`configurations` (
`name` VARCHAR(20) NOT NULL,
`value` VARCHAR(500) NOT NULL,
`status` VARCHAR(45) NOT NULL,
`date` DATETIME NOT NULL,
`udate` DATETIME NOT NULL,
PRIMARY KEY (`name`),
UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

CREATE TABLE `currency_data`.`watch_list` (
  `idwatch_list` INT NOT NULL AUTO_INCREMENT,
  `currency` VARCHAR(20) NOT NULL,
  `status` VARCHAR(20) NOT NULL,
  `price_target` DECIMAL(10) NULL,
  `price_indicator` DECIMAL(10) NULL,
  `notification` VARCHAR(45) NULL,
  `date` DATETIME NOT NULL,
  `udate` DATETIME NOT NULL,
  PRIMARY KEY (`idwatch_list`),
  UNIQUE INDEX `idwatch_list_UNIQUE` (`idwatch_list` ASC) VISIBLE);