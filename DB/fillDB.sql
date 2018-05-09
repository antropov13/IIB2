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
INSERT INTO `dienstleister` (`dlr_id`, `dlr_firmaname`, `dlr_username`, `dlr_passwort`) VALUES
	(1, 'SGG', 'sgg', 1111),
	(2, 'Clou', 'clou', 3232),
	(3, 'Skybad', 'sb', 2323),
	(4, 'Unilux', 'uni', _binary 0x7065616365),
    (5, 'Weru', 'weru', 4321),
    (6, 'SolteQ', 'solteq', 1234),
    (7, 'IndPark', 'ipark', 'aaabb'),
    (8, 'Facility', 'facility', 74123)
    (9, 'Qualitiy Services', 'quality', 223654),
    (10, 'Eko', 'eko', 14258);
/*!40000 ALTER TABLE `dienstleister` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.dienstleister: ~4 rows (ungefähr)
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
      dass der funktionsfähige Zustand erhalten bleibt oder bei Ausfall wiederhergestellt wird.', 'Täglich', 8)
      (12,'Catering für 30 Personen', NULL, '3 mal am Tag', 4)
/*!40000 ALTER TABLE `dienstleistungen` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.dezernatmitarbeiter: ~4 rows (ungefähr)
/*!40000 ALTER TABLE `leistungsspektren` DISABLE KEYS */;
INSERT INTO `leistungsspektren` (`ls_id`, `ls_dln_id`, `ls_dlr_id`, `ls_preis`) VALUES
	(1, 1, 1, 200),
	(2, 2, 1, 300),
    (3, 3, 1, 250),
    (4, 1, 2, 210),
    (5, 2, 2, 260),
    (6, 1, 3, 300),
    (7, 2, 3, 100),
    (8, 3, 3, 150),
    (9, 4, 6, 350),
    (10, 4, 1, 350),
    (11, 4, 1, 350),
    (12, 4, 1, 350),
    (13, 4, 1, 350),
    (14, 4, 1, 350),
    (15, 4, 1, 350),
    (16, 4, 1, 350),
    (17, 4, 1, 350);
/*!40000 ALTER TABLE `leistungsspektren` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle gebaeudemanagement.dezernatmitarbeiter: ~4 rows (ungefähr)
/*!40000 ALTER TABLE `dezernatmitarbeiter` DISABLE KEYS */;
INSERT INTO `dezernatmitarbeiter` (`dma_vorname`, `dma_name`, `dma_username`, `dma_passwort`) VALUES
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

INSERT INTO `leistungsspektren1` (`lsp_dlr_id`) VALUES
    (1), (2), (3), (4), (4), (5), (6), (1), 
    (6), (7), (8), (8), (9), (10), (8);

INSERT INTO `lnLspDln` (`lld_lsp_id`, `lld_dln_id`, `lld_preis`) VALUES
    (1, 1, 200), (1, 2, 20000), (1, 3, 50),
    (2, 4, 200), (2, 7, 550), (2, 8, 500),
    (3, 5, 80), (3, 6, 120), (3, 9, 1350),
    (4, 1, 270), (4, 2, 18500), (4, 3, 50), (4, 4, 200), (4, 5, 100), (4, 6, 132),
    (5, 7, 530), (5, 8, 525), (5, 9, 1250), (5, 12, 1360),
    (10, 3, 50), (10, 4, 200) ,(10, 5, 85), (10, 6, 133), (10, 7, 525),
    (8, 3, 50), (8, 4, 199), (8, 5, 75), (8, 6, 145),
    (15, 12, 1500);  


INSERT INTO `auftraege`(`aft_dma_id`, `aft_dlr_id`, `aft_status`) VALUES
    (1,1,'Ausführung'),
    (1,1,'Erledigt'),
    (1,4,'Warte auf eine Antwort'),
    (3,4,'Erledigt')
    ;

INSERT INTO `auftraege` (`aft_dma_id`, `aft_dlr_id`, `aft_datum`, `aft_status`) VALUES
    (4,7, '2018-01-01 20:00:00', 'Abgelehnt'),
    (2,8, '2018-05-01 11:30:05', 'Abgelehnt'),
    (7,2, '2018-05-07 10:00:00', 'Warte auf eine Antwort');

INSERT INTO `lnAftDln` (`lad_aft_id`,`lad_dln_id`) VALUES
    (1,1), (1,2), (1,3),
    (2,4), (2,5),
    (3,6), (4,7), (4,8),
    (5,3), (5,4), 
    (6,12),
    (7, 4), (7,7);

INSERT INTO `maengel`(`mgl_dln_id`, `mgl_dlr_id`) VALUES
    (2,1), (4,4);

INSERT INTO `lndokumentiert`(`ldo_dma_id`,`ldo_mgl_id`, `ldo_titel`, `ldo_bes`) VALUES
    (2,1, 'Gebäudereinigung wurde nicht richtig durchgeführt', 'Zwei Räume wurden nicht sauber gemacht'),
    (3,2, 'Graffiti im Ausgang wurde nicht entfernt', 'Siehe Titel');

    