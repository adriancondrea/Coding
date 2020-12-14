GO

--generates a random float number between [0, 1) and add it in a view
CREATE VIEW RandomView
AS
SELECT RAND() AS Value
GO

--DROP FUNCTION generate_random_int
GO
--generates random int between [leftBound, rightBound]
CREATE FUNCTION generate_random_int(
	@leftBound INT,
	@rightBound INT
)
RETURNS INT
AS
BEGIN
	RETURN FLOOR((SELECT Value FROM RandomView) * (@rightBound - @leftBound) + @leftBound)
END
GO


--insert a test entry into Team table
CREATE PROCEDURE procedure_insert_Team @seed INT
AS
    BEGIN
        INSERT Team(TeamName, TeamNation, FoundingYear)
            VALUES
            ('Team' + CONVERT(VARCHAR(10), @seed),
             'Nationality' + CONVERT(VARCHAR(10), @seed),
             1900 + @seed)
    END
GO

--delete all entries from Team table, reseeds the Table
create procedure procedure_delete_Team
    as
    begin
        delete from Team
        DBCC CHECKIDENT ('Team', RESEED, 0);
    end
        go



--inserts one test entry into the Driver table
CREATE PROCEDURE procedure_insert_Driver @seed INT
AS
    BEGIN
        insert Driver(DriverID, DriverName, DateOfBirth, Nationality, TeamID)
            VALUES
            (@seed,
             'Test Driver' + CONVERT(VARCHAR(10), @seed),
             CONVERT(VARCHAR(10), 1900 + @seed) + '-01-12',
             'Test Nationality' + CONVERT(VARCHAR(10), @seed),
             [dbo].[generate_random_int](1, (SELECT COUNT(*) FROM Team)))
    end
    go

--delete all entries from Driver table, reseeds the table
create procedure procedure_delete_Driver
    as
    begin
        delete from Driver
    end
go

--inserts one test entry to Sponsor table
create procedure procedure_insert_Sponsor @seed int
as
    begin
        insert Sponsor(sponsorname, sponsorbudget)
        VALUES
        ('Sponsor' + CONVERT(VARCHAR(10), @seed),
         @seed)
    end
GO

--delete all entries from Sponsor table, reseeds the table
create procedure procedure_delete_Sponsor as
    begin
        delete from Sponsor
        DBCC CHECKIDENT ('Sponsor', RESEED, 0);
    end
    go


--inserts one test entry to Sponsor_Team table
create procedure procedure_insert_Sponsor_Team @seed INT
    as
    begin
        Insert Sponsor_Team(SponsorID, TeamID, SponsorshipValue) VALUES
        (@seed,
         @seed,
         [dbo].[generate_random_int] (1, 15))
    end
    go

--delete all entries from Sponsor_Team table
create procedure procedure_delete_Sponsor_Team
    as
    begin
        delete from Sponsor_Team
    end
go

--populates a table whose name is given as parameter with rows entries
create procedure procedure_populate_table @name varchar(50), @rows int
as
    begin
        declare @currentRow int, @command VARCHAR(1024)
        set @currentRow = 1
        while @currentRow <= @rows
        begin
            set @command = 'procedure_insert_' + @name + ' ' + CONVERT(VARCHAR(10), @currentRow)
            exec(@command)
            set @currentRow = @currentRow + 1
        end
    end
GO

--select the sponsors with a budget greater than 50
create view sponsors_View
as
        select *
        from Sponsor S
        where S.SponsorBudget >= 50
go


--select the drivers that drive for teams founded after 1950
create view drivers_View
as
  select D.DriverName, T.TeamName, T.FoundingYear
    from Driver D, Team T
    where D.TeamID = T.TeamID and T.FoundingYear >= 1950
go

--show the number of drivers for each team
create view number_of_drivers_View
as
    select T.TeamID, COUNT(D.DriverID) as NumberOfDrivers
    from Team T, Driver D
    where D.TeamID = T.TeamID
    group by T.TeamID
go


create procedure procedure_create_test @test_name VARCHAR(50)
as
    begin
        if @test_name in (select Name from Tests)
        begin
            print 'Test ' + @test_name + ' already exists!'
            return
        end
        insert Tests(Name)
        values (@test_name)
    end
go

create procedure procedure_delete_tests
    as
    begin
        delete from Tests
        DBCC CHECKIDENT ('Tests', RESEED, 0);
    end
