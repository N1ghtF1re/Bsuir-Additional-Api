CREATE TABLE `student_group` (
    `id` INT PRIMARY KEY  AUTO_INCREMENT,
    `number` VARCHAR(16) UNIQUE NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `group_member` (
    `id` INT PRIMARY KEY  AUTO_INCREMENT,
    `group_id` INT NOT NULL,
    `role` VARCHAR(64) DEFAULT NULL,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) DEFAULT NULL,
    `phone` VARCHAR(16) DEFAULT NULL,

    FOREIGN KEY (group_id) REFERENCES `student_group` (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
