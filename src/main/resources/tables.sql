CREATE TABLE IF NOT EXISTS `auditoriums` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `type` VARCHAR(250)  NOT NULL,
  `floor`  INT NOT NULL,
  `building` INT NOT NULL,

   PRIMARY KEY  (`id`)
);