DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
	`id` LONG AUTO_INCREMENT  PRIMARY KEY,
	`email_address` VARCHAR(250) NOT NULL,
    `encrypted_password` VARCHAR(250) NOT NULL,
	`role` VARCHAR(250) NOT NULL,
	`is_locked` boolean DEFAULT false
);

INSERT INTO `User` (`email_address`, `encrypted_password`, `role`) VALUES
('admin@chef.lol', 'sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=', 'administrator');