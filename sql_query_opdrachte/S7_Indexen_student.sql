-- ------------------------------------------------------------------------
-- Data & Persistency
-- Opdracht S7: Indexen
--
-- (c) 2020 Hogeschool Utrecht
-- Tijmen Muller (tijmen.muller@hu.nl)
-- André Donk (andre.donk@hu.nl)
-- ------------------------------------------------------------------------
-- LET OP, zoals in de opdracht op Canvas ook gezegd kun je informatie over
-- het query plan vinden op: https://www.postgresql.org/docs/current/using-explain.html


-- S7.1.
--
-- Je maakt alle opdrachten in de 'sales' database die je hebt aangemaakt en gevuld met
-- de aangeleverde data (zie de opdracht op Canvas).
--
-- Voer het voorbeeld uit wat in de les behandeld is:
-- 1. Voer het volgende EXPLAIN statement uit:
--    EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
--    Bekijk of je het resultaat begrijpt. Kopieer het explain plan onderaan de opdracht
-- 2. Voeg een index op stock_item_id toe:
--    CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- 3. Analyseer opnieuw met EXPLAIN hoe de query nu uitgevoerd wordt
--    Kopieer het explain plan onderaan de opdracht
-- 4. Verklaar de verschillen. Schrijf deze hieronder op.

-- 1. Voer het volgende EXPLAIN statement uit:
--    EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
--    Bekijk of je het resultaat begrijpt. Kopieer het explain plan onderaan de opdracht
-- 2. Voeg een index op stock_item_id toe:
--    CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- 3. Analyseer opnieuw met EXPLAIN hoe de query nu uitgevoerd wordt
--    Kopieer het explain plan onderaan de opdracht
-- 4. Verklaar de verschillen. Schrijf deze hieronder op.

-- 1
EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
-- Parallel Seq Scan on order_lines  (cost=0.00..5051.27 rows=419 width=96)

-- 2
-- CREATE INDEX ord_lines_si_id_idx ON order_lines (stock_item_id);
-- done!

-- 3
EXPLAIN SELECT * FROM order_lines WHERE stock_item_id = 9;
-- Bitmap Index Scan on ord_lines_si_id_idx  (cost=0.00..11.84 rows=1006 width=0)

-- 4
-- het verschil is dat de zelfde query na het maken van een index een heel stuk sneller loopt,
-- dit komt doordat de index tabel een lijst heeft met alle stock_item_id's van order lines tabel 
-- en de bijhorende plaats in de orginele tabel. door dit te sorten en dan alleen te zoeken in 
-- deze tabel weet hij op welke index's hij de juiste info kan ophalen en kan hij de rest negeren 



-- S7.2.
--
-- 1. Maak de volgende twee query’s:
-- 	  A. Toon uit de order tabel de order met order_id = 73590
-- 	  B. Toon uit de order tabel de order met customer_id = 1028
-- 2. Analyseer met EXPLAIN hoe de query’s uitgevoerd worden en kopieer het explain plan onderaan de opdracht
-- 3. Verklaar de verschillen en schrijf deze op
-- 4. Voeg een index toe, waarmee query B versneld kan worden
-- 5. Analyseer met EXPLAIN en kopieer het explain plan onder de opdracht
-- 6. Verklaar de verschillen en schrijf hieronder op

-- 1
select * from orders where order_id = 73590;
select * from orders where customer_id = 1028;

-- 2 
EXPLAIN select * from orders where order_id = 73590;
-- Index Scan using pk_sales_orders on orders  (cost=0.29..8.31 rows=1 width=155). 
-- de query wordt dus uitgevoerd door op basis van indexen de tabel te scannen en er staat welke index er wordt gebruikt namelijk: pk_sales_orders.
EXPLAIN select * from orders where customer_id = 1028;
-- Seq Scan on orders  (cost=0.00..1819.94 rows=106 width=155).
-- de query wordt uitgevoerd door in de tabel "Orders" elke rij langs te gaan.

-- 3 
-- het verschilt komt omdat "pk_sales_orders" wel een pk is en customer_id niet. en dus kan er voor de ene wel op gescant worden en bij de curomer_id moet die echt alles doorzoeken

-- 4
create index ind_customer_id ON orders (customer_id);
EXPLAIN select * from orders where customer_id = 1028;

-- 5
-- Bitmap Index Scan on ind_customer_id  (cost=0.00..5.09 rows=106 width=0)

-- 6
-- nu sql de index kan gebruiken om te scannen hoeft die lang niet alles langs te gaan, hij pakt gewoon de index en dan waar dat staat in de orginele order tabel
-- en pakt dan de informatie die daar staat. dit is natuurlijk een stuk sneller dan alles lijn voor lijn langs gaan



-- S7.3.A
--
-- Het blijkt dat customers regelmatig klagen over trage bezorging van hun bestelling.
-- Het idee is dat verkopers misschien te lang wachten met het invoeren van de bestelling in het systeem.
-- Daar willen we meer inzicht in krijgen.

