DROP TABLE IF EXISTS `Verification`;
DROP TABLE IF EXISTS `Session`;
DROP TABLE IF EXISTS `Reset`;

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
	`id` LONG AUTO_INCREMENT  PRIMARY KEY,
	`email_address` VARCHAR(250) NOT NULL,
    `encrypted_password` VARCHAR(250) NOT NULL,
    `is_verified` boolean DEFAULT false,
	`is_locked` boolean DEFAULT false,
	`credibility` int DEFAULT 0,
	`privacy_setting` int DEFAULT 0,
	`role` VARCHAR(250) NOT NULL,
	`updated_at` timestamp NOT NULL DEFAULT NOW(),
	`created_at` timestamp NOT NULL DEFAULT NOW() ON UPDATE NOW()
);

CREATE TABLE `Session`  (
  `id` LONG AUTO_INCREMENT  PRIMARY KEY,
  `user_Id` int NOT NULL,
  `token` VARCHAR(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE `Reset`  (
  `id` LONG AUTO_INCREMENT  PRIMARY KEY,
  `user_Id` int NOT NULL,
  `token` VARCHAR(255) NOT NULL ,
  `created_at` timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE `Verification`  (
  `id` LONG AUTO_INCREMENT  PRIMARY KEY,
  `user_Id` int NOT NULL,
  `token` VARCHAR(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT NOW()
);

ALTER TABLE `Reset` ADD CONSTRAINT `fk_reset_userId` FOREIGN KEY (`user_Id`) REFERENCES `User` (`id`);
ALTER TABLE `Session` ADD CONSTRAINT `fk_session_userId` FOREIGN KEY (`user_Id`) REFERENCES `User` (`id`);
ALTER TABLE `Verification` ADD CONSTRAINT `fk_verification_userId` FOREIGN KEY (`user_Id`) REFERENCES `User` (`id`);

INSERT INTO `User` (`email_address`, `encrypted_password`, `role`) VALUES
('admin@chef.lol', 'sha: uwXzywW1mNUAWQZIaRA3cvRyyQQ=', 'administrator');

/*test_pw*/
INSERT INTO `User` (`email_address`, `encrypted_password`, `role`) VALUES
('test@mail.com', 'sha: 2YbXcpo8qT6PuNnA8ZYlZK7Z+Ck=', 'user');

INSERT INTO `Reset` (`user_id`, `token`) VALUES
(2, 'sha: 2YbXcpo8qT6PuNnA8ZYlZK7Z+Ck=');