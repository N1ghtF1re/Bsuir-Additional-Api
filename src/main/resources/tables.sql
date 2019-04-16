CREATE TABLE IF NOT EXISTS `auditoriums` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `type` VARCHAR(250)  NOT NULL,
  `floor`  INT NOT NULL,
  `building` INT NOT NULL,

   PRIMARY KEY  (`id`)
);

CREATE TABLE IF NOT EXISTS `lessons` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `auditorium` INT NOT NULL,
  `weeks` INT NOT NULL,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  `group` VARCHAR(255) NOT NULL,

   PRIMARY KEY  (`id`)
);