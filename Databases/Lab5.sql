create table Ta
    (aid int primary key,
    a2 int
    )

create table Tb
    (bid int primary key,
    b2 int
    )

create table Tc
    (cid int primary key,
    aid int foreign key references Ta(aid),
    bid int foreign key references Tb(bid)
    )


