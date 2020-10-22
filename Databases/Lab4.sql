SELECT *
FROM Driver D INNER JOIN Team T ON D.TeamID = T.TeamID

SELECT *
FROM Circuit C LEFT JOIN Event E ON C.CircuitID = E.CircuitID

SELECT *
FROM Event E RIGHT JOIN Circuit C ON C.CircuitID = E.CircuitID

SELECT *
FROM Car C RIGHT JOIN Driver D ON D.DriverID = C.DriverID

SELECT *
FROM Event E FULl JOIN Circuit C on C.CircuitID = E.CircuitID

SELECT *
FROM Car C JOIN Engine E on C.EngineID = E.EngineID
JOIN Tires T on C.TireID = T.TireID

SELECT *
FROM Sponsor_Team ST JOIN Team_Event TE on ST.TeamID = TE.TeamID
JOIN Event E on E.EventID = TE.EventID
JOIN Circuit C on C.CircuitID = E.CircuitID

SELECT T.TeamName, ST.SponsorshipValue
FROM Team T Join Sponsor_Team ST on T.TeamID = ST.TeamID

--select all sponsors of Ferrari
SELECT S.SponsorName
FROM Sponsor S
WHERE EXISTS(SELECT *
             FROM Sponsor_Team ST
             WHERE ST.TeamID = 1 and ST.SponsorID = S.SponsorID)