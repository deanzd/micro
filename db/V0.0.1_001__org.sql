CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50),
  `password` varchar(255),
  `role_id` int(11) NOT NULL,
  `sys_init` tinyint(1) NOT NULL default 0,
  `create_time` datetime NOT NULL,
  `create_user` varchar(64) NOT NULL,
  `update_time` datetime,
  `update_user` varchar(64),
  `deleted` tinyint(1) NOT NULL default 0,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50),
  `code` varchar(50),
  `description` varchar(255),
  `sys_init` tinyint(1) NOT NULL default 0,
  `create_time` datetime NOT NULL,
  `create_user` varchar(64) NOT NULL,
  `update_time` datetime,
  `update_user` varchar(64),
  `deleted` tinyint(1) NOT NULL default 0,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL ,
  `permission_id` int(11) NOT NULL,
  `sys_init` tinyint(1) NOT NULL default 0,
  `create_time` datetime NOT NULL,
  `create_user` varchar(64) NOT NULL,
  `update_time` datetime,
  `update_user` varchar(64),
  `deleted` tinyint(1) NOT NULL default 0,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50),
  `name` varchar(50),
  `description` varchar(255),
  `path` varchar(255),
  `method` varchar(10),
  `sys_init` tinyint(1) NOT NULL default 0,
  `create_time` datetime NOT NULL,
  `create_user` varchar(64) NOT NULL,
  `update_time` datetime,
  `update_user` varchar(64),
  `deleted` tinyint(1) NOT NULL default 0,
  PRIMARY KEY (`id`)
);

