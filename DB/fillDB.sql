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

-- Exportiere Daten aus Tabelle gebaeudemanagement.dienstleister: ~10 rows (ungefähr)
/*!40000 ALTER TABLE `dienstleister` DISABLE KEYS */;
INSERT INTO `dienstleister` (`dlr_id`, `dlr_firmaname`, `dlr_username`, `dlr_passwort`) VALUES
	(1, 'SGG', 'sgg', 1111),
	(2, 'Clou', 'clou', 3232),
	(3, 'Skybad', 'sb', 2323),
	(4, 'Unilux', 'uni', _binary 0x7065616365),
    (5, 'Weru', 'weru', 4321),
    (6, 'SolteQ', 'solteq', 1234),
    (7, 'IndPark', 'ipark', 'aaabb'),
    (8, 'Facility', 'facility', 74123),
    (9, 'Qualitiy Services', 'quality', 223654),
    (10, 'Eko', 'eko', 14258);
/*!40000 ALTER TABLE `dienstleister` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.dienstleister: ~12 rows (ungefähr)
/*!40000 ALTER TABLE `dienstleistungen` DISABLE KEYS */;
INSERT INTO `dienstleistungen` (`dln_id`, `dln_name`, `dln_beschreibung`, `dln_haefigkeit`, `dln_dma_id`) VALUES
	(1, 'Gebäudereinigung', 
    'Übernehmung der Planung, Organisation und Ausführung sämtlicher Reinigungsarbeiten. 
    Dabei soll durch den Einsatz umweltfreundlicher Reinigungsmittel auf Qualität geachtet werden.', 
    'Täglich', 1),
	(2, 'Bauschlussreinigung', 
    'Materialschonend Erstreinigung nach dem Abschluss', 
    'Einmalig', 1),
	(3, 'Fassadenreinigung', 'Glas, Beton, Stahl und andere Materialien 
	sind mittlerweile Bestandteil vieler Fassaden an den unterschiedlichsten Gebäuden.
	Nur durch fachgerechte Reinigung und regelmäßige Pflege kann eine ansprechende Optik 
    erhalten und dem umwelt- und witterungsbedingten Verfall Einhalt geboten werden.', 
    'Jeder 2. Tag', 1),
	(4, 'Graffitientfernung', 'So hat Graffiti keine Chance mehr, sich langfristig auf Ihrer Fassade zu halten.', 
    'Monatlich', 3),
    (5, 'Abfallentsorgung', 'Leerung der Mülleimern. Der Müll muss in die richtigen Tonnen geworfen werden', 'täglich', 4),
    (6, 'Winterdienste', 'Dazu gehört es die Schneeräumung und Streudienst. Es muss gewährleistet sein, dass die Gehwege
    und Parkplätze gefahrlos passierbar sind.', 'An verschneiten und regnerischen Wintertagen', 6),
    (7, 'Objektbuchhaltung', 'Diese Dienstleistung beinhaltet die Finanzierung, Miete, Pacht,
     Abschreibung, Steuern, Gebühren, Abgaben und Versicherungen des Immobilien', 'Taglich', 4),
    (8, 'Vertragsmanagement', 'Hier sollen alle Tätigkeiten im Rahmen des Projektmanagements, die sich mit der Entwicklung,
     Verwaltung, Anpassung, Abwicklung und Fortschreibung der Gesamtheit der Verträge im Rahmen eines Projektes beschäftigen übernommen werden',
     'Von dem Projekt abhängig', 9),
     (9,'Beschaffungsmanagent', 'Analyse, Bewertung und Auswahl der Lieferanten. In diesem Fall soll 
     für die geringstmöglichen Kosten versorgt werden.', 'Monatllich', 10), 
     (10, 'Anordnung der Arbeitsplätze', 'Die Anordnung der Arbeitsplätze soll unter Berücksichtigung von ergonomischen Aspekte erfolgen',
     'halbjährlich', 7),
     (11, 'Instandhaltung', 'Die Instandhaltung von technischen Systemen, Bauelementen, Geräten und Betriebsmitteln soll sicherstellen,
      dass der funktionsfähige Zustand erhalten bleibt oder bei Ausfall wiederhergestellt wird.', 'Täglich', 8),
      (12,'Catering für 30 Personen', NULL, '3 mal am Tag', 4);
/*!40000 ALTER TABLE `dienstleistungen` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.dezernatmitarbeiter: ~10 rows (ungefähr)
/*!40000 ALTER TABLE `dezernatmitarbeiter` DISABLE KEYS */;
INSERT INTO `dezernatmitarbeiter` (`dma_id`, `dma_vorname`, `dma_name`, `dma_username`, `dma_passwort`) VALUES
	(1, 'Emila', 'Wagner', 'wag', 2222),
	(2, 'Leon', 'Koch', 'lk', _binary 0x73EFBFBDEFBFBD),
	(3, 'Marie', 'Wolf', NULL, NULL),
	(4, 'Lukas', 'Braun', 'brau', _binary 0x706965707321),
	(5, 'Marie', 'Neumann', 'neu', 3333),
	(6, 'Lukas', 'Zimmermann', 'zimmer', 5555),
    (7, 'Igor', 'Antropov', 'antropov', 66666),
    (8, 'Max', 'Schulz', 'mschl', 142579),
    (9, 'Ana', 'Schmidt', 'ana', _binary 0x108565707012),
    (10, 'John', 'Hofmann', 'hofmann', 45782);
/*!40000 ALTER TABLE `dezernatmitarbeiter` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.leistungsspektren: ~10 rows (ungefähr)
/*!40000 ALTER TABLE `leistungsspektren` DISABLE KEYS */;
INSERT INTO `leistungsspektren` (`lsp_id`, `lsp_dlr_id`) VALUES
    (1, 1), 
    (2, 2), 
    (3, 3), 
    (4, 4), 
    (5, 4), 
    (6, 5), 
    (7, 6), 
    (8, 1), 
    (9, 6), 
    (10, 7), 
    (11, 8), 
    (12, 8), 
    (13, 9), 
    (14, 10), 
    (15, 8);
/*!40000 ALTER TABLE `leistungsspektren` ENABLE KEYS */;


-- Exportiere Daten aus Tabelle gebaeudemanagement.lnLspDln: ~29 rows (ungefähr)
/*!40000 ALTER TABLE `lnLspDln` DISABLE KEYS */;
INSERT INTO `lnLspDln` (`lld_id`, `lld_lsp_id`, `lld_dln_id`, `lld_preis`) VALUES
    (1, 1, 1, 200), 
    (2, 1, 2, 20000), 
    (3, 1, 3, 50),
    (4, 2, 4, 200), 
    (5, 2, 7, 550), 
    (6, 2, 8, 500),
    (7, 3, 5, 80), 
    (8, 3, 6, 120), 
    (9, 3, 9, 1350),
    (10, 4, 1, 270), 
    (11, 4, 2, 18500), 
    (12, 4, 3, 50), 
    (13, 4, 4, 200), 
    (14, 4, 5, 100), 
    (15, 4, 6, 132),
    (16, 5, 7, 530), 
    (17, 5, 8, 525), 
    (18, 5, 9, 1250), 
    (19, 5, 12, 1360),
    (20, 10, 3, 50), 
    (21, 10, 4, 200),
    (22, 10, 5, 85), 
    (23, 10, 6, 133), 
    (24, 10, 7, 525),
    (25, 8, 3, 50), 
    (26, 8, 4, 199), 
    (27, 8, 5, 75), 
    (28, 8, 6, 145),
    (29, 15, 12, 1500);  
/*!40000 ALTER TABLE `lnLspDln` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.auftraege: ~4 rows (ungefähr)
/*!40000 ALTER TABLE `auftraege` DISABLE KEYS */;
INSERT INTO `auftraege`(`aft_id`, `aft_dma_id`, `aft_dlr_id`, `aft_status`) VALUES
    (1, 1,1,'Ausführung'),
    (2, 1,1,'Erledigt'),
    (3, 1,4,'Warte auf eine Antwort'),
    (4, 3,4,'Erledigt');
/*!40000 ALTER TABLE `auftraege` ENABLE KEYS */;


-- Exportiere Daten aus Tabelle gebaeudemanagement.auftraege: ~4 rows (ungefähr)
/*!40000 ALTER TABLE `auftraege` DISABLE KEYS */;

INSERT INTO `auftraege` (`aft_id`,`aft_dma_id`, `aft_dlr_id`, `aft_datum`, `aft_status`) VALUES
    (5, 4,7, '2018-01-01 20:00:00', 'Abgelehnt'),
    (6, 2,8, '2018-05-01 11:30:05', 'Abgelehnt'),
    (7, 7,2, '2018-05-07 10:00:00', 'Warte auf eine Antwort');
/*!40000 ALTER TABLE `auftraege` ENABLE KEYS */;


-- Exportiere Daten aus Tabelle gebaeudemanagement.lnAftDln: ~13 rows (ungefähr)
/*!40000 ALTER TABLE `lnAftDln` DISABLE KEYS */;
INSERT INTO `lnAftDln` (`lad_id`, `lad_aft_id`,`lad_dln_id`) VALUES
    (1, 1,1), (2, 1,2), (3, 1,3),
    (4, 2,4), (5, 2,5),
    (6, 3,6), (7, 4,7), (8 ,4,8),
    (9, 5,3), (10, 5,4), 
    (11, 6,12),
    (12, 7, 4), (13, 7,7);
/*!40000 ALTER TABLE `lnAftDln` ENABLE KEYS */;


-- Exportiere Daten aus Tabelle gebaeudemanagement.maengel: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `maengel` DISABLE KEYS */;
INSERT INTO `maengel`(`mgl_id`, `mgl_dln_id`, `mgl_dlr_id`) VALUES
    (1, 2,1), (2, 4,4);
/*!40000 ALTER TABLE `maengel` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.lndokumentiert: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `lndokumentiert` DISABLE KEYS */;
INSERT INTO `lndokumentiert`(`ldo_dma_id`,`ldo_mgl_id`, `ldo_titel`, `ldo_bes`) VALUES
    (2,1, 'Gebäudereinigung wurde nicht richtig durchgeführt', 'Zwei Räume wurden nicht sauber gemacht'),
    (3,2, 'Graffiti im Ausgang wurde nicht entfernt', 'Siehe Titel');
/*!40000 ALTER TABLE `lndokumentiert` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.wand: ~27 rows (ungefähr)
/*!40000 ALTER TABLE `wand` DISABLE KEYS */;
INSERT INTO `wand`(`wan_id`) VALUES
    (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12), 
    (13), (14), (15), (16), (17), (18), (19), (20), (21), (22), (23),
    (24), (25), (26), (27);
/*!40000 ALTER TABLE `wand` ENABLE KEYS */;


-- Exportiere Daten aus Tabelle gebaeudemanagement.gebaeude: ~ 2 rows (ungefähr)
/*!40000 ALTER TABLE `wand` DISABLE KEYS */;
INSERT INTO `gebaeude`(`geb_id`, `geb_strasse`, `geb_hausnr`, `geb_ort`,
            `geb_plz`, `geb_dma_id`) VALUES
    (1, 'Hauptstr.', 1, 'Darmstadt', 64287, 3),
    (2, 'Hauptstr.', 2, 'Darmstadt', 64287, 4);
/*!40000 ALTER TABLE `gebaeude` ENABLE KEYS */;


-- Exportiere Daten aus Tabelle gebaeudemanagment.stockwewrk: ~2 rows
/*!40000 ALTER TABLE `stockwerk` DISABLE KEYS */;
INSERT INTO `stockwerk`(`stw_id`, `stw_bezeichnung`,`stw_geb_id`) VALUES
    (1, 'EG', 1),  (2, '1.OG', 1),(3, '2.OG', 1),
    (4, 'EG', 2),  (5, '1.OG', 2),(6, '2.OG', 2);
     /*!40000 ALTER TABLE `stockwerk` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagment.raum: ~9 rows
/*!40000 ALTER TABLE `raum` DISABLE KEYS */;
INSERT INTO `raum`(`rau_id`, `rau_nummer`, `rau_bezeichnung`, `rau_stw_id`) VALUES
    (1, '0', 'H1/00', 1), (2, '1', 'H1/10', 2), (3, '2', 'H1/21', 3), (4, '3', 'H1/22', 3),
    (5, '0', 'H2/00', 4), (6, '1', 'H2/11', 5), (7, '2', 'H2/12', 5), (8, '3', 'H2/13', 5),
    (9, '4', 'H2/20', 6) ;
/*!40000 ALTER TABLE `raum` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagment.lnraumwand: ~20 rows (ungefähr)
/*!40000 ALTER TABLE `lnraumwand` DISABLE KEYS */;
INSERT INTO `lnraumwand`(`lrw_id`, `lrw_rau_id`, `lrw_wan_id`) VALUES
    (1, 1, 1), (2, 1, 2), (3, 1, 3), (4, 1, 4),
    (5, 2, 5), (6, 2,6), (7, 2, 7), (8, 2, 8),
    (9, 3, 9), (10, 3, 11), (11, 3, 12), (12, 3, 13),
    (13, 4, 9), (14, 4, 10), (15, 4, 11), (16, 4, 13),
    (17, 5, 14), (18, 5, 15), (19, 5, 16), (20, 5, 17),
    (21, 6, 18), (22, 6, 19), (23, 6, 23), (24, 6, 22),
    (25, 7, 19), (26, 7, 20), (27, 7, 22), (28, 7,23),
    (29, 8, 18), (30, 8, 20), (31, 8, 21), (32, 8, 22),
    (33, 9, 24), (34, 9, 25), (35, 9, 26), (36, 9, 27);
/*!40000 ALTER TABLE `lnraumwand` ENABLE KEYS */;
