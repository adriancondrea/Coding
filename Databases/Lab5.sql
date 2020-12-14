create table Ta
    (aid int primary key,
    a2 int
    )

alter table Ta
add CONSTRAINT a2_unique UNIQUE (a2)
go

create table Tb
    (bid int primary key,
    b2 int
    )

create table Tc
    (cid int primary key,
    aid int foreign key references Ta(aid),
    bid int foreign key references Tb(bid)
    )

go


--Ta is sponsor
sp_helpindex Sponsor
go

alter table Sponsor
    add constraint sponsorBudget_unique UNIQUE(SponsorBudget)
go


--a) Write queries on Ta such that their execution plans contain the following operators:
--1. clustered index scan;
select * from Sponsor
    go

--2.clustered index seek;
SELECT * from Sponsor
    where SponsorID = 1
    go

create nonclustered index IX_Sponsor_SponsorBudget_asc
on Sponsor(SponsorBudget asc)
    go

create nonclustered index IX_Sponsor_SponsorName_asc
on Sponsor(SponsorName asc)
include (SponsorID)
go

--3. nonclustered index scan
select SponsorID, SponsorBudget
from Sponsor
order by SponsorBudget

--4. nonclustered index seek
select SponsorID, SponsorBudget
from Sponsor
where SponsorBudget > 10
order by SponsorBudget

--5. key lookup
select SponsorName, SponsorBudget
from Sponsor
order by SponsorBudget


--b. Write a query on table Tb with a WHERE clause of the form WHERE b2 = value and analyze
-- its execution plan. Create a nonclustered index that can speed up the query. Examine the
-- execution plan again.
--Tb is Team

--0.0065 with clustered index
--0.0317 without clustered index
SELECT TeamName, TeamNation
from Team
where FoundingYear = 2000


create nonclustered index IX_Team_FoundingYear_asc
on Team(FoundingYear)
go

drop index IX_Team_FoundingYear_asc on Team
go

--c. Create a view that joins at least 2 tables. Check whether existing indexes are helpful;
-- if not, reassess existing indexes / examine the cardinality of the tables.
--uses IX_Sponsor_SponsorBudget_asc
create view st_view as
select T.TeamName, S.SponsorName, S.SponsorBudget
from Team T inner join Sponsor S on T.TeamID = S.sponsorID
where S.SponsorBudget = 5
go


--uses IX_Sponsor_SponsorName_asc
--find sponsors that sponsor both McLaren and Mercedes
SELECT S.SponsorName
FROM Sponsor S, Team T, Sponsor_Team ST
WHERE ST.TeamID = T.TeamID AND ST.SponsorID = S.SponsorID and T.TeamName LIKE  '%McLaren%'
INTERSECT
SELECT DISTINCT S1.SponsorName
FROM Sponsor S1, Team T1, Sponsor_Team ST1
WHERE ST1.TeamID = T1.TeamID AND ST1.SponsorID = S1.SponsorID and T1.TeamName LIKE  '%Mercedes%'