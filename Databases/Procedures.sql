CREATE PROCEDURE CreateTeamEventTable
AS
CREATE TABLE Team_Event
    (TeamID INT FOREIGN KEY REFERENCES Team(TeamID),
    EventID INT FOREIGN KEY REFERENCES Event(EventID))

GO;

EXEC CreateTeamEventTable

CREATE PROCEDURE UndoCreateTeamEventTable
AS
DROP TABLE Team_Event
GO;
EXEC UndoCreateTeamEventTable

CREATE PROCEDURE CreateCurrentVersionTable
AS
CREATE TABLE CurrentVersion
    (Version INT PRIMARY KEY)

INSERT CurrentVersion(Version)
VALUES
    (1)
GO;

EXEC CreateCurrentVersionTable


CREATE PROCEDURE Team_AddColumnPoints
AS
    ALTER TABLE Team
    ADD Points smallint;
GO

EXEC Team_AddColumnPoints


CREATE PROCEDURE Team_RemoveColumnPoints
AS
    ALTER TABLE Team
    Drop COLUMN Points
GO

EXEC Team_RemoveColumnPoints
