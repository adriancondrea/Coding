CREATE TABLE Sponsor
    (SponsorID INT PRIMARY KEY IDENTITY(1,1),
    SponsorName VARCHAR(50) NOT NULL,
    SponsorBudget money)


CREATE TABLE Team
    (TeamID INT PRIMARY KEY IDENTITY(1,1),
    TeamName VARCHAR(50) NOT NULL,
    TeamNation VARCHAR(50),
    FoundingYear INT)


CREATE TABLE Sponsor_Team
    (SponsorID INT FOREIGN KEY REFERENCES Sponsor(SponsorID),
    TeamID INT FOREIGN KEY REFERENCES Team(TeamID),
    SponsorshipValue MONEY NOT NULL,
    PRIMARY KEY (SponsorID, TeamID))


CREATE TABLE Driver
    (DriverID INT PRIMARY KEY,
    DriverName VARCHAR(50),
    DateOfBirth DATE,
    Nationality VARCHAR(50),
    TeamID INT NOT NULL FOREIGN KEY REFERENCES Team(TeamID))


CREATE TABLE Supplier
    (SupplierID INT PRIMARY KEY IDENTITY(1,1),
    SupplierName VARCHAR(50) NOT NULL,
    SuppliesSince INT)


CREATE TABLE Tires
    (TireID INT PRIMARY KEY IDENTITY(1,1),
    Compound VARCHAR(50),   --material tires are made of
    Durability VARCHAR(50)) --soft, medium, hard


CREATE TABLE Supplier_Tires
    (SupplierID INT FOREIGN KEY REFERENCES Supplier(SupplierID),
    TireID INT FOREIGN KEY REFERENCES Tires(TireID),
    PRIMARY KEY (SupplierID, TireID))


CREATE TABLE Engine
    (EngineID INT PRIMARY KEY IDENTITY(1,1),
    Manufacturer VARCHAR(50) NOT NULL,
    Power INT,
    Capacity REAL)


CREATE TABLE Car
    (
    CarNumber INT PRIMARY KEY,
    DriverID INT FOREIGN KEY REFERENCES Driver(DriverID),
    constraint unique_driverID UNIQUE(DriverID),
    Cost money,
    TireID INT NOT NULL FOREIGN KEY REFERENCES Tires(TireID),
    EngineID INT NOT NULL FOREIGN KEY REFERENCES Engine(EngineID))


CREATE TABLE Circuit
    (CircuitID INT PRIMARY KEY IDENTITY(1,1),
    Type VARCHAR(50),   --street, road, race circuit
    Location VARCHAR(50) NOT NULL,
    Length REAL)


CREATE TABLE Event
    (EventID INT PRIMARY KEY IDENTITY(1,1),
    EventDate DATE,
    CircuitID INT NOT NULL FOREIGN KEY REFERENCES Circuit(CircuitID))

CREATE TABLE Team_Event
    (TeamID INT FOREIGN KEY REFERENCES Team(TeamID),
    EventID INT FOREIGN KEY REFERENCES Event(EventID))