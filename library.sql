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



--
-- SQL Queries
--

select No_Of_Copies
	from Book_Copies, Library_Branch, Book
	where Book_Copies.BranchId = Library_Branch.BranchId
		and Book_Copies.BookId = Book.BookId
		and Title = 'The Lost Tribes'
		and BranchName = 'Sharpstown';

select BranchName, No_Of_Copies
	from Book_Copies, Library_Branch, Book
	where Book_Copies.BranchId = Library_Branch.BranchId
		and Book_Copies.BookId = Book.BookId
		and Title = 'The Lost Tribes';

(select Name from Borrower)
	except (select Name
		from Borrower, Book_Loans
		where Borrower.CardNo = Book_Loans.CardNo);

select Title, Borrower.Name, Borrower.Address
	from Book, Borrower, Book_Loans, Library_Branch
	where Borrower.CardNo = Book_Loans.CardNo
		and Book_Loans.BranchId = Library_Branch.BranchId
		and Book_Loans.BookId = Book.BookId
		and BranchName = 'Sharpstown'
		and DueDate = '08-APR-2003';

select BranchName, count(Book_Loans.BranchId)
	from Library_Branch, Book_Loans
	group by Library_Branch.BranchId;

select Name, Address, count(Book_Loans.CardNo)
	from Borrower, Book_Loans
	where Borrow.CardNo = Book_Loans.CardNo
	having count(Book_Loans.CardNo) > 5;

select Title, No_Of_Copies
	from Book, Book_Copies, Library_Branch, Book_Authors
	where Book.BookId = Book_Authors.BookId
		and Book.BookId = Book_Copies.BookId
		and Library_Branch.BranchId = Book_Copies.BranchId
		and AuthorName like '%Stephen King%'
		and BranchName = 'Central';