go

--adds a table to Tables that stores tables to be tested.
create procedure procedure_add_test_table @table_name varchar(50)
as
    begin
        if exists(select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = @table_name)
        begin
            if @table_name not in (select Name from Tables)
            begin
                insert Tables(Name)
                values (@table_name)
            end
            else
                begin
                    print 'table already added!'
                end
        end
        else
            begin
                print 'table ' + @table_name + ' does not exist!'
            end
    end
go

--adds an entry into TestTables
create procedure procedure_add_TestTables
    @table_name varchar(50),
    @test_name varchar(50),
    @number_of_rows int,
    @position int
as
    begin
        if @position <= 0
            begin
                print 'invalid position!'
                return
            end

        if @number_of_rows <= 0
            begin
                print 'invalid number of rows!'
                return
            end

        declare @test_id int, @table_id int
        set @test_id = (select TestID from Tests where Name = @test_name)
        if @test_id is null
            begin
                print 'No test with name ' + @test_name + '!'
                return
            end
        set @table_id = (select TableID from Tables where Name = @table_name)
        if @table_id is null
            begin
                print 'No table with name ' + @table_name + '!'
                return
            end
        begin try
            insert TestTables(TestID, TableID, NoOfRows, Position)
            values (@test_id, @table_id, @number_of_rows, @position)
        end try
        begin catch
            print error_message()
        end catch
    end
go


--adds a View into Views table that stores Views to be tested.
create procedure procedure_add_test_view @view_name varchar(50)
as
    begin
        if exists( select * from INFORMATION_SCHEMA.VIEWS where TABLE_NAME = @view_name)
        begin
            if @view_name not in (select Name from Views)
            begin
                insert Views(Name)
                values (@view_name)
            end
            else
                begin
                    print 'View ' + @view_name + ' already exists in Views table!'
                end
        end
        else
            begin
                print 'View ' + @view_name + ' does not exist in the database!'
            end
    end
go

--adds an entry into TestViews
create procedure procedure_add_TestViews
@view_name varchar(50),
@test_name varchar(50)
as
    begin
        declare @test_id int, @view_id int
        set @test_id = (select TestID from Tests where Name = @test_name)
        if @test_id is null
            begin
                print 'No test named ' + @test_name + ' in Tests table!'
                return
            end
        set @view_id = (select ViewID from Views where Name = @view_name)
        if @view_id is null
            begin
                print 'No view named ' + @view_name +' in Views table!'
                return
            end
        begin try
            insert TestViews(TestID, ViewID)
            values (@test_id, @view_id)
        end try
        begin catch
            print error_message()
        end catch
    end
go

