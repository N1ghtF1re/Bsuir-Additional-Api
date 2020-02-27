CREATE TABLE `record_book` (
  `id` INT PRIMARY KEY  AUTO_INCREMENT,
  `number` VARCHAR(16) UNIQUE NOT NULL,
  `average_mark` DOUBLE NOT NULL DEFAULT 0
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `record_book_semester` (
  `id` INT PRIMARY KEY  AUTO_INCREMENT,
  `record_book_id` INT NOT NULL,
  `number` INT NOT NULL,
  `average_mark` DOUBLE NOT NULL DEFAULT 0,

   FOREIGN KEY (record_book_id) REFERENCES record_book (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `record_book_mark` (
  `id` INT PRIMARY KEY  AUTO_INCREMENT,
  `semester_id` INT NOT NULL,
  `subject` VARCHAR(255) NOT NULL,
  `form_of_control` VARCHAR(255) NOT NULL,
  `hours` INT DEFAULT NULL,
  `mark` VARCHAR(64) DEFAULT NULL,
  `date` DATE DEFAULT NULL,
  `teacher` VARCHAR(255) DEFAULT NULL,
  `retakes_count` INT DEFAULT 0,

   FOREIGN KEY (semester_id) REFERENCES record_book_semester (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `record_book_subject_statistic` (
  `id` INT PRIMARY KEY  AUTO_INCREMENT,
  `mark_id` INT NOT NULL,
  `average_mark` DOUBLE DEFAULT NULL,
  `retake_probability` DOUBLE DEFAULT NULL,


   FOREIGN KEY (mark_id) REFERENCES record_book_mark (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `record_book_diploma` (
  `id` INT PRIMARY KEY  AUTO_INCREMENT,
  `record_book_id` INT NOT NULL,
  `topic` VARCHAR(255) DEFAULT NULL,
  `teacher` VARCHAR(255) DEFAULT NULL,

  FOREIGN KEY (record_book_id) REFERENCES record_book (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;