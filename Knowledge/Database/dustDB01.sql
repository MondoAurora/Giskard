DROP TABLE IF EXISTS dust_data, dust_event, dust_text, dust_stream, dust_entity;

CREATE TABLE `dust_entity` (
  `EntityId` int NOT NULL AUTO_INCREMENT,
  `PrimaryType` int NOT NULL,
  `Unit` int NOT NULL,
  `LastChange` int NOT NULL,
  `LastValid` int,
  PRIMARY KEY (`EntityId`),
  KEY `ENTITY_PRIMARY_TYPE` (`PrimaryType`),
  KEY `FK_ENTITY_CHANGE_idx` (`LastValid`),
  KEY `ENTITY_UNIT` (`Unit`)
-- , CONSTRAINT `FK_ENTITY_UNIT` FOREIGN KEY (`Unit`) REFERENCES `dust_entity` (`EntityId`) ON DELETE RESTRICT,
--  CONSTRAINT `FK_ENTITY_PRIMARYTYPE` FOREIGN KEY (`PrimaryType`) REFERENCES `dust_entity` (`EntityId`) ON DELETE RESTRICT,
--  CONSTRAINT `FK_ENTITY_LASTChange` FOREIGN KEY (`LastChange`) REFERENCES `dust_entity` (`EntityId`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE `dust_data` (
  `DataId` int NOT NULL AUTO_INCREMENT,
  `Entity` int NOT NULL,
  `Token` int NOT NULL,
  `OptKey` int,
  `ValInteger` int,
  `ValReal` double,
  `ValLink` int,
  `LastValid` int COMMENT 'NULL for current, otherwise the last Change in which this was the value',
  PRIMARY KEY (`DataId`),
  KEY `DATA_ENTITY` (`Entity`),
  KEY `DATA_TOKEN` (`Token`),
  KEY `FK_DATA_KEY_idx` (`OptKey`),
  KEY `FK_DATA_CHANGE_idx` (`LastValid`),
  CONSTRAINT `FK_DATA_ENTITY` FOREIGN KEY (`Entity`) REFERENCES `dust_entity` (`EntityId`) ON DELETE CASCADE,
  CONSTRAINT `FK_DATA_CHANGE` FOREIGN KEY (`LastValid`) REFERENCES `dust_entity` (`EntityId`) ON DELETE RESTRICT,
  CONSTRAINT `FK_DATA_TOKEN` FOREIGN KEY (`Token`) REFERENCES `dust_entity` (`EntityId`) ON DELETE RESTRICT,
  CONSTRAINT `FK_DATA_LINK` FOREIGN KEY (`ValLink`) REFERENCES `dust_entity` (`EntityId`) ON DELETE RESTRICT
) AUTO_INCREMENT=1 ;

CREATE TABLE `dust_event` (
  `EventId` int NOT NULL AUTO_INCREMENT,
  `Entity` int NOT NULL,
  `Type` int NOT NULL,
  `Level` int NOT NULL,
  `Start` DATETIME NOT NULL,
  `End` DATETIME,
  `LastValid` int COMMENT 'NULL for current, otherwise the last Change in which this was the value',
  PRIMARY KEY (`EventId`),
  KEY `EVENT_ENTITY` (`Entity`),
  KEY `FK_EVENT_CHANGE_idx` (`LastValid`),
  KEY `EVENT_TYPE` (`Type`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE `dust_text` (
  `TextId` int NOT NULL AUTO_INCREMENT,
  `Entity` int NOT NULL,
  `Token` int NOT NULL,
  `OptKey` int,
  `Language` int NOT NULL,
  `Text` Text NOT NULL,
  `LastValid` int COMMENT 'NULL for current, otherwise the last Change in which this was the value',
  PRIMARY KEY (`TextId`),
  KEY `TEXT_ENTITY` (`Entity`),
  KEY `FK_TEXT_CHANGE_idx` (`LastValid`),
  KEY `TEXT_LANG` (`Language`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE `dust_stream` (
  `StreamId` int NOT NULL AUTO_INCREMENT,
  `Entity` int NOT NULL,
  `Type` int NOT NULL,
  `Content` BLOB NOT NULL,
  `LastValid` int COMMENT 'NULL for current, otherwise the last Change in which this was the value',
  PRIMARY KEY (`StreamId`),
  KEY `STREAM_ENTITY` (`Entity`),
  KEY `FK_STREAM_CHANGE_idx` (`LastValid`),
  KEY `STREAM_TYPE` (`Type`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

