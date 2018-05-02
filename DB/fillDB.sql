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
INSERT INTO `dienstleister` (`dlt_id`, `dlt_name`, `dlt_vorname`, `dlt_username`, `dlt_passwort`) VALUES
	(1, 'Burch', 'Timmy', 'Timmy!', _binary 0x54696D6D7921),
	(2, 'Fritz', 'Jörg', 'jf', _binary 0x0DC3C11CD09782F5404591EFE5631994065A774D),
	(3, 'Rieger', 'Bastian', NULL, NULL),
	(4, 'Friedrich', 'Friedl', 'Frieden', _binary 0x7065616365),
    (5, 'Tom', 'Mot', 'q', 1234);
/*!40000 ALTER TABLE `dienstleister` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.dezernatmitarbeiter: ~4 rows (ungefähr)
/*!40000 ALTER TABLE `dezernatmitarbeiter` DISABLE KEYS */;
INSERT INTO `dezernatmitarbeiter` (`dma_id`, `dma_name`, `dma_vorname`, `dma_username`, `dma_passwort`) VALUES
	(2, 'Böhler', 'Maria', 'm_boe', _binary 0x98DCB2717DDAE152D5B359C6EA97E4FE34A29D4C),
	(3, 'Feldhase', 'Frieda', 'zucker', _binary 0x73EFBFBDEFBFBD),
	(4, 'Schmidt', 'Karl', NULL, NULL),
	(5, 'Vogel', 'Nina', 'Kuecken', _binary 0x706965707321);
/*!40000 ALTER TABLE `dezernatmitarbeiter` ENABLE KEYS */;