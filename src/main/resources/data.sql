DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
	`id` LONG AUTO_INCREMENT  PRIMARY KEY,
	`email_address` VARCHAR(250) NOT NULL,
    `encrypted_password` VARCHAR(250) NOT NULL,
    `is_verified` boolean DEFAULT false,
	`is_locked` boolean DEFAULT false,
	`credibility` int DEFAULT 0,
	`privacy_setting` int DEFAULT 0,
	`role` VARCHAR(250) NOT NULL
	/*`updatedAt` timestamp NOT NULL DEFAULT NOW(),
	`createdAt` timestamp NOT NULL DEFAULT NOW() ON UPDATE NOW()*/
);

INSERT INTO `User` (`email_address`, `encrypted_password`, `role`) VALUES
('admin@chef.lol', 'sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=', 'administrator');

INSERT INTO `User` (`email_address`, `encrypted_password`, `role`) VALUES
('emre', 'sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=', 'user');