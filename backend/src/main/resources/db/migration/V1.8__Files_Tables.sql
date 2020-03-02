CREATE TABLE `file` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `student_id` INT NOT NULL,
   `file_name` VARCHAR(255) NOT NULL,
   `file_external_id` VARCHAR(255) UNIQUE NOT NULL,
   `access_type` VARCHAR(64) NOT NULL,
   `group_owner` VARCHAR(32) NOT NULL,
   `file_type` VARCHAR(32) NOT NULL,
   `mime_type` VARCHAR(128) NOT NULL,
   `link` VARCHAR(512) DEFAULT NULL,
   `parent_id` INT DEFAULT NULL,

   PRIMARY KEY (`id`),
   FOREIGN KEY (student_id) REFERENCES `student` (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
   FOREIGN KEY (parent_id) REFERENCES `file` (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) DEFAULT CHARSET=utf8;