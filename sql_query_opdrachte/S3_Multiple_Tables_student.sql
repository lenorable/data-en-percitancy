-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S3: Multiple Tables
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
--
--
-- Opdracht: schrijf SQL-queries om onderstaande resultaten op te vragen,
-- aan te maken, verwijderen of aan te passen in de database van de
-- bedrijfscasus.
--
-- Codeer je uitwerking onder de regel 'DROP VIEW ...' (bij een SELECT)
-- of boven de regel 'ON CONFLICT DO NOTHING;' (bij een INSERT)
-- Je kunt deze eigen query selecteren en los uitvoeren, en wijzigen tot
-- je tevreden bent.
--
-- Vervolgens kun je je uitwerkingen testen door de testregels
-- (met [TEST] erachter) te activeren (haal hiervoor de commentaartekens
-- weg) en vervolgens het hele bestand uit te voeren. Hiervoor moet je de
-- testsuite in de database hebben geladen (bedrijf_postgresql_test.sql).
-- NB: niet alle opdrachten hebben testregels.
--
-- Lever je werk pas in op Canvas als alle tests slagen.
-- ------------------------------------------------------------------------


-- S3.1.
-- Produceer een overzicht van alle cursusuitvoeringen; geef de
-- code, de begindatum, de lengte en de naam van de docent.
DROP VIEW IF EXISTS s3_1; CREATE OR REPLACE VIEW s3_1 AS                                                   
select c.code, u.begindatum, c.lengte, m.naam as maam_docent from cursussen c join uitvoeringen u on c.code = u.cursus join medewerkers m ON m.mnr = u.docent;

-- S3.2.
-- Geef in twee kolommen naast elkaar de achternaam van elke cursist (`cursist`)
-- van alle S02-cursussen, met de achternaam van zijn cursusdocent (`docent`).
DROP VIEW IF EXISTS s3_2; CREATE OR REPLACE VIEW s3_2 AS                                                   
SELECT medewerkers1.naam AS cursist, medewerkers2.naam AS docent
FROM inschrijvingen 
JOIN medewerkers medewerkers1 ON medewerkers1.mnr = inschrijvingen.cursist
JOIN uitvoeringen ON (uitvoeringen.cursus = inschrijvingen.cursus AND uitvoeringen.begindatum = inschrijvingen.begindatum)
JOIN medewerkers medewerkers2 ON medewerkers2.mnr = uitvoeringen.docent
WHERE uitvoeringen.cursus = 'S02';


-- S3.3.
-- Geef elke afdeling (`afdeling`) met de naam van het hoofd van die
-- afdeling (`hoofd`).
DROP VIEW IF EXISTS s3_3; CREATE OR REPLACE VIEW s3_3 AS             
select a.naam as afdeling, m.naam as hoofd from medewerkers m join afdelingen a on a.hoofd = m.mnr;
-- select medewerkers.naam from medewerkers left join inschrijvingen ON (inschrijvingen.cursist = medewerkers.mnr) left join uitvoeringen ON uitvoeringen.docent = medewerkers.mnr where uitvoeringen.cursus = 'S02' or inschrijvingen.cursus = 'S02';


-- S3.4.
-- Geef de namen van alle medewerkers, de naam van hun afdeling (`afdeling`)
-- en de bijbehorende locatie.
DROP VIEW IF EXISTS s3_4; CREATE OR REPLACE VIEW s3_4 AS                                                   
select m.naam, a.naam as afdeling, a.locatie from medewerkers m join afdelingen a on a.anr = m.afd;

-- S3.5.
-- Geef de namen van alle cursisten die staan ingeschreven voor de cursus S02 van 12 april 2019
DROP VIEW IF EXISTS s3_5; CREATE OR REPLACE VIEW s3_5 AS                                                   
select m.naam from inschrijvingen i join medewerkers m on m.mnr = i.cursist where (i.cursus = 'S02' AND i.begindatum = '12-04-2019');

-- S3.6.
-- Geef de namen van alle medewerkers en hun toelage.
DROP VIEW IF EXISTS s3_6; CREATE OR REPLACE VIEW s3_6 AS                                                   
SELECT medewerkers.naam, schalen.toelage FROM medewerkers LEFT JOIN schalen ON (medewerkers.maandsal >= schalen.ondergrens AND medewerkers.maandsal <= schalen.bovengrens);

-- -------------------------[ HU TESTRAAMWERK ]--------------------------------
-- Met onderstaande query kun je je code testen. Zie bovenaan dit bestand
-- voor uitleg.

SELECT * FROM test_select('S3.1') AS resultaat
UNION
SELECT * FROM test_select('S3.2') AS resultaat
UNION
SELECT * FROM test_select('S3.3') AS resultaat
UNION
SELECT * FROM test_select('S3.4') AS resultaat
UNION
SELECT * FROM test_select('S3.5') AS resultaat
UNION
SELECT * FROM test_select('S3.6') AS resultaat
ORDER BY resultaat;
