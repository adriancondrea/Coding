USE Formula1DB
GO

DBCC CHECKIDENT ('Team', RESEED, 0);
DBCC CHECKIDENT ('Circuit', RESEED, 0);
DBCC CHECKIDENT ('Event', RESEED, 0);
DBCC CHECKIDENT ('Engine', RESEED, 0);
DBCC CHECKIDENT ( 'Tires', RESEED , 0);


INSERT Team(TeamName, TeamNation, FoundingYear)
VALUES
    ('Scuderia Ferrari', 'Italy', 1929),
    ('Mercedes-AMG Petronas', 'Germany', 1954),
    ('Red Bull Racing Honda', 'Switzerland', null),
    ('BWT Racing Point', 'United Kingdom', 2019),
    ('Alfa Romeo', 'Switzerland', 1950),
    ('AlphaTauri', 'Italy', 2020),
    ('Haas', 'United States', 2016),
    ('McLaren', 'United Kingdom', 1966),
    ('Renault', 'France', 1977),
    ('Williams', 'United Kingdom', 1978)


INSERT Driver(DriverID, DriverName, DateOfBirth, Nationality, TeamID)
VALUES
    (1, 'Lewis Hamilton', '1985-01-12', 'British', 2),
    (2, 'Valtteri Bottas', '1989-08-28', 'Swedish', 2),
    (3, 'Charles Leclerc', '1997-10-16', null, 1),
    (4, 'Sebastian Vettel', '1987-07-03', 'German', 1),
    (5, 'Alexander Albon', '1996-03-23', 'British', 3),
    (6, 'Max Verstappen', '1997-09-30', 'Dutch', 3),
    (7, 'Nico Hulkenberg', '1987-08-19', 'German', 4),
    (8, 'Sergio Perez', '1990-01-26', 'Mexican', 4),
    (9, 'Carlos Sainz', '1994-09-1', 'Spanish', 8),
    (10, 'Lando Norris', '1999-11-13', 'British', 8),
    (11, 'Esteban Ocon', '1996-09-17', 'French', 9),
    (12, 'Daniel Ricciardo', '1989-07-01', 'Australian', 9),
    (13, 'Pierre Gasly', '1996-02-07', 'French', 6),
    (14, 'Daniil Kvyat', '1994-04-26', 'Russian', 6),
    (15, 'Kimi Raikkonen', '1979-10-17', 'Finnish', 5),
    (16, 'Antonio Giovinazzi', '1993-12-14', 'Italian', 5),
    (17, 'Kevin Magnussen', '1992-10-05', 'Danish', 7),
    (18, 'Romain Grosjean', '1986-04-17', 'French', 7),
    (19, 'Nicholas Latifi', '1995-06-29', 'Canadian', 10),
    (20, 'George Russell', '1998-02-15', 'British', 10)
    --(21, 'Carlos Sainz', '1994-09-1', 'Spanish', 15) --!!Invalid team, violates foreign key constraint, team with teamID 15 doesn't exist


INSERT Engine(Manufacturer, Power, Capacity)
VALUES
    ('Ferrari', 980, 1.6),
    ('Honda', 960, 1.5),
    ('Renault', 976, 1.58),
    ('Mercedes', 1000, 1.6)


INSERT Tires(Compound, DrivingConditions, Grip, Durability, Color)
VALUES
    ('Hard', 'Dry', 5, 1, 'White'),
    ('Hard', 'Dry', 4, 2, 'White'),
    ('Hard', 'Dry', 3, 3, 'White'),
    ('Medium', 'Dry', 4, 2, 'Yellow'),
    ('Medium', 'Dry', 3, 3, 'Yellow'),
    ('Soft', 'Dry', 3, 3, 'Red'),
    ('Soft', 'Dry', 2, 4, 'Red'),
    ('Soft', 'Dry', 1, 5, 'Red'),
    ('Intermediate', 'Wet - light standing water', null, null, 'Green'),
    ('Wet', 'Wet - heavy standing water', null, null, 'Blue')


INSERT Supplier(SupplierName, SuppliesSince)
VALUES
    ('Pirelli', 1950),
    ('Bridgestone', 1976),
    ('Continental', 1954),
    ('Dunlop', 1951),
    ('Goodyear', 1964),
    ('Michelin', 1978)


INSERT Car(CarNumber, DriverID, Cost, TireID, EngineID, CarName)
VALUES
    (1, 1, 12000000, 8, 4, 'Mercedes-AMG F1 W11 EQ Performance'),
    (2, 2, 12000000, 7, 4, 'Mercedes-AMG F1 W11 EQ Performance'),
    (3, 3, 11549000, 5, 1, 'Ferrari SF1000'),
    (4, 4, 11580000, 1, 1, 'Ferrari SF1000'),
    (5, 5, 11800000, 7, 2, 'Red Bull RB16'),
    (6, 6, 11850000, 7, 2, 'Red Bull RB16'),
    (7, 7, 10000000, 6, 4, 'Racing Point RP20'),
    (8, 8, 11000000, 4, 4, 'Racing Point RP20'),
    (9, 9, 9000000, 2, 3, 'McLaren MCL35'),
    (10, 10, 9200000, 1, 3, 'McLaren MCL35'),
    (11, 11, 11900000, 3, 3, 'Renault R.S.20'),
    (12, 12, 11950000, 4, 3, 'Renault R.S.20'),
    (13, 13, 8500000, 5, 2, 'AlphaTauri AT01'),
    (14, 14, 8000000, 5, 2, 'AlphaTauri AT01'),
    (15, 15, 9000000, 3, 1, 'Alfa Romeo C39'),
    (16, 16, 8500000, 7, 1, 'Alfa Romeo C39'),
    (17, 17, 7000000, 5, 1, 'Haas VF-20'),
    (18, 18, 6500000, 7, 1, 'Haas VF-20'),
    (19, 19, 5000000, 1, 4, 'Williams FW43'),
    (20, 20, 5000000, 2, 4, 'Williams FW43')


INSERT Circuit(Location, Length, Type)
VALUES
    ('Algarve International Circuit Portugal', 4.653, 'Race Circuit'),
    ('AVUS Germany', 8.300, 'Road Circuit'),
    ('Autodromo Internazionale Enzo e Dino Ferrari Italy', 4.909, 'Race Circuit'),
    ('Adelaide Street Circuit Australia', 3.780, 'Street Circuit'),
    ('Autodromo Nazionale Monza, Italy', 5.793, 'Race Circuit'),
    ('Bahrain International Circuit', 5.412, 'Race Circuit'),
    ('Marina Bay Street Circuit Singapore', 5.063, 'Street Circuit'),
    ('Circuit de Barcelona-Catalunya', 4.655, 'Race Circuit'),
    ('Circuit Bremgarten Switzerland', 7.208, 'Road Circuit')


INSERT Event(EventDate, CircuitID)
VALUES
    ('2020-01-04', 1),
    ('2020-01-11', 2),
    ('2020-01-18', 4),
    ('2020-01-25', 3),
    ('2020-02-01', 9),
    ('2020-02-08', 6),
    ('2020-02-15', 5),
    ('2020-02-22', 7),
    ('2020-03-07', 8),
    ('2020-03-14', 1),
    ('2020-04-18', 2),
    ('2020-04-25', 4),
    ('2020-05-09', 3),
    ('2020-06-20', 9),
    ('2020-07-25', 6),
    ('2020-08-22', 5),
    ('2020-09-26', 7),
    ('2020-10-31', 8),
    ('2020-11-21', 4),
    ('2020-12-5', 7)
    --('2021-09-12', 12) - !!!invalid circuit id (non existent) - violates foreign key constraint


INSERT Sponsor(SponsorName, SponsorBudget)
VALUES
    ('Nescafe Xpress', 0.08),
    ('Hugo Boss', 2.35),
    ('Vodafone' , 57.19),
    ('Mobil 1', 26.51),
    ('Pirelli', 200)


INSERT Sponsor_Team(SponsorID, TeamID, SponsorshipValue)
VALUES
    (1, 8, 0.08),
    (2, 8, 2.35),
    (3, 8, 57.19),
    (4, 8, 26.51),
    (5, 1, 10),
    (5, 2, 10),
    (5, 3, 10),
    (5, 4, 10),
    (5, 5, 10),
    (5, 6, 10),
    (5, 7, 10),
    (5, 8, 10),
    (5, 9, 10),
    (5,10, 10)


INSERT Supplier_Tires(SupplierID, TireID)
VALUES
    (1,1),
    (1,2),
    (1,3),
    (1,4),
    (1,5),
    (1,6),
    (1,7),
    (1,8),
    (1,9),
    (1,10),
    (2,1),
    (2,4),
    (2,7),
    (2,10),
    (3,1),
    (3,2),
    (3,3),
    (4,4),
    (4,5),
    (4,6),
    (4,7),
    (4,8),
    (5,6),
    (5,7),
    (5,8),
    (5,9),
    (5,10),
    (6,1),
    (6,8),
    (6,9)


INSERT Team_Event(TeamID, EventID)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (1, 8),
    (1, 9),
    (1, 10),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 7)
