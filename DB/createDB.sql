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
  `dln_beschreibung` varchar(50) DEFAULT NULL,
  `dln_haefigkeit` int(11) DEFAULT NULL,
  `dln_dma_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`dln_id`),
  KEY `dln_d_id` (`dln_dma_id`),
  CONSTRAINT `dln_d_id` FOREIGN KEY (`dln_dma_id`) REFERENCES `dezernatmitarbeiter` (`dma_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


-- Exportiere Struktur von Tabelle gebaeudemanagement.leistungsspektren
DROP TABLE IF EXISTS `leistungsspektren`;
CREATE TABLE IF NOT EXISTS `leistungsspektren` (
  `ls_id` int(11) NOT NULL AUTO_INCREMENT,
  `ls_dln_id` int(11) DEFAULT NULL,
  `ls_dma_id` int(11) DEFAULT NULL,
  `ls_preis` int(11) DEFAULT NULL,
  PRIMARY KEY (`ls_id`),
  KEY `ls_d_id` (`ls_dma_id`),
  CONSTRAINT `ls_d_id` FOREIGN KEY (`ls_dma_id`) REFERENCES `dezernatmitarbeiter` (`dma_id`) ON DELETE SET NULL ON UPDATE CASCADE
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;