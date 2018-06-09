-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               5.6.27-log - MySQL Community Server (GPL)
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
 
-- Exportiere Datenbank Struktur für IIB2_UE2_Gruppe09
DROP DATABASE IF EXISTS `IIB2_UE2_Gruppe09`;
CREATE DATABASE IF NOT EXISTS `IIB2_UE2_Gruppe09` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `IIB2_UE2_Gruppe09`;

-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.dienstleister
DROP TABLE IF EXISTS `dienstleister`;
CREATE TABLE IF NOT EXISTS `dienstleister` (
    `dlr_id` INT(11) NOT NULL AUTO_INCREMENT,
    `dlr_firmaname` VARCHAR(50) DEFAULT NULL,
    `dlr_username` VARCHAR(50) DEFAULT NULL,
    `dlr_passwort` BLOB,
    PRIMARY KEY (`dlr_id`)
)  ENGINE=INNODB AUTO_INCREMENT=11 DEFAULT CHARSET=UTF8;

-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.dienstleister
DROP TABLE IF EXISTS `dienstleistungen`;
CREATE TABLE IF NOT EXISTS `dienstleistungen` (
    `dln_id` INT(11) NOT NULL AUTO_INCREMENT,
    `dln_name` VARCHAR(50) DEFAULT NULL,
    `dln_beschreibung` TEXT(255) DEFAULT NULL,
    `dln_haefigkeit` VARCHAR(50) DEFAULT NULL,
    `dln_dma_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`dln_id`),
    KEY `dln_d_id` (`dln_dma_id`),
    CONSTRAINT `dln_d_id` FOREIGN KEY (`dln_dma_id`)
        REFERENCES `dezernatmitarbeiter` (`dlr_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=13 DEFAULT CHARSET=UTF8;

-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.leistungspektren1
DROP TABLE IF EXISTS `leistungsspektren`;
CREATE TABLE IF NOT EXISTS `leistungsspektren` (
    `lsp_id` INT(11) NOT NULL AUTO_INCREMENT,
    `lsp_dlr_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`lsp_id`),
    KEY `lsp_d_id` (`lsp_dlr_id`),
    CONSTRAINT `lsp_d_id` FOREIGN KEY (`lsp_dlr_id`)
        REFERENCES `dienstleister` (`dlr_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=UTF8;

 
-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.lnLspDln
DROP TABLE IF EXISTS `lnLspDln`;
CREATE TABLE IF NOT EXISTS `lnLspDln` (
    `lld_id` INT(11) NOT NULL AUTO_INCREMENT,
    `lld_dln_id` INT(11) DEFAULT NULL,
    `lld_lsp_id` INT(11) DEFAULT NULL,
    `lld_preis` INT(11) DEFAULT NULL,
    PRIMARY KEY (`lld_id`),
    KEY `lld_d_id` (`lld_dln_id`),
    KEY `lld_l_id` (`lld_lsp_id`),
    CONSTRAINT `lld_d_id` FOREIGN KEY (`lld_dln_id`)
        REFERENCES `dienstleistungen` (`dln_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `lld_l_id` FOREIGN KEY (`lld_lsp_id`)
        REFERENCES `leistungsspektren` (`lsp_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=30 DEFAULT CHARSET=UTF8;

-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.dezernatmitarbeiter
DROP TABLE IF EXISTS `dezernatmitarbeiter`;
CREATE TABLE IF NOT EXISTS `dezernatmitarbeiter` (
    `dma_id` INT(11) NOT NULL AUTO_INCREMENT,
    `dma_name` VARCHAR(50) DEFAULT NULL,
    `dma_vorname` VARCHAR(50) DEFAULT NULL,
    `dma_username` VARCHAR(50) DEFAULT NULL,
    `dma_passwort` BLOB,
    PRIMARY KEY (`dma_id`)
)  ENGINE=INNODB AUTO_INCREMENT=11 DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS `auftraege`;
CREATE TABLE IF NOT EXISTS `auftraege` (
    `aft_id` INT(11) NOT NULL AUTO_INCREMENT,
    `aft_dma_id` INT(11) DEFAULT NULL,
    `aft_dlr_id` INT(11) DEFAULT NULL,
    `aft_dmadlr_id` INT(11) DEFAULT NULL,
    `aft_dlrdma_id` INT(11) DEFAULT NULL,
    `aft_datum` DATE DEFAULT NULL,
    `aft_status` VARCHAR(50) DEFAULT NULL,
    `aft_geb_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`aft_id`),
    KEY `aft_d_id` (`aft_dma_id`),
    CONSTRAINT `aft_d_id` FOREIGN KEY (`aft_dma_id`)
        REFERENCES `dezernatmitarbeiter` (`dma_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    KEY `a_dr_id` (`aft_dlr_id`),
    CONSTRAINT `a_dr_id` FOREIGN KEY (`aft_dlr_id`)
        REFERENCES `dienstleister` (`dlr_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    KEY `aft_dmdr_id` (`aft_dmadlr_id`),
    CONSTRAINT `aft_dmdr_id` FOREIGN KEY (`aft_dmadlr_id`)
        REFERENCES `dezernatmitarbeiter` (`dma_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    KEY `a_drdm_id` (`aft_dlrdma_id`),
    CONSTRAINT `a_drdm_id` FOREIGN KEY (`aft_dlrdma_id`)
        REFERENCES `dienstleister` (`dlr_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    KEY `a_g_id` (`aft_geb_id`),
    CONSTRAINT `a_g_id` FOREIGN KEY (`aft_geb_id`)
        REFERENCES `gebaeude` (`geb_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=10 DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS `gebaeude`;
CREATE TABLE IF NOT EXISTS `gebaeude` (
    `geb_id` INT(11) NOT NULL AUTO_INCREMENT,
    `geb_strasse` VARCHAR(50) DEFAULT NULL,
    `geb_hausnr` VARCHAR(4) DEFAULT NULL,
    `geb_ort` VARCHAR(50) DEFAULT NULL,
    `geb_plz` INT(6) UNSIGNED DEFAULT NULL,
    `geb_dma_id` INT(11) DEFAULT NULL,
    `geb_guid` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`geb_id`),
    KEY `geb_d_id` (`geb_dma_id`),
    CONSTRAINT `geb_d_id` FOREIGN KEY (`geb_dma_id`)
        REFERENCES `dezernatmitarbeiter` (`dma_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=11 DEFAULT CHARSET=UTF8;
  
DROP TABLE IF EXISTS `stockwerk`;
CREATE TABLE IF NOT EXISTS `stockwerk` (
    `stw_id` INT(11) NOT NULL AUTO_INCREMENT,
    `stw_bezeichnung` VARCHAR(50) DEFAULT NULL,
    `stw_geb_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`stw_id`),
    KEY `s_geb_id` (`stw_geb_id`),
    CONSTRAINT `s_geb_id` FOREIGN KEY (`stw_geb_id`)
        REFERENCES `gebaeude` (`geb_id`)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=UTF8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.lnraumwand
DROP TABLE IF EXISTS `lnraumwand`;
CREATE TABLE IF NOT EXISTS `lnraumwand` (
    `lrw_id` INT(11) NOT NULL AUTO_INCREMENT,
    `lrw_rau_id` INT(11) DEFAULT NULL,
    `lrw_wan_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`lrw_id`),
    KEY `r_id` (`lrw_rau_id`),
    KEY `w_id` (`lrw_wan_id`),
    CONSTRAINT `r_id` FOREIGN KEY (`lrw_rau_id`)
        REFERENCES `raum` (`rau_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `w_id` FOREIGN KEY (`lrw_wan_id`)
        REFERENCES `wand` (`wan_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=22 DEFAULT CHARSET=UTF8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.raum
DROP TABLE IF EXISTS `raum`;
CREATE TABLE IF NOT EXISTS `raum` (
    `rau_id` INT(11) NOT NULL AUTO_INCREMENT,
    `rau_nummer` VARCHAR(5) DEFAULT NULL,
    `rau_bezeichnung` VARCHAR(50) DEFAULT NULL,
    `rau_guid` VARCHAR(50) DEFAULT NULL,
    `rau_stw_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`rau_id`),
    KEY `r_s_id` (`rau_stw_id`),
    CONSTRAINT `r_s_id` FOREIGN KEY (`rau_stw_id`)
        REFERENCES `stockwerk` (`stw_id`)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=22 DEFAULT CHARSET=UTF8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.stockwerk
DROP TABLE IF EXISTS `stockwerk`;
CREATE TABLE IF NOT EXISTS `stockwerk` (
    `stw_id` INT(11) NOT NULL AUTO_INCREMENT,
    `stw_bezeichnung` VARCHAR(50) DEFAULT NULL,
    `stw_guid` VARCHAR(50) DEFAULT NULL,
    `stw_geb_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`stw_id`),
    KEY `s_geb_id` (`stw_geb_id`),
    CONSTRAINT `s_geb_id` FOREIGN KEY (`stw_geb_id`)
        REFERENCES `gebaeude` (`geb_id`)
        ON DELETE CASCADE ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=146 DEFAULT CHARSET=UTF8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.wand
DROP TABLE IF EXISTS `wand`;
CREATE TABLE IF NOT EXISTS `wand` (
    `wan_id` INT(11) NOT NULL AUTO_INCREMENT,
    `wan_guid` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`wan_id`)
)  ENGINE=INNODB AUTO_INCREMENT=21 DEFAULT CHARSET=UTF8;


-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.maengel
DROP TABLE IF EXISTS `maengel`;
CREATE TABLE IF NOT EXISTS `maengel` (
    `mgl_id` INT(11) NOT NULL AUTO_INCREMENT,
    `mgl_aft_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`mgl_id`),
    KEY `mgl_a_id` (`mgl_aft_id`),
    CONSTRAINT `mgl_a_id` FOREIGN KEY (`mgl_aft_id`)
        REFERENCES `auftraege` (`aft_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=UTF8;


-- Exportiere Struktur von Tabelle IIB2_UE2_Gruppe09.lndokumentiert
DROP TABLE IF EXISTS `lndokumentiert`;
CREATE TABLE IF NOT EXISTS `lndokumentiert` (
    `ldo_id` INT(11) NOT NULL AUTO_INCREMENT,
    `ldo_dma_id` INT(11) DEFAULT NULL,
    `ldo_mgl_id` INT(11) DEFAULT NULL,
    `ldo_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `ldo_titel` VARCHAR(50) DEFAULT NULL,
    `ldo_bes` VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (`ldo_id`),
    KEY `dm_id` (`ldo_dma_id`),
    KEY `m_id` (`ldo_mgl_id`),
    CONSTRAINT `dm_id` FOREIGN KEY (`ldo_dma_id`)
        REFERENCES `dezernatmitarbeiter` (`dma_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `m_id` FOREIGN KEY (`ldo_mgl_id`)
        REFERENCES `maengel` (`mgl_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=UTF8;


DROP TABLE IF EXISTS `lnAftDln`;
CREATE TABLE IF NOT EXISTS `lnAftDln` (
    `lad_id` INT(11) NOT NULL AUTO_INCREMENT,
    `lad_aft_id` INT(11) DEFAULT NULL,
    `lad_dln_id` INT(11) DEFAULT NULL,
    PRIMARY KEY (`lad_id`),
    KEY `lad_a_id` (`lad_aft_id`),
    CONSTRAINT `lad_a_id` FOREIGN KEY (`lad_aft_id`)
        REFERENCES `auftraege` (`aft_id`)
        ON DELETE SET NULL ON UPDATE CASCADE,
    KEY `lad_d_id` (`lad_dln_id`),
    CONSTRAINT `lad_d_id` FOREIGN KEY (`lad_dln_id`)
        REFERENCES `dienstleistungen` (`dln_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
)  ENGINE=INNODB AUTO_INCREMENT=14 DEFAULT CHARSET=UTF8;
