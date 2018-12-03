DROP TABLE IF EXISTS `attachments`;
DROP TABLE IF EXISTS `todoitems`;
DROP TABLE IF EXISTS `topics`;

CREATE TABLE `topics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL UNIQUE,
  `description` varchar(255),
  `created_at` datetime(6) DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `topics_uk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `todoitems` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `topic_id` bigint(20) NOT NULL,
  `element` varchar(255) NOT null,
  `notes` varchar(1500) NOT null,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `todoitems_key` (`topic_id`),
  CONSTRAINT `todoslist_fk` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `attachments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `filename` varchar(45) NOT NULL,
  `path` varchar(255) NOT NULL,
  `todoitem_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `deleted_by_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `attachments_key` (`todoitem_id`),
  CONSTRAINT `atachmentst_fk` FOREIGN KEY (`todoitem_id`) REFERENCES `todoitems` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;