-- Tim Cheadle
-- tcheadle@gmu.edu
-- April 8, 2003
--
-- CS332: Homework 2

create table Book (
	BookId char(18) not null,
	Title varchar2(30) not null,
	PublisherName varchar2(50) not null,
	primary key (BookId)
);

create table Book_Authors (
	BookId char(18) not null,
	AuthorName varchar2(50) not null,
	primary key (BookId, AuthorName)
);

create table Publisher (
	Name varchar2(50) not null,
	Address varchar2(100) not null,
	Phone integer null,
	primary key (Name)
);

create table Book_Copies (
	BookId char(18) not null,
	BranchId integer not null,
	No_Of_Copies integer not null,
	primary key (BookId, BranchId)
);

create table Book_Loans (
	BookId char(18) not null,
	BranchId integer not null,
	CardNo integer not null,
	DateOut date not null,
	DueDate date not null,
	primary key (BookId, BranchId, CardNo)
);

create table Library_Branch (
	BranchId integer not null,
	BranchName varchar2(60) not null,
	Address varchar2(100) not null,
	primary key (BranchId)
);

create table Borrower (
	CardNo integer not null,
	Name varchar2(50) not null,
	Address varchar2(100) not null,
	Phone integer null,
	primary key (CardNo)
);

alter table Book         add (foreign key (PublisherName) references Publisher(Name));

alter table Book_Authors add (foreign key (BookId) references Book(BookId));
alter table Book_Copies  add (foreign key (BookId) references Book(BookId));
alter table Book_Loans   add (foreign key (BookId) references Book(BookId));

alter table Book_Copies  add (foreign key (BranchId) references Library_Branch(BranchId));
alter table Book_Loans   add (foreign key (BranchId) references Library_Branch(BranchId));

alter table Book_Loans   add (foreign key (CardNo) references Borrower(CardNo));
