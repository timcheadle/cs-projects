drop table Laboratory_Technician;
create table Laboratory_Technician (
	Technician_ID numeric(9) not null,
	Laboratory_ID int not null
);

drop table Laboratory;
create table Laboratory (
	Laboratory_ID int not null,
	Name varchar2(64) not null,
	Location varchar2(32) not null
);

drop table Zip_Code;
create table Zip_Code (
	Zip numeric(5) not null,
	City varchar2(32) not null,
	State varchar2(2) not null
);

drop table Employee;
create table Employee (
	Employeee_ID numeric(9) not null,
	Date_Hired date not null,
	Salary numeric(10,2) not null,
	Employee_Type varchar2(10) not null
);

drop table Staff;
create table Staff (
	Staff_ID numeric(9) not null,
	Job_Class varchar2(10) not null
);

drop table Person;
create table Person (
	Person_ID numeric(9) not null,
	FName varchar2(16) not null,
	MInit char(1) null,
	LName varchar2(32) not null,
	Street varchar2(31) not null,
	Zip numeric(5) not null,
	Phone numeric(10) not null,
	DOB date not null
);

drop table Care_Center_Employee;
create table Care_Center_Employee (
	Employee_ID numeric(9) not null,
	Care_Center_ID int not null,
	Hours numeric(5,2) not null
);

drop table Care_Center;
create table Care_Center (
	Nurse_In_Charge_ID numeric(9) not null,
	Name varchar2(32) not null,
	Care_Center_ID int not null
);

drop table Volunteer;
create table Volunteer (
	Volunteer_ID numeric(9) not null,
	Skill varchar2(32) not null
);

drop table Registered_Nurse;
create table Registered_Nurse (
	Nurse_ID numeric(9) not null
);

drop table Nurse;
create table Nurse (
	Nurse_ID numeric(9) not null,
	Certification varchar2(10) not null
);

drop table In_Patient;
create table In_Patient (
	Patient_Number int not null,
	Admittance_Date date not null,
	Bed_Number int not null,
	Care_Center_ID int not null
);

drop table Out_Patient;
create table Out_Patient (
	Patient_Number int not null
);

drop table Scheduled_Visit;
create table Scheduled_Visit (
	Visit_Date date not null,
	Comments varchar2(255) null,
	Patient_Number int not null
);

drop table Physician;
create table Physician (
	Physician_ID numeric(9) not null,
	Specialty varchar2(20) not null,
	Pager_Number numeric(10) not null
);

drop table Bed;
create table Bed (
	Bed_Number int not null,
	Care_Center_ID int not null,
	Room_Number int not null
);

drop table Patient;
create table Patient (
	Patient_Number int not null,
	Patient_Type char(1) not null,
	Patient_ID numeric(9) not null,
	Referred_Physician_ID numeric(9) not null
);

drop table Treatment;
create table Treatment (
	Name varchar2(32) not null,
	Treatment_ID int not null
);

drop table Patient_Treatment;
create table Patient_Treatment (
	Patient_Number int not null,
	Treatment_ID int not null,
	Physician_ID numeric(9) not null,
	Date_Time date not null,
	Results varchar2(32) not null
);

drop table Patient_Item;
create table Patient_Item (
	Item_Number int not null,
	Patient_Number int not null,
	Date_Time date not null,
	Quantity int not null
);

drop table Medical_Surgical_Item;
create table Medical_Surgical_Item (
	Item_Number int not null,
	Name varchar2(32) not null,
	Description varchar2(64) not null,
	Cost numeric(10,2) not null
);
