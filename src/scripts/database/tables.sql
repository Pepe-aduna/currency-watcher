CREATE TABLE `currency_data`.`price_track` (
  `price` DECIMAL(10) NOT NULL,
  `time` BIGINT(10) NOT NULL,
  `date` DATETIME NOT NULL,
  `currency` VARCHAR(8) NOT NULL,
  `type` VARCHAR(10) NULL,
  `kind` VARCHAR(45) NULL,
  `variation` VARCHAR(10) NULL,
  `direction` VARCHAR(10) NULL,
  `price_trackcol` VARCHAR(45) NULL,
  `millis_diff` BIGINT(10) NULL,
  `t_id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`t_id`),
  UNIQUE INDEX `id_UNIQUE` (`t_id` ASC) VISIBLE);