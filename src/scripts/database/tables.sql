CREATE TABLE `currency_data`.`price_track` (
  `price` DECIMAL(18,8) NOT NULL,
  `time` BIGINT(10) NOT NULL,
  `date` DATETIME NOT NULL,
  `symbol` VARCHAR(20) NOT NULL,
  `type` VARCHAR(10) NULL,
  `kind` VARCHAR(45) NULL,
  `variation` VARCHAR(10) NULL,
  `direction` VARCHAR(10) NULL,
  `price_track` VARCHAR(45) NULL,
  `millis_diff` BIGINT(10) NULL,
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`t_id`),
  UNIQUE INDEX `id_UNIQUE` (`t_id` ASC) VISIBLE,

  INDEX idx_symbol (symbol),
  INDEX idx_recorded_at (recorded_at),
  INDEX idx_symbol_time (symbol, recorded_at)
 );

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

CREATE TABLE `currency_data`.`alerts` (
`id` INT NOT NULL,
`name` VARCHAR(45) NOT NULL,
`symbol` VARCHAR(45) NOT NULL,
`type` VARCHAR(45) NOT NULL,
`message` VARCHAR(150) NOT NULL,
`origin` VARCHAR(45) NOT NULL,
`notification_channel` VARCHAR(45) NOT NULL,
`percent` DOUBLE NULL,
`open_price` DECIMAL(18,8) NULL,
`amount_mov` DECIMAL(18,8) NOT NULL,
`prices` VARCHAR(100) NULL,
`reminders` INT NOT NULL,
PRIMARY KEY (`id`),
UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);
