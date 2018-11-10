--
-- Table structure for table `todolist_db`
--

DROP TABLE IF EXISTS `topics`;
DROP TABLE IF EXISTS `todoslist`;
DROP TABLE IF EXISTS `atachments`;

CREATE TABLE `topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL UNIQUE,
  `created_at` datetime DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `todoitems` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) not null,
  `element` varchar(45) NOT null,
  `notes` varchar(1500) NOT null,
  `created_at` datetime DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `todoslist_fk` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `atachments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `todoitem_id` int(11) not null,
  `path` varchar(45) NOT null,
  `created_at` datetime DEFAULT NULL,
  `created_by_user` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by_user` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `atachmentst_fk` FOREIGN KEY (`todolist_id`) REFERENCES `todoslist` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;