create procedure procedure_run_test @test_name varchar(50)
as
    begin
        declare @test_id int
        set @test_id = (select TestID from Tests where Name = @test_name)
        if @test_id is null
            begin
                print 'Test '+ @test_name + ' does not exist in Tests table!'
                return
            end
        --get all tables for the test ordered by the position
        declare TablesCursor cursor scroll for
        select Tables.TableID, Tables.Name, TestTables.NoOfRows
        from TestTables inner join Tables on TestTables.TableID = Tables.TableID
        where TestTables.TestID = @test_id
        order by TestTables.Position

        --get all views for the test
        declare ViewsCursor cursor scroll for
        select V.ViewID, V.Name
        from Views V inner join TestViews TV on V.ViewID = TV.ViewID
        where TV.TestID = @test_id

        declare
            @table varchar(50),
            @number_of_rows int,
            @position int,
            @table_id int,
            @test_start_time datetime2,
            @test_end_time datetime2,
            @subtest_start_time datetime2,
            @subtest_end_time datetime2,
            @view_id int,
            @view varchar(50),
            @test_run_id int,
            @command varchar(512)

        set nocount on
        --Stops the message that shows the count of the number of rows affected by a Transact-SQL statement or stored
        --procedure from being returned as part of the result set.

        insert into TestRuns(Description) VALUES
        ('Test results for ' + @test_name)

        set @test_run_id = convert(int, (select last_value from sys.identity_columns where name = 'TestRunID'))
        set @test_start_time = sysdatetime()

        --delete data from test's tables in order specified by position
        open TablesCursor
        fetch first from TablesCursor
        into @table_id, @table, @number_of_rows
        while @@fetch_status = 0 --while the fetch is successful
        begin
            exec('procedure_delete_' + @table)
            fetch next from TablesCursor
            into @table_id, @table, @number_of_rows
        end
        close TablesCursor

        --insert data into test tables - reverse deletion order
        open TablesCursor
        fetch last from TablesCursor
        into @table_id, @table, @number_of_rows
        while @@FETCH_STATUS = 0
        begin
            set @command = 'procedure_populate_table ''' + @table + ''', ' + CONVERT(VARCHAR(10), @number_of_rows)
            set @subtest_start_time = sysdatetime()
            exec(@command)
            set @subtest_end_time = sysdatetime()
            print 'finished inserting data in ' + @table
            insert TestRunTables(TestRunID, TableID, StartAt, EndAt)
            values (@test_run_id, @table_id, @subtest_start_time, @subtest_end_time)

            fetch prior from TablesCursor
            into @table_id, @table, @number_of_rows
        end
        print 'finished tables test!'

        open ViewsCursor
        fetch ViewsCursor
        into @view_id, @view

        while @@fetch_status = 0
        begin
            set @command = 'select * from ' + @view
            set @subtest_start_time = sysdatetime()
            exec(@command)
            set @subtest_end_time = sysdatetime()
            insert TestRunViews (TestRunID, ViewID, StartAt, EndAt)
            values (@test_run_id, @view_id, @subtest_start_time, @subtest_end_time)
            fetch ViewsCursor
            into @view_id, @view
        end
        set @test_end_time = sysdatetime()

        --add the start/end times
        update TestRuns
        set StartAt = @test_start_time, EndAt = @test_end_time
        where TestRunID = @test_run_id

        close TablesCursor
        close  ViewsCursor
        deallocate ViewsCursor
        deallocate TablesCursor

        set nocount off
    end
go

create procedure procedure_delete_TestRuns
    as
    begin
        delete from TestRuns
        DBCC CHECKIDENT ('TestRuns', RESEED, 0);
    end
go

create procedure procedure_test_results @test_name varchar(50)
as
    begin
        select * from TestRuns
        where Description like '%' + @test_name + '%'
        select TRT.TestRunID, T.Name, TRT.StartAt, TRT.EndAt
        from TestRunTables TRT inner join Tables T on TRT.TableID = T.TableID
        where TRT.TestRunID in (
            select TestRunID from TestRuns
            where Description like '%' + @test_name + '%'
            )
        order by StartAt

        select TRV.TestRunID, V.Name, TRV.StartAt, TRV.EndAt
        from TestRunViews TRV inner join Views V on TRV.ViewID = V.ViewID
        where TRV.TestRunID in (
            select TestRunID from TestRuns
            where Description like '%' + @test_name + '%'
            )
        order by StartAt
    end
go


--setup tests
exec procedure_add_test_table 'Team'
exec procedure_add_test_table 'Driver'
exec procedure_add_test_table 'Sponsor'
exec procedure_add_test_table 'Sponsor_Team'

exec procedure_add_test_view drivers_View
exec procedure_add_test_view number_of_drivers_View
exec procedure_add_test_view sponsors_View

exec procedure_create_test 'test1'
exec procedure_create_test 'test2'
exec procedure_create_test 'test3'

--test1 add tables /views
exec procedure_add_TestTables 'Team', 'test1', 500, 4
exec procedure_add_TestTables 'Driver', 'test1', 500, 3
exec procedure_add_TestTables 'Sponsor', 'test1', 100, 2
exec procedure_add_TestTables 'Sponsor_Team', 'test1', 100, 1

exec procedure_add_TestViews 'drivers_View', 'test1'
exec procedure_add_TestViews 'number_of_drivers_View', 'test1'
exec procedure_add_TestViews 'sponsors_View', 'test1'

--test2 add tables /views
exec procedure_add_TestTables 'Team', 'test2', 5000, 4
exec procedure_add_TestTables 'Driver', 'test2', 5000, 3
exec procedure_add_TestTables 'Sponsor', 'test2', 1000, 2
exec procedure_add_TestTables 'Sponsor_Team', 'test2', 1000, 1

exec procedure_delete_TestRuns
exec procedure_run_test 'test1'
exec procedure_run_test 'test2'

exec procedure_test_results 'test1'
exec procedure_test_results 'test2'

select * from TestRuns
select * from TestRunTables



--test3 add tables / views
exec procedure_add_TestTables 'Sponsor', 'test3', 10, 1

exec procedure_run_test 'test3'