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


-- Exportiere Datenbank Struktur für gebaeudemanagement
DROP DATABASE IF EXISTS `gebaeudemanagement`;
CREATE DATABASE IF NOT EXISTS `gebaeudemanagement` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `gebaeudemanagement`;

-- Exportiere Struktur von Tabelle gebaeudemanagement.dienstleister
DROP TABLE IF EXISTS `dienstleister`;
CREATE TABLE IF NOT EXISTS `dienstleister` (
  `dlr_id` int(11) NOT NULL AUTO_INCREMENT,
  `dlr_firmaname` varchar(50) DEFAULT NULL,
  `dlr_username` varchar(50) DEFAULT NULL,
  `dlr_passwort` blob,
  PRIMARY KEY (`dlr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle gebaeudemanagement.dienstleister
DROP TABLE IF EXISTS `dienstleistungen`;
CREATE TABLE IF NOT EXISTS `dienstleistungen` (
  `dln_id` int(11) NOT NULL AUTO_INCREMENT,
  `dln_name` varchar(50) DEFAULT NULL,
  `dln_beschreibung` text(255) DEFAULT NULL,
  `dln_haefigkeit` int(11) DEFAULT NULL,
  `dln_dma_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`dln_id`),
  KEY `dln_d_id` (`dln_dma_id`),
  CONSTRAINT `dln_d_id` FOREIGN KEY (`dln_dma_id`) REFERENCES `dezernatmitarbeiter` (`dlr_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


-- Exportiere Struktur von Tabelle gebaeudemanagement.leistungsspektren
DROP TABLE IF EXISTS `leistungsspektren`;
CREATE TABLE IF NOT EXISTS `leistungsspektren` (
  `ls_id` int(11) NOT NULL AUTO_INCREMENT,
  `ls_dln_id` int(11) DEFAULT NULL,
  `ls_dlr_id` int(11) DEFAULT NULL,
  `ls_preis` int(11) DEFAULT NULL,
  PRIMARY KEY (`ls_id`),
  KEY `ls_d_id` (`ls_dlr_id`),
  KEY `ls_l_id` (`ls_dln_id`),
  CONSTRAINT `ls_d_id` FOREIGN KEY (`ls_dlr_id`) REFERENCES `dezernatmitarbeiter` (`dma_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `ls_l_id` FOREIGN KEY (`ls_dln_id`) REFERENCES `dienstleistungen` (`dln_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle gebaeudemanagement.dezernatmitarbeiter
DROP TABLE IF EXISTS `dezernatmitarbeiter`;
CREATE TABLE IF NOT EXISTS `dezernatmitarbeiter` (
  `dma_id` int(11) NOT NULL AUTO_INCREMENT,
  `dma_name` varchar(50) DEFAULT NULL,
  `dma_vorname` varchar(50) DEFAULT NULL,
  `dma_username` varchar(50) DEFAULT NULL,
  `dma_passwort` blob,
  PRIMARY KEY (`dma_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `gebaeude`;
CREATE TABLE IF NOT EXISTS `gebaeude` (
  `geb_id` int(11) NOT NULL AUTO_INCREMENT,
  `geb_strasse` varchar(50) DEFAULT NULL,
  `geb_hausnr` varchar(4) DEFAULT NULL,
  `geb_ort` varchar(50) DEFAULT NULL,
  `geb_plz`int(6) unsigned DEFAULT NULL,
  `geb_dma_id` int(11) DEFAULT NULL,
   PRIMARY KEY (`geb_id`),
  KEY `geb_d_id` (`geb_dma_id`),
  CONSTRAINT `geb_d_id` FOREIGN KEY (`geb_dma_id`) REFERENCES `dezernatmitarbeiter` (`dma_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle gebaeudemanagement.raum
DROP TABLE IF EXISTS `raum`;
CREATE TABLE IF NOT EXISTS `raum` (
  `rau_id` int(11) NOT NULL AUTO_INCREMENT,
  `rau_nummer` varchar(5) DEFAULT NULL,
  `rau_bezeichnung` varchar(50) DEFAULT NULL, 
  `rau_stw_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`rau_id`),
  KEY `r_s_id` (`rau_stw_id`),
  CONSTRAINT `r_s_id` FOREIGN KEY (`rau_stw_id`) REFERENCES `stockwerk` (`stw_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle gebaeudemanagement.stockwerk
DROP TABLE IF EXISTS `stockwerk`;
CREATE TABLE IF NOT EXISTS `stockwerk` (
  `stw_id` int(11) NOT NULL AUTO_INCREMENT,
  `stw_bezeichnung` varchar(50) DEFAULT NULL,
  `stw_geb_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`stw_id`),
  KEY `s_geb_id` (`stw_geb_id`),
  CONSTRAINT `s_geb_id` FOREIGN KEY (`stw_geb_id`) REFERENCES `gebaeude` (`geb_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
-- Exportiere Struktur von Tabelle gebaeudemanagement.wand
DROP TABLE IF EXISTS `wand`;
CREATE TABLE IF NOT EXISTS `wand` (
  `wan_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`wan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- Exportiere Struktur von Tabelle gebaeudemanagement.lnraumwand
DROP TABLE IF EXISTS `lnraumwand`;
CREATE TABLE IF NOT EXISTS `lnraumwand` (
  `lrw_id` int(11) NOT NULL AUTO_INCREMENT,
  `lrw_rau_id` int(11) DEFAULT NULL,
  `lrw_wan_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`lrw_id`),
  KEY `r_id` (`lrw_rau_id`),
  KEY `w_id` (`lrw_wan_id`),
  CONSTRAINT `r_id` FOREIGN KEY (`lrw_rau_id`) REFERENCES `raum` (`rau_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `w_id` FOREIGN KEY (`lrw_wan_id`) REFERENCES `wand` (`wan_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


-- Exportiere Struktur von Tabelle gebaeudemanagement.maengel
DROP TABLE IF EXISTS `maengel`;
CREATE TABLE IF NOT EXISTS `maengel` (
  `mgl_id` int(11) NOT NULL AUTO_INCREMENT,
  `mgl_dln_id` int(11) DEFAULT NULL,
  `mgl_dlr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`mgl_id`) ,
  KEY `dn_id` (`mgl_dln_id`),
  KEY `dr_id` (`mgl_dlr_id`),
  CONSTRAINT `dn_id` FOREIGN KEY (`mgl_dln_id`) REFERENCES `dienstleistungen` (`dln_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `dr_id` FOREIGN KEY (`mgl_dlr_id`) REFERENCES `dienstleister` (`dlr_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;


-- Exportiere Struktur von Tabelle gebaeudemanagement.lndokumentiert
DROP TABLE IF EXISTS `lndokumentiert`;
CREATE TABLE IF NOT EXISTS `lndokumentiert` (
  `ldo_id` int(11) NOT NULL AUTO_INCREMENT,
  `ldo_dma_id` int(11) DEFAULT NULL,
  `ldo_mgl_id` int(11) DEFAULT NULL,
  `ldo_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `ldo_titel` varchar(50) DEFAULT NULL,
  `ldo_bes`  varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ldo_id`),
   KEY `dm_id` (`ldo_dma_id`),
   KEY `m_id` (`ldo_mgl_id`),
  CONSTRAINT `dm_id` FOREIGN KEY (`ldo_dma_id`) REFERENCES `dezernatmitarbeiter` (`dma_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `m_id` FOREIGN KEY (`ldo_mgl_id`) REFERENCES `maengel` (`mgl_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `auftraege`;
CREATE TABLE IF NOT EXISTS `auftraege` (
  `aft_id` int(11) NOT NULL AUTO_INCREMENT,
  `aft_dma_id` int(11) DEFAULT NULL,
  `aft_dlr_id` int(11) DEFAULT NULL,
  `aft_datum` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `aft_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`aft_id`),
  KEY `aft_d_id` (`aft_dma_id`),
  CONSTRAINT `aft_d_id` FOREIGN KEY (`aft_dma_id`) REFERENCES `dezernatmitarbeiter`(`dma_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  KEY `a_dr_id` (`aft_dlr_id`),
  CONSTRAINT `a_dr_id` FOREIGN KEY (`aft_dlr_id`) REFERENCES `dienstleister`(`dlr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `lnAftDln`;
CREATE TABLE IF NOT EXISTS `lnAftDln` (
  `lad_id` int(11) NOT NULL AUTO_INCREMENT,
  `lad_aft_id` int(11) DEFAULT NULL,
  `lad_dln_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`lad_id`),
  KEY `lad_a_id` (`lad_aft_id`),
  CONSTRAINT `lad_a_id` FOREIGN KEY (`lad_aft_id`) REFERENCES `auftraege`(`aft_id`) ON DELETE SET NULL  ON UPDATE CASCADE,
  KEY `lad_d_id` (`lad_dln_id`),
  CONSTRAINT `lad_d_id` FOREIGN KEY (`lad_dln_id`) REFERENCES `dienstleistungen` (`dln_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
 
-- Exportiere Struktur von Tabelle gebaeudemanagement.leistungspektren1
DROP TABLE IF EXISTS `leistungsspektren1`;
CREATE TABLE IF NOT EXISTS `leistungsspektren1` (
  `lsp_id` int(11) NOT NULL AUTO_INCREMENT,
  `lsp_dlr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`lsp_id`),
  KEY `lsp_d_id` (`lsp_dlr_id`),
  CONSTRAINT `lsp_d_id` FOREIGN KEY (`lsp_dlr_id`) REFERENCES `dienstleister` (`dlr_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

 
-- Exportiere Struktur von Tabelle gebaeudemanagement.lnLspDln
DROP TABLE IF EXISTS `lnLspDln`;
CREATE TABLE IF NOT EXISTS `lnLspDln` (
  `lld_id` int(11) NOT NULL AUTO_INCREMENT,
  `lld_dln_id` int(11) DEFAULT NULL,
  `lld_lsp_id` int(11) DEFAULT NULL,
  `lld_preis` int(11) DEFAULT NULL,
  PRIMARY KEY (`lld_id`),
  KEY `lld_d_id` (`lld_dln_id`),
  KEY `lld_l_id` (`lld_lsp_id`),
  CONSTRAINT `lld_d_id` FOREIGN KEY (`lld_dln_id`)
  REFERENCES `dienstleistungen` (`dln_id`)
    ON DELETE SET NULL 
    ON UPDATE CASCADE,
  CONSTRAINT `lld_l_id` FOREIGN KEY (`lld_lsp_id`)
  REFERENCES `lesistungsspektren1` (`lsp_id`)
    ON DELETE SET NULL 
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