-- We willen alle orders (order_id, order_date, salesperson_person_id (als verkoper),
--    het verschil tussen expected_delivery_date en order_date (als levertijd),  
--    en de bestelde hoeveelheid van een product zien (quantity uit order_lines).
-- Dit willen we alleen zien voor een bestelde hoeveelheid van een product > 250
--   (we zijn nl. als eerste geïnteresseerd in grote aantallen want daar lijkt het vaker mis te gaan)
-- En verder willen we ons focussen op verkopers wiens bestellingen er gemiddeld langer over doen.
-- De meeste bestellingen kunnen binnen een dag bezorgd worden, sommige binnen 2-3 dagen.
-- Het hele bestelproces is er op gericht dat de gemiddelde bestelling binnen 1.45 dagen kan worden bezorgd.
-- We willen in onze query dan ook alleen de verkopers zien wiens gemiddelde levertijd 
--  (expected_delivery_date - order_date) over al zijn/haar bestellingen groter is dan 1.45 dagen.
-- Maak om dit te bereiken een subquery in je WHERE clause.
-- Sorteer het resultaat van de hele geheel op levertijd (desc) en verkoper.
-- 1. Maak hieronder deze query (als je het goed doet zouden er 377 rijen uit moeten komen, en het kan best even duren...)
select o.order_id, 
o.order_date, 
o.salesperson_person_id AS verkoper, 
(o.expected_delivery_date - o.order_date) AS levertijd, 
ol.stock_item_id, 
ol.quantity 
from orders o JOIN order_lines ol 
ON o.order_id = ol.order_id
WHERE ol.quantity > 250
AND o.salesperson_person_id IN 
(SELECT salesperson_person_id FROM orders
    GROUP BY salesperson_person_id
	HAVING AVG((expected_delivery_date - order_date)) > 1.45
) ORDER BY levertijd DESC, o.salesperson_person_id;

-- S7.3.B
--
-- 1. Vraag het EXPLAIN plan op van je query (kopieer hier, onder de opdracht)
-- 2. Kijk of je met 1 of meer indexen de query zou kunnen versnellen
-- 3. Maak de index(en) aan en run nogmaals het EXPLAIN plan (kopieer weer onder de opdracht) 
-- 4. Wat voor verschillen zie je? Verklaar hieronder.

-- 1
-- "Gather Merge  (cost=9824.52..9855.09 rows=262 width=24)"
-- "  Workers Planned: 2"
-- "  ->  Sort  (cost=8824.50..8824.83 rows=131 width=24)"
-- "        Sort Key: ((o.expected_delivery_date - o.order_date)) DESC, o.salesperson_person_id"
-- "        ->  Hash Join  (cost=2188.42..8819.89 rows=131 width=24)"
-- "              Hash Cond: (o.salesperson_person_id = orders.salesperson_person_id)"
-- "              ->  Nested Loop  (cost=0.29..6629.80 rows=437 width=24)"
-- "                    ->  Parallel Seq Scan on order_lines ol  (cost=0.00..5051.27 rows=437 width=12)"
-- "                          Filter: (quantity > 250)"
-- "                    ->  Index Scan using pk_sales_orders on orders o  (cost=0.29..3.61 rows=1 width=16)"
-- "                          Index Cond: (order_id = ol.order_id)"
-- "              ->  Hash  (cost=2188.09..2188.09 rows=3 width=4)"
-- "                    ->  HashAggregate  (cost=2187.91..2188.06 rows=3 width=4)"
-- "                          Group Key: orders.salesperson_person_id"
-- "                          Filter: (avg((orders.expected_delivery_date - orders.order_date)) > 1.45)"
-- "                          ->  Seq Scan on orders  (cost=0.00..1635.95 rows=73595 width=12)"

-- 2 done

-- 3
CREATE INDEX idx_orderlist_quant on order_lines (quantity);

CREATE INDEX idx_orders_date on orders (order_date);
CREATE INDEX idx_orders_expctdate on orders (expected_delivery_date);

-- "Sort  (cost=6657.69..6658.47 rows=315 width=24)"
-- "  Sort Key: ((o.expected_delivery_date - o.order_date)) DESC, o.salesperson_person_id"
-- "  ->  Hash Join  (cost=4564.08..6644.62 rows=315 width=24)"
-- "        Hash Cond: (o.order_id = ol.order_id)"
-- "        ->  Hash Join  (cost=2188.13..4099.15 rows=22078 width=16)"
-- "              Hash Cond: (o.salesperson_person_id = orders.salesperson_person_id)"
-- "              ->  Seq Scan on orders o  (cost=0.00..1635.95 rows=73595 width=16)"
-- "              ->  Hash  (cost=2188.09..2188.09 rows=3 width=4)"
-- "                    ->  HashAggregate  (cost=2187.91..2188.06 rows=3 width=4)"
-- "                          Group Key: orders.salesperson_person_id"
-- "                          Filter: (avg((orders.expected_delivery_date - orders.order_date)) > 1.45)"
-- "                          ->  Seq Scan on orders  (cost=0.00..1635.95 rows=73595 width=12)"
-- "        ->  Hash  (cost=2362.83..2362.83 rows=1049 width=12)"
-- "              ->  Bitmap Heap Scan on order_lines ol  (cost=12.42..2362.83 rows=1049 width=12)"
-- "                    Recheck Cond: (quantity > 250)"
-- "                    ->  Bitmap Index Scan on idx_orderlist_quant  (cost=0.00..12.16 rows=1049 width=0)"
-- "                          Index Cond: (quantity > 250)"

-- 4
-- het grooste verschil wat ik zie is dat je nu op sommige plekken ziet dat sql inplaats van zoeken rij bij rij (sequence scannen)
-- er nu vooral wordt gezocht met Bitmap (de index die ik had aangemaakt). verder zie ik ook dat me laaste 2 indexen niet echt wat doen aangezien ze niet worden gebruikt...

-- S7.3.C
--
-- Zou je de query ook heel anders kunnen schrijven om hem te versnellen?
-- subquerys zijn zelden echt handig om te gebruiken. wil je meer preformance zou ik als eerste daar naar kijken, je kan het bijvoorbeelt vervangen met joins
