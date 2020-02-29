CREATE TABLE  `auditorium` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(250) NOT NULL,
  `type` VARCHAR(250)  NOT NULL,
  `floor`  INT NOT NULL,
  `building` INT NOT NULL,

   PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lesson` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `auditorium_id` INT NOT NULL,
  `day` INT NOT NULL,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  `group` VARCHAR(255) NOT NULL,

   PRIMARY KEY  (`id`),
   FOREIGN KEY (auditorium_id) REFERENCES `auditorium` (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `lesson_weeks` (
  `lesson_id` INT NOT NULL,
  `week` INT NOT NULL,

  PRIMARY KEY (`lesson_id`, `week`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;