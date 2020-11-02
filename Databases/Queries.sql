--UPDATE

--1. incrase the cost of cars with engine from ferrari (engineID = 1) and tireID = 7 or tireID = 1
-- with 10 000$
UPDATE Car
SET Cost = Cost + 10000
WHERE EngineID = 1 AND (TireID = 1 or TireID = 7)


--2. for drivers with null nationality, add 'unknown' to their nationality
UPDATE Driver
SET Nationality = 'unknown'
WHERE Nationality is NULL


--3. Cars with cost >= 10 000 000 $ get a 10% discount!
UPDATE Car
SET Cost = Cost * 0.9
WHERE Cost >= 10000000


--4. Tires used in wet driving conditions (both light standing water and heavy standing water)
--get their grip set to 1
UPDATE Tires
SET Grip = 1
WHERE DrivingConditions LIKE 'Wet%'





--DELETE
--1. Delete Sponsors that have budgets between 2-5 (budget is too small to be a major sponsor,
-- but too big to be a small sponsor)
DELETE Sponsor
WHERE SponsorBudget BETWEEN 2 AND 5;


--2. Massive team changes at Teams with TeamID 1 and 2. They fire their drivers
DELETE Driver
WHERE TeamID IN (1, 2);


--3. delete all events with no date
DELETE
from Event
WHERE EventDate is NULL




--a)union queries

--show the names of the drivers that are british or belong to a british team or an US team
SELECT D.DriverName
FROM Driver D
WHERE D.Nationality = 'British'
UNION
SELECT D1.DriverName
FROM Team T, Driver D1
WHERE (T.TeamNation = 'United Kingdom' or T.TeamNation = 'United States') AND D1.TeamID = T.TeamID


--show the manufacturer and engineID for the engines that have a power >= 980hp or are used
--on a car from team Red bull
SELECT E.Manufacturer, E.EngineID
FROM Engine E
WHERE E.Power >= 980
UNION
SELECT E.Manufacturer, E.EngineID
FROM Engine E, Car C
WHERE C.CarName LIKE '%Red_Bull%' AND C.EngineID = E.EngineID





--b)intersection queries

--find the names of the drivers that drive either for Ferrari or Mercedes, and drive a car
--worth more that 11.5 million $
SELECT D.DriverName
FROM Driver D, Team T
WHERE D.TeamID = T.TeamID AND T.TeamName IN('Scuderia Ferrari','Mercedes-AMG Petronas')
INTERSECT
SELECT D2.DriverName
FROM Driver D2, Car C
WHERE C.DriverID = D2.DriverID AND C.Cost >= 11500000


--find suppliers that supply both soft and hard tires
SELECT S.SupplierName
FROM Supplier S, Supplier_Tires ST, Tires T
WHERE S.SupplierID = ST.SupplierID AND T.Compound = 'Soft' and T.TireID = ST.TireID
INTERSECT
SELECT S1.SupplierName
From Supplier S1, Supplier_Tires ST1, Tires T1
WHERE S1.SupplierID = ST1.SupplierID AND T1.Compound = 'Hard' AND T1.TireID = ST1.TireID


--c)difference queries
--find drivers that are not british or german nor belong to a british or german team
SELECT D.DriverName
FROM Driver D
WHERE D.Nationality NOT IN ('British', 'German')
INTERSECT
SELECT D1.DriverName
FROM Driver D1, Team T1
WHERE D1.TeamID = T1.TeamID AND T1.TeamNation NOT IN ('United Kingdom', 'Germany')


--find sponsors that sponsor both McLaren and Mercedes
SELECT S.SponsorName
FROM Sponsor S, Team T, Sponsor_Team ST
WHERE ST.TeamID = T.TeamID AND ST.SponsorID = S.SponsorID and T.TeamName LIKE  '%McLaren%'
INTERSECT
SELECT DISTINCT S1.SponsorName
FROM Sponsor S1, Team T1, Sponsor_Team ST1
WHERE ST1.TeamID = T1.TeamID AND ST1.SponsorID = S1.SponsorID and T1.TeamName LIKE  '%Mercedes%'





--d)join queries

--inner join between drivers and their team
--inner join shows only records that have matching values in both tables
SELECT D.DriverID, D.DriverName, D.DateOfBirth, D.Nationality, T.TeamID, TeamName, TeamNation, FoundingYear
FROM Driver D
INNER JOIN Team T ON D.TeamID = T.TeamID


--show details about the car, engine and tires. This joins 3 tables
--with full join we can detect fields which are null. for example,
--we can see that tires with id 9 and 10 are never used
SELECT C.CarNumber, C.CarName, C.Cost, E.Manufacturer as EngineManufacturer, E.Power, E.Capacity, T.*
FROM Car C FULL JOIN Engine E on C.EngineID = E.EngineID
FULL JOIN Tires T on C.TireID = T.TireID

--show the team name, sponsor name, location of the event and date of the event.
-- this query joins two many to many relations
--use left join
SELECT T.TeamName, S.SponsorName, C.location, E.eventdate
FROM Sponsor_Team ST   LEFT JOIN Team_Event TE on ST.TeamID = TE.TeamID
LEFT JOIN Event E on E.EventID = TE.EventID
LEFT JOIN Circuit C on C.CircuitID = E.CircuitID
LEFT JOIN Team T on ST.TeamID = T.TeamID
LEFT JOIN Sponsor S on ST.SponsorID = S.SponsorID


--see tire ID and compound that are never used
--use right join
SELECT C.CarName, T.TireID, T.Compound
FROM Car C RIGHT JOIN Tires T on C.TireID = T.TireID





--e)queries with the in operator and a subquery in the were clause
--show the names of the drivers that drive a car that doesn't have an engine manufactured by Mercedes
SELECT D.DriverName
FROM Driver D
WHERE D.DriverID NOT IN (SELECT C.DriverID
                         FROM Car C
                         WHERE C.CarNumber NOT IN(SELECT C1.CarNumber
                                                  FROM Engine E, Car C1
                                                  WHERE C1.EngineID = E.EngineID AND E.Manufacturer NOT LIKE '%Mercedes%'))


--show the tires compound manufactured by Dunlop
SELECT DISTINCT T.Compound
FROM Tires T
WHERE T.TireID IN(SELECT T.TireID
                  FROM Supplier S, Supplier_Tires ST
                  WHERE T.TireID = ST.TireID AND S.SupplierID = ST.SupplierID AND S.SupplierName = 'Dunlop')



--f)queries with the EXISTS operator and a subquery in the WHERE clause

--find all sponsors of McLaren
SELECT S.SponsorName
FROM Sponsor S, Team T1
WHERE T1.TeamName LIKE '%McLaren%' AND EXISTS(
            SELECT *
             FROM Sponsor_Team ST
             WHERE ST.SponsorID = S.SponsorID AND ST.TeamID = T1.TeamID)


--find all suppliers that provide tires with wet compound, ordered descendent
SELECT S.SupplierName
FROM Supplier S, Tires T
WHERE T.Compound LIKE '%Wet%' AND EXISTS(
    SELECT *
    FROM Supplier_Tires ST
    WHERE ST.TireID = T.TireID AND ST.SupplierID = S.SupplierID
    )
ORDER BY S.SupplierName DESC;




--g)queries with a subquery in the FROM clause
--display the average car cost for each team
SELECT TeamName, AverageCarCost
FROM (SELECT T.TeamName, AVG(C.Cost) as AverageCarCost
      FROM Team T, Driver D, Car C
      WHERE T.TeamID = D.TeamID and C.DriverID = D.DriverID
      GROUP BY T.TeamName) as TeamSummary
ORDER BY AverageCarCost


--display the number of suppliers for each tire type
SELECT TireID, SuppliersCount
FROM (SELECT T.TireID, COUNT(ST.SupplierID) as SuppliersCount
      FROM Tires T, Supplier S, Supplier_Tires ST
      WHERE T.TireID = ST.TireID AND S.SupplierID = ST.SupplierID
      GROUP BY T.TireID) as TireSummary
ORDER BY TireID





--h)queries with the GROUP BY clause
--show number of events on a given circuit location for locations that have at least 3 events
SELECT C.Location, COUNT(EventID) as NumberOfEvents
FROM Circuit C, Event E
WHERE C.CircuitID = E.CircuitID
GROUP BY C.Location
HAVING COUNT(EventID) >= 3

--display the teams having an average car cost greater than the overall car cost average
--for all teams
SELECT T.TeamName,
       AVG(C.Cost) AS AverageCarCost
FROM Team T, Car C, Driver D
WHERE T.TeamID = D.TeamID AND C.DriverID = D.DriverID
GROUP BY TeamName
HAVING AVG(C.Cost) > (SELECT AVG(C1.Cost)
                      FROM Car C1)


--display the maximum length of each circuit type
SELECT C.Type, MAX(C.Length) as MaxLength
FROM Circuit C
GROUP BY C.Type


--display the minimum length of each circuit type
SELECT C.Type, MIN(C.Length) as MaxLength
FROM Circuit C
GROUP BY C.Type


--display the teams having a sponsorship value greater than the average sponsorship for all teams
SELECT T.TeamName,
       SUM(ST.SponsorshipValue) AS SponsorshipValue
FROM Team T, Sponsor_Team ST
WHERE T.TeamID = ST.TeamID
GROUP BY TeamName
HAVING AVG(ST.SponsorshipValue) > (SELECT AVG(ST1.SponsorshipValue)
                                   FROM Sponsor_Team ST1)


--i)queries using ANY and ALL to introduce a subquery in the WHERE clause
--find all teams that participate to event 1 or 2
SELECT DISTINCT T.TeamName
FROM Team T
WHERE TeamID = ANY(SELECT TE.TeamID FROM Team_Event TE WHERE EventID in (1, 2))

--rewrite using in
SELECT DISTINCT T.TeamNAme
FROM Team T, Team_Event TE
WHERE T.TeamID = TE.TeamID AND TE.EventID IN (1, 2)


--find all drivers that drive for Ferrari or Mercedes
SELECT D.DriverName
FROM Driver D
WHERE D.TeamID = ANY(SELECT T.TeamID FROM Team T WHERE T.TeamName in ('Scuderia Ferrari', 'Mercedes-AMG Petronas'))

--rewrite using in
SELECT D.DriverName
FROM Driver D, Team T
WHERE D.TeamID = T.TeamID AND  T.TeamName in ('Scuderia Ferrari', 'Mercedes-AMG Petronas')


--find the sponsor whose budget is maximum
SELECT S.SponsorName
FROM Sponsor S
WHERE S.SponsorBudget > ALL(SELECT S2.SponsorBudget FROM Sponsor S2 WHERE S2.SponsorName != S.SponsorName)

--rewrite with aggregation operator

SELECT S.SponsorName
FROM Sponsor S
WHERE S.SponsorBudget = ANY(SELECT MAX(S2.SponsorBudget)
                            FROM Sponsor S2);


--find the sponsor whose budget is minimum
SELECT S.SponsorName
FROM Sponsor S
WHERE S.SponsorBudget < ALL(SELECT S2.SponsorBudget FROM Sponsor S2 WHERE S2.SponsorName != S.SponsorName)

--rewrite with aggregation operator
SELECT S.SponsorName
FROM Sponsor S
WHERE S.SponsorBudget = ANY(SELECT MIN(S2.SponsorBudget)
                            FROM Sponsor S2);


--show top 2 most powerful engines
SELECT TOP 2 E.Manufacturer, E.Power
FROM Engine E
ORDER BY E.Power DESC

--show top 2 biggest sponsors
SELECT TOP 2 S.SponsorName ,S.SponsorBudget
FROM Sponsor S
ORDER BY S.SponsorBudget DESC
