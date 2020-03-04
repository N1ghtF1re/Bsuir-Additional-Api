CREATE TABLE `notification_token` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `student_id` INT NOT NULL,
   `type` VARCHAR(64) NOT NULL,
   `token` VARCHAR(255) NOT NULL,

   PRIMARY KEY (`id`),
   FOREIGN KEY (student_id) REFERENCES `student` (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;