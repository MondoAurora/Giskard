CREATE SCHEMA `dust` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;


CREATE TABLE `dust_entity` (
  `StoreId` int NOT NULL AUTO_INCREMENT COMMENT 'External ID, known by other components. Represents the current state of the entity',
  `UnitId` int NOT NULL,
  `PrimaryTypeId` int NOT NULL,
  `InitialId` int NOT NULL,
  `CommitId` int DEFAULT NULL COMMENT 'If the entity is versioned',
  `State` int DEFAULT NULL COMMENT 'Null means "OK"',
  `Idx01` int DEFAULT NULL,
  `Idx02` int DEFAULT NULL,
  `Idx03` int DEFAULT NULL,
  PRIMARY KEY (`StoreId`),
  UNIQUE KEY `StoreID_UNIQUE` (`UnitId`, `StoreId`),
  KEY `PRIMARY_TYPE` (`PrimaryTypeId`),
  KEY `INITIAL_ID` (`InitialId`,`CommitId`)
) ENGINE=InnoDB AUTO_INCREMENT=2;


CREATE TABLE `dust_tag` (
  `StoreId` int NOT NULL AUTO_INCREMENT,
  `UnitId` int NOT NULL,
  `EntityStoreId` int NOT NULL,
  `EntityUnitId` int NOT NULL,
  `TagStoreId` int NOT NULL,
  `TagUnitId` int NOT NULL,
  PRIMARY KEY (`StoreId`),
  UNIQUE KEY `Tag_UNIQUE` (`UnitId`, `StoreId`),
  KEY `Tag_ENTITY` (`EntityStoreId`, `EntityUnitId`),
  KEY `Tag_TAG` (`TagStoreId`, `TagUnitId`)
) ENGINE=InnoDB AUTO_INCREMENT=2;

CREATE TABLE `dust_data` (
  `StoreId` int NOT NULL AUTO_INCREMENT,
  `UnitId` int NOT NULL,
  `EntityStoreId` int NOT NULL,
  `EntityUnitId` int NOT NULL,
  `ValInteger` int DEFAULT NULL,
  `ValReal` double DEFAULT NULL,
  `ValIdentifier` VARCHAR(100) DEFAULT NULL,
  `ValDateTime` DATETIME DEFAULT NULL,
  `TokenStoreId` int NOT NULL,
  `TokenUnitId` int NOT NULL,
  `KeyStoreId` int,
  `KeyUnitId` int,
  `ArrIdx` int,
  PRIMARY KEY (`StoreId`),
  UNIQUE KEY `Link_UNIQUE` (`UnitId`, `StoreId`),
  KEY `Tag_ENTITY` (`EntityStoreId`, `EntityUnitId`),
  KEY `Link_TOKEN` (`TokenStoreId`, `TokenUnitId`)
) ENGINE=InnoDB AUTO_INCREMENT=2;

CREATE TABLE `dust_link` (
  `StoreId` int NOT NULL AUTO_INCREMENT,
  `UnitId` int NOT NULL,
  `FromStoreId` int NOT NULL,
  `FromUnitId` int NOT NULL,
  `ToStoreId` int NOT NULL,
  `ToUnitId` int NOT NULL,
  `TokenStoreId` int NOT NULL,
  `TokenUnitId` int NOT NULL,
  `KeyStoreId` int,
  `KeyUnitId` int,
  `ArrIdx` int,
  PRIMARY KEY (`StoreId`),
  UNIQUE KEY `Link_UNIQUE` (`UnitId`, `StoreId`),
  KEY `Link_FROM` (`FromStoreId`, `FromUnitId`),
  KEY `Link_TO` (`ToStoreId`, `ToUnitId`),
  KEY `Link_TOKEN` (`TokenStoreId`, `TokenUnitId`)
) ENGINE=InnoDB AUTO_INCREMENT=2;


