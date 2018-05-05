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
  `dlt_id` int(11) NOT NULL AUTO_INCREMENT,
  `dlt_name` varchar(50) DEFAULT NULL,
  `dlt_vorname` varchar(50) DEFAULT NULL,
  `dlt_username` varchar(50) DEFAULT NULL,
  `dlt_passwort` blob,
  PRIMARY KEY (`dlt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


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

DROP TABLE IF EXISTS `auftraege`;
CREATE TABLE IF NOT EXISTS `auftraege` (
  `auft_id` int(11) NOT NULL AUTO_INCREMENT,
  `auft_dleistungen` varchar(50) DEFAULT NULL,
  `auft_ersteller` varchar(50) DEFAULT NULL,
  `auft_dleister` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`auft_id`),
  FOREIGN KEY (`auft_dleistungen`)
  REFERENCES dienstleistungen(`dlstng_name`),
  FOREIGN KEY (`auft_ersteller`)
  REFERENCES dezernatmitarbeiter(`dma_id`),
  FOREIGN KEY (`auft_dleister`)
  REFERENCES dienstleister(`dlt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dienstleistungen`;
CREATE TABLE IF NOT EXISTS `auftraege` (
  `dleistung_name` int(11) NOT NULL AUTO_INCREMENT,
  `dleistung_bes` varchar(50) DEFAULT NULL,
  `dleistung_haeufigkeit` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dleistung_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;