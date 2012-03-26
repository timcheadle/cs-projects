-- Tim Cheadle
-- tcheadle@gmu.edu
-- March 25, 2003
--
-- CS 332: Extra Credit

create table Employee (
	ESSN char(11) not null,
	DNumber integer not null,
	FName varchar2(15) null,
	MInit char(1) null,
	LName varchar2(20) null,
	BDate date null,
	Address varchar2(30) null,
	Salary numeric(10,2) null,
	SuperSSN char(11) null,
	primary key (ESSN)
);

create table Department (
	DNumber integer not null,
	DName varchar2(10) not null,
	MgrSSN char(11) null,
	MgrStartDate date null,
	primary key (DNumber)
);

create table DeptLocation (
	DLocation varchar2(20) not null,
	DNumber integer not null,
	primary key (DLocation, DNumber)
);

create table Project (
	PNumber integer not null,
	DNumber integer not null,
	PName varchar2(15) not null,
	PLocation varchar2(20) null,
	primary key (PNumber)
);

create table WorksOn (
	ESSN char(11) not null,
	PNumber integer not null,
	Hours numeric(3,1) null,
	primary key (ESSN, PNumber)
);

create table Dependent (
	DependentName varchar2(20) not null,
	ESSN char(11) not null,
	Sex char(1) null,
	BDate date null,
	Relationship varchar2(8) null,
	primary key (DependentName, ESSN)
);

alter table Employee add (foreign key (SuperSSN) references Employee(ESSN));
alter table Employee add (foreign key (DNumber) references Department);

alter table DeptLocation add (foreign key (DNumber) references Department);

alter table Project add (foreign key (DNumber) references Department);

alter table WorksOn add (foreign key (ESSN) references Employee);
alter table WorksOn add (foreign key (PNumber) references Project);

alter table Dependent add (foreign key (ESSN) references Employee);
