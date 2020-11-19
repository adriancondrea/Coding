--naming convention: affectedTable_operation_*affected_columns
--* - optional

--a. modify the type of a column;
--say we want to change the cost for a car from dollars to millions of dollars
CREATE PROCEDURE Car_ChangeCostToMillions(@duringUndo BIT = 0)
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
        if @duringUndo != 1
            BEGIN
            INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
                ('Car_ChangeCostToMillions', 'Car_UndoChangeCostToMillions')
            UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
            END
        END
GO;
EXEC Car_ChangeCostToMillions

--the reverse of the procedure
CREATE PROCEDURE Car_UndoChangeCostToMillions(@duringUndo BIT = 0)
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
        if @duringUndo != 1
            BEGIN
            INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
                ('Car_UndoChangeCostToMillions', 'Car_ChangeCostToMillions')
            UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
            END
        END
GO;
EXEC Car_UndoChangeCostToMillions





--b. add / remove a column;
CREATE PROCEDURE Team_AddColumnPoints(@duringUndo BIT = 0)
AS
    IF COL_LENGTH('Team', 'Points') IS NULL
BEGIN
    ALTER TABLE Team
    ADD Points smallint
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Team_AddColumnPoints', 'Team_RemoveColumnPoints')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
END
GO;
EXEC Team_AddColumnPoints

CREATE PROCEDURE Team_RemoveColumnPoints(@duringUndo BIT = 0)
    AS
    IF COL_LENGTH('Team', 'Points') IS NOT NULL
    BEGIN
    ALTER TABLE Team
    DROP COLUMN Points
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Team_RemoveColumnPoints', 'Team_AddColumnPoints')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    END
GO;
EXEC Team_RemoveColumnPoints





--c. add / remove a DEFAULT constraint;
CREATE PROCEDURE Team_DefaultColumnPoints(@duringUndo BIT = 0)
    AS
    ALTER TABLE Team
    ADD CONSTRAINT df_Points DEFAULT 0 FOR Points
    IF @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Team_DefaultColumnPoints', 'Team_RemoveDefaultColumnPoints')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
GO;
EXEC Team_DefaultColumnPoints


CREATE PROCEDURE Team_RemoveDefaultColumnPoints(@duringUndo BIT = 0)
    AS
    ALTER TABLE Team
    DROP CONSTRAINT IF EXISTS df_Points
    IF @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Team_RemoveDefaultColumnPoints', 'Team_DefaultColumnPoints')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
GO;
EXEC Team_RemoveDefaultColumnPoints





--d. add / remove a primary key;
CREATE PROCEDURE Car_Remove_PrimaryKey(@duringUndo BIT = 0)
    AS
    DECLARE @constraint VARCHAR(100)
    set @constraint = (SELECT name
                    FROM sys.key_constraints
                    WHERE type = 'PK' AND OBJECT_NAME(parent_object_id) = N'Car')
    EXEC('ALTER TABLE Car DROP CONSTRAINT ' + @constraint)
    IF @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Car_Remove_PrimaryKey', 'Car_Add_PrimaryKey')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Car_Remove_PrimaryKey

CREATE PROCEDURE Car_Add_PrimaryKey(@duringUndo BIT = 0)
    AS
    ALTER TABLE Car
    ADD CONSTRAINT PK_CarNumber PRIMARY KEY (CarNumber)
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Car_Add_PrimaryKey', 'Car_Remove_PrimaryKey')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Car_Add_PrimaryKey





--e. add / remove a candidate key;
CREATE PROCEDURE Car_Remove_CandidateKey_DriverID(@duringUndo BIT = 0)
    AS
    ALTER TABLE Car
    DROP CONSTRAINT unique_driverID
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Car_Remove_CandidateKey_DriverID', 'Car_Add_CandidateKey_DriverID')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Car_Remove_CandidateKey_DriverID

CREATE PROCEDURE Car_Add_CandidateKey_DriverID(@duringUndo BIT = 0)
    AS
    ALTER TABLE Car
    ADD CONSTRAINT unique_driverID UNIQUE(DriverID)
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Car_Add_CandidateKey_DriverID', 'Car_Remove_CandidateKey_DriverID')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Car_Add_CandidateKey_DriverID





--f. add / remove a foreign key;
CREATE PROCEDURE Driver_Remove_ForeignKey_TeamID(@duringUndo BIT = 0)
    AS
    ALTER TABLE Driver
    DROP FK_Team
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Driver_Remove_ForeignKey_TeamID', 'Driver_Add_ForeignKey_TeamID')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Driver_Remove_ForeignKey_TeamID

CREATE PROCEDURE Driver_Add_ForeignKey_TeamID(@duringUndo BIT = 0)
    AS
    ALTER TABLE Driver
    ADD CONSTRAINT FK_Team
    FOREIGN KEY (TeamID) REFERENCES Team(TeamID)
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Driver_Add_ForeignKey_TeamID', 'Driver_Remove_ForeignKEy_TeamID')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Driver_Add_ForeignKey_TeamID





--g. create / drop a table.
CREATE PROCEDURE Team_Event_DropTable(@duringUndo BIT = 0)
    AS
    DROP TABLE Team_Event
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Team_Event_DropTable', 'Team_Event_CreateTable')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Team_Event_DropTable

CREATE PROCEDURE Team_Event_CreateTable(@duringUndo BIT = 0)
    AS
    CREATE TABLE Team_Event
    (TeamID INT FOREIGN KEY REFERENCES Team(TeamID),
    EventID INT FOREIGN KEY REFERENCES Event(EventID))
    if @duringUndo != 1
        BEGIN
        INSERT Executed_Procedures(ExecutedProcedure, UndoProcedure) VALUES
        ('Team_Event_CreateTable', 'Team_Event_DropTable')
        UPDATE CurrentVersion
            SET CurrentVersion = CurrentVersion + 1
        END
    GO;
EXEC Team_Event_CreateTable





--Create a new table that holds the current version of the database schema. Simplifying assumption:
-- the version is an integer number.
CREATE TABLE CurrentVersion
(
    CurrentVersion INT DEFAULT 0
)

INSERT CurrentVersion(CurrentVersion)
VALUES(0)

CREATE TABLE Executed_Procedures(
    ExecutedProcedure VARCHAR(50),
    UndoProcedure VARCHAR(50),
    VersionNumber INT IDENTITY (1,1) PRIMARY KEY
)

--Write a stored procedure that receives as a parameter a version number and brings the database to that version.
CREATE PROCEDURE GetCurrentVersion(@version INT OUTPUT)
AS
	SELECT @version = CurrentVersion
	FROM CurrentVersion
GO

CREATE PROCEDURE GetDoFunction(@version INT, @ExecutedProcedure VARCHAR(50) OUTPUT)
AS
	SELECT @ExecutedProcedure = PV.ExecutedProcedure
	FROM Executed_Procedures PV
	WHERE PV.VersionNumber = @version
GO

CREATE PROCEDURE GetUndoFunction(@version INT, @UndoProcedure VARCHAR(50) OUTPUT)
AS
	SELECT @UndoProcedure = PV.UndoProcedure
	FROM Executed_Procedures PV
	WHERE PV.VersionNumber = @version
GO

CREATE PROCEDURE UpdateCurrentVersion(@version INT)
AS
	UPDATE CurrentVersion
	SET CurrentVersion = @version
GO

CREATE PROCEDURE BringDatabaseToVersion(@version INT)
AS
	DECLARE @currentVersion int
	EXEC GetCurrentVersion @currentVersion OUTPUT
	DECLARE @ExecutedProcedure VARCHAR(50)
	DECLARE @UndoProcedure VARCHAR(50)

	IF @version > @currentVersion
	BEGIN
		WHILE @version != @currentVersion
		BEGIN
			SET @currentVersion = @currentVersion + 1
			EXEC GetDoFunction @currentVersion, @ExecutedProcedure OUTPUT
			set @ExecutedProcedure = @ExecutedProcedure
			EXEC @ExecutedProcedure @duringUndo = 1
			EXEC UpdateCurrentVersion @currentVersion
		END
	END

	ELSE

	BEGIN
		WHILE @version != @currentVersion
		BEGIN
			EXEC GetUndoFunction @currentVersion, @UndoProcedure OUTPUT
			set @UndoProcedure = @UndoProcedure
			SET @currentVersion = @currentVersion - 1
			EXEC @UndoProcedure @duringUndo = 1
			EXEC UpdateCurrentVersion @currentVersion
		END
	END
GO

EXEC BringDatabaseToVersion 0

--reset Executed_Procedures and current version
CREATE PROCEDURE Reset_Version AS
    DELETE Executed_Procedures
    DBCC CHECKIDENT ( 'Executed_Procedures', RESEED , 0);
    EXEC UpdateCurrentVersion  0
    GO;

EXEC Reset_Version
EXEC BringDatabaseToVersion 2

SELECT *
FROM TEAM
