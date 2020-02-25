
CREATE TABLE student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    iis_id INT NOT NULL UNIQUE,

    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255) DEFAULT NULL,

    record_book_number VARCHAR(255) UNIQUE DEFAULT NULL,

    birth_day DATETIME NOT NULL,

    photo VARCHAR(512) DEFAULT NULL,
    summary TEXT DEFAULT NULL,
    rating INT NOT NULL,

    updated_at DATETIME DEFAULT NOW()
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;;

CREATE TABLE student_education_information (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `student_id` INT NOT NULL,

    `faculty` VARCHAR(64) NOT NULL,
    `speciality` VARCHAR(64) NOT NULL,
    `group` VARCHAR(16) NOT NULL,

    `course` INT NOT NULL
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE student_settings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,

    is_show_rating TINYINT NOT NULL,
    is_public_profile TINYINT NOT NULL,
    is_search_job TINYINT NOT NULL

)  ENGINE=InnoDB DEFAULT CHARSET=utf8;