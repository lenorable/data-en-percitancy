-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S6: Views
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------


-- S6.1.
--
-- 1. Maak een view met de naam "deelnemers" waarmee je de volgende gegevens uit de tabellen inschrijvingen en uitvoering combineert:
--    inschrijvingen.cursist, inschrijvingen.cursus, inschrijvingen.begindatum, uitvoeringen.docent, uitvoeringen.locatie
-- 2. Gebruik de view in een query waarbij je de "deelnemers" view combineert met de "personeels" view (behandeld in de les):
--     CREATE OR REPLACE VIEW personeel AS
-- 	     SELECT mnr, voorl, naam as medewerker, afd, functie
--       FROM medewerkers;
-- 3. Is de view "deelnemers" updatable ? Waarom ?

-- 1
CREATE OR REPLACE VIEW deelnemers AS
select i.cursist, i.cursus, i.begindatum, u.docent, u.locatie 
from inschrijvingen i 
join uitvoeringen u 
ON (u.cursus, u.begindatum) = (i.cursus, i.begindatum);

CREATE OR REPLACE VIEW personeel AS
SELECT mnr, voorl, naam as medewerker, afd, functie
FROM medewerkers;

-- 2
select * from deelnemers dnmr join personeel pers on dnmr.docent = pers.mnr;

-- 3
-- de view "Deelnemers is niet updatable omdat er in join in deze view zit. het kan niet updateable zijn als het niet gaat om 1 tabel"

-- S6.2.
--
-- 1. Maak een view met de naam "dagcursussen". Deze view dient de gegevens op te halen: 
--      code, omschrijving en type uit de tabel curssussen met als voorwaarde dat de lengte = 1. Toon aan dat de view werkt. 
-- 2. Maak een tweede view met de naam "daguitvoeringen". 
--    Deze view dient de uitvoeringsgegevens op te halen voor de "dagcurssussen" (gebruik ook de view "dagcursussen"). Toon aan dat de view werkt
-- 3. Verwijder de views en laat zien wat de verschillen zijn bij DROP view <viewnaam> CASCADE en bij DROP view <viewnaam> RESTRICT

-- 1. Maak een view met de naam "dagcursussen". Deze view dient de gegevens op te halen: 
--      code, omschrijving en type uit de tabel curssussen met als voorwaarde dat de lengte = 1. Toon aan dat de view werkt. 
-- 2. Maak een tweede view met de naam "daguitvoeringen". 
--    Deze view dient de uitvoeringsgegevens op te halen voor de "dagcurssussen" (gebruik ook de view "dagcursussen"). Toon aan dat de view werkt
-- 3. Verwijder de views en laat zien wat de verschillen zijn bij DROP view <viewnaam> CASCADE en bij DROP view <viewnaam> RESTRICT

-- 1
CREATE OR REPLACE VIEW dagcursussen AS
select curs.code, curs.omschrijving, curs.type 
from cursussen curs 
where curs.lengte = 1;

-- prove opdracht 1
select dagcursussen.type, dagcursussen.omschrijving, cursussen.lengte from dagcursussen left join cursussen on dagcursussen."code" = cursussen."code";

--2 
CREATE OR REPLACE VIEW daguitvoeringen AS
select * from dagcursussen dagc 
join uitvoeringen uitv 
on dagc.code = uitv.cursus;

-- prove opdracht 2
select * from cursussen c join uitvoeringen u on c.code = u.cursus where c.lengte = 1; -- levert het zelfde op
select * from daguitvoeringen join cursussen c on c.code = daguitvoeringen.code; -- laat je zien dat ze allemaal lengte = 1 hebben

-- 3
-- DROP VIEW dagcursussen RESTRICT; -- doet het niet niet omdat de daguitvoeringen nog een dependacy heeft op deze view.

DROP VIEW dagcursussen CASCADE; -- doet het wel omdat hij ook meteen de daguitvoeringen verwijdert.