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

-- Exportiere Daten aus Tabelle gebaeudemanagement.dienstleister: ~4 rows (ungefähr)
/*!40000 ALTER TABLE `dienstleister` DISABLE KEYS */;
INSERT INTO `dienstleister` (`dlt_id`, `dlt_vorname`, `dlt_name`, `dlt_username`, `dlt_passwort`) VALUES
	(1, 'Mia', 'Mueller', 'mia', 1111),
	(2, 'Ben', 'Schmidt', 'ben', 3232),
	(3, 'Emma', 'Schulz', NULL, NULL),
	(4, 'Paul', 'Friedl', 'Frieden', _binary 0x7065616365),
    (5, 'Anna', 'Weber', 'web', 4321),
    (6, 'Igor', 'Antropov', 'antropov', 1234);
/*!40000 ALTER TABLE `dienstleister` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.dezernatmitarbeiter: ~4 rows (ungefähr)
/*!40000 ALTER TABLE `dezernatmitarbeiter` DISABLE KEYS */;
INSERT INTO `dezernatmitarbeiter` (`dma_id`, `dma_vorname`, `dma_name`, `dma_username`, `dma_passwort`) VALUES
	(1, 'Emila', 'Wagner', 'wag', 2222),
	(2, 'Leon', 'Koch', 'lk', _binary 0x73EFBFBDEFBFBD),
	(3, 'Marie', 'Wolf', NULL, NULL),
	(4, 'Lukas', 'Braun', 'brau', _binary 0x706965707321),
	(5, 'Marie', 'Neumann', 'neu', 3333),
	(6, 'Lukas', 'Zimmermann', 'zimmer', 6666);
/*!40000 ALTER TABLE `dezernatmitarbeiter` ENABLE KEYS */;