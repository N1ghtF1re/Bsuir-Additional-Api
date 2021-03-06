CREATE TABLE `faculty` (
    `id` INT PRIMARY KEY  AUTO_INCREMENT,
    `name` VARCHAR(512) UNIQUE NOT NULL,
    `alias` VARCHAR(64) UNIQUE NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `speciality` (
    `id` INT PRIMARY KEY  AUTO_INCREMENT,
    `faculty_id` INT NOT NULL,
    `name` VARCHAR(512) NOT NULL,
    `iis_id` INT NOT NULL,
    `alias` VARCHAR(64) NOT NULL,
    `education_form` VARCHAR(32) NOT NULL,

    FOREIGN KEY (faculty_id) REFERENCES `faculty` (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

