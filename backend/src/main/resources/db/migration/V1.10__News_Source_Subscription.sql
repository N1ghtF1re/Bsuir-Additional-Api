CREATE TABLE `news_source_subscription` (
   `id` INT NOT NULL AUTO_INCREMENT,
   `subscriber_id` INT NOT NULL,
   `news_source_id` INT NOT NULL,

   PRIMARY KEY (`id`),
   FOREIGN KEY (subscriber_id) REFERENCES `student` (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
   FOREIGN KEY (news_source_id) REFERENCES `news_source` (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;