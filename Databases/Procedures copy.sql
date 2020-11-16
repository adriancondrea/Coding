--naming convention: affectedTable_operation_*affected_columns
--* - optional

--a. modify the type of a column;
--say we want to change the cost for a car from dollars to millions of dollars
CREATE PROCEDURE Car_ChangeCostToMillions
AS
    DECLARE @type VARCHAR(50)
    set @type = (SELECT DATA_TYPE
                 FROM INFORMATION_SCHEMA.COLUMNS
                 WHERE
                    TABLE_NAME = 'Car' AND
                    COLUMN_NAME = 'Cost')
    if @type = 'money'
        BEGIN
        ALTER TABLE Car
        ALTER COLUMN Cost float
        UPDATE Car
        SET Cost = Cost / 1000000
        END
GO;
EXEC Car_ChangeCostToMillions

--the reverse of the procedure
CREATE PROCEDURE Car_UndoChangeCostToMillions
AS
    DECLARE @type VARCHAR(50)
    set @type = (SELECT DATA_TYPE
                 FROM INFORMATION_SCHEMA.COLUMNS
                 WHERE
                    TABLE_NAME = 'Car' AND
                    COLUMN_NAME = 'Cost')
    if @type = 'float'
        BEGIN
        ALTER TABLE Car
        ALTER COLUMN Cost money
        UPDATE Car
        SET Cost = Cost * 1000000
        END
GO;
EXEC Car_UndoChangeCostToMillions





--b. add / remove a column;
CREATE PROCEDURE Team_AddColumnPoints
AS
    IF COL_LENGTH('Team', 'Points') IS NULL
BEGIN
    ALTER TABLE Team
    ADD Points smallint
END
GO;
EXEC Team_AddColumnPoints

CREATE PROCEDURE Team_RemoveColumnPoints
    AS
    IF COL_LENGTH('Team', 'Points') IS NOT NULL
    BEGIN
    ALTER TABLE Team
    DROP COLUMN Points
    END
GO;
EXEC Team_RemoveColumnPoints





--c. add / remove a DEFAULT constraint;
CREATE PROCEDURE Team_DefaultColumnPoints
    AS
    ALTER TABLE Team
    ADD CONSTRAINT df_Points DEFAULT 0 FOR Points
GO;
EXEC Team_DefaultColumnPoints


CREATE PROCEDURE Team_RemoveDefaultColumnPoints
    AS
    ALTER TABLE Team
    DROP CONSTRAINT IF EXISTS df_Points
GO;
EXEC Team_RemoveDefaultColumnPoints





--d. add / remove a primary key;
CREATE PROCEDURE Car_Remove_PrimaryKey
    AS
    DECLARE @constraint VARCHAR(100)
    set @constraint = (SELECT name
                    FROM sys.key_constraints
                    WHERE type = 'PK' AND OBJECT_NAME(parent_object_id) = N'Car')
    EXEC('ALTER TABLE Car DROP CONSTRAINT ' + @constraint)
    GO;
EXEC Car_Remove_PrimaryKey

CREATE PROCEDURE Car_Add_PrimaryKey
    AS
    ALTER TABLE Car
    ADD CONSTRAINT PK_CarNumber PRIMARY KEY (CarNumber)
    GO;
EXEC Car_Add_PrimaryKey





--e. add / remove a candidate key;
CREATE PROCEDURE Car_Remove_CandidateKey_DriverID
    AS
    ALTER TABLE Car
    DROP CONSTRAINT unique_driverID
    GO;
EXEC Car_Remove_CandidateKey_DriverID

CREATE PROCEDURE Car_Add_CandidateKey_DriverID
    AS
    ALTER TABLE Car
    ADD CONSTRAINT unique_driverID UNIQUE(DriverID)
    GO;
EXEC Car_Add_CandidateKey_DriverID





--f. add / remove a foreign key;
CREATE PROCEDURE Driver_Remove_ForeignKey_TeamID
    AS
    ALTER TABLE Driver
    DROP FK_Team
    GO;
EXEC Driver_Remove_ForeignKey_TeamID

CREATE PROCEDURE Driver_Add_ForeignKey_TeamID
    AS
    ALTER TABLE Driver
    ADD CONSTRAINT FK_Team
    FOREIGN KEY (TeamID) REFERENCES Team(TeamID)
    GO;
EXEC Driver_Add_ForeignKey_TeamID





--g. create / drop a table.
CREATE PROCEDURE Team_Event_DropTable
    AS
    DROP TABLE Team_Event
    GO;
EXEC Team_Event_DropTable

CREATE PROCEDURE Team_Event_CreateTable
    AS
    CREATE TABLE Team_Event
    (TeamID INT FOREIGN KEY REFERENCES Team(TeamID),
    EventID INT FOREIGN KEY REFERENCES Event(EventID))
    GO;
EXEC Team_Event_CreateTable




CREATE PROCEDURE CreateCurrentVersionTable
AS
CREATE TABLE CurrentVersion
    (Version INT PRIMARY KEY)

INSERT CurrentVersion(Version)
VALUES
    (1)
GO;

EXEC CreateCurrentVersionTable
