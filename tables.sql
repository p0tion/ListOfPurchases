CREATE SCHEMA IF NOT EXISTS `listofpurchases`;
USE `listofpurchases` ;

-- -----------------------------------------------------
-- Table `listofpurchases`.`customer_table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `listofpurchases`.`customer_table` (
  `customer_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `card_number` VARCHAR(45) NOT NULL,
  `quantity` INT(10) UNSIGNED NOT NULL DEFAULT '0',
  `invoice` FLOAT UNSIGNED NOT NULL DEFAULT '0',
  `login` VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE INDEX `full_name` (`first_name`, `last_name`));

-- -----------------------------------------------------
-- Table `listofpurchases`.`product_table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `listofpurchases`.`product_table` (
  `product_id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL UNIQUE,
  `description` VARCHAR(45) NOT NULL,
  `price` FLOAT UNSIGNED NOT NULL);

-- -----------------------------------------------------
-- Table `listofpurchases`.`customer_product_table`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `listofpurchases`.`customer_product_table` (
  `customer_id` INT(10) UNSIGNED NOT NULL,
  `product_id` INT(10) UNSIGNED NOT NULL,
  INDEX `customer_id` (`customer_id`),
  INDEX `product_id` (`product_id`),
  CONSTRAINT `customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `listofpurchases`.`customer_table` (`customer_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `listofpurchases`.`product_table` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
