CREATE DATABASE AirGym;
GO

USE AirGym;
GO


CREATE TABLE Branch (
    BranchID INT IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Location VARCHAR(255) NOT NULL,
    PhoneNumber VARCHAR(15),
    OpeningDate DATE,
    Status VARCHAR(20) CHECK (Status IN ('Active', 'Maintenance', 'Closed')) DEFAULT 'Active'
);

CREATE TABLE MembershipType (
    MembershipID INT IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Duration INT NOT NULL, -- in days
    AccessLevel VARCHAR(100)
);

CREATE TABLE Trainer (
    TrainerID INT IDENTITY(1,1) PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Specialization VARCHAR(50),
    Email VARCHAR(100),
    PhoneNumber VARCHAR(15),
    ExperienceYears INT,
    Salary DECIMAL(10,2),
    BranchID INT FOREIGN KEY REFERENCES Branch(BranchID),
    Status VARCHAR(15) CHECK (Status IN ('Active', 'OnLeave', 'Terminated')) DEFAULT 'Active'
);

CREATE TABLE Member (
    MemberID INT IDENTITY(1,1) PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Email VARCHAR(100),
	PasswordHash CHAR(60) NOT NULL, 
    PasswordSalt CHAR(29) NOT NULL,
    PhoneNumber VARCHAR(15),
    Address TEXT,
    DateOfBirth DATE,
    Gender VARCHAR(10) CHECK (Gender IN ('Male', 'Female', 'Other')),
    JoinDate DATE DEFAULT GETDATE(),
    MembershipID INT FOREIGN KEY REFERENCES MembershipType(MembershipID),
    BranchID INT FOREIGN KEY REFERENCES Branch(BranchID),
    PaymentMethod VARCHAR(20) CHECK (PaymentMethod IN ('CreditCard', 'DebitCard', 'Cash', 'BankTransfer', 'Other')),
    SubscriptionStatus VARCHAR(20) CHECK (SubscriptionStatus IN ('Active', 'Expired', 'Pending', 'Cancelled', 'OnHold'))
);

CREATE TABLE Equipment (
    EquipmentID INT IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    PurchaseDate DATE,
    MaintenanceDate DATE,
    Status VARCHAR(20) CHECK (Status IN ('Available','Maintenance','Retired')) DEFAULT 'Available',
    BranchID INT FOREIGN KEY REFERENCES Branch(BranchID)
);

CREATE TABLE Session (
    SessionID INT IDENTITY(1,1) PRIMARY KEY,
    TrainerID INT FOREIGN KEY REFERENCES Trainer(TrainerID),
    BranchID INT FOREIGN KEY REFERENCES Branch(BranchID),
    SessionType VARCHAR(20) CHECK (SessionType IN ('Yoga', 'HIIT', 'Cycling', 'Pilates', 'Boxing', 'Zumba')),
    MaxCapacity INT NOT NULL CHECK (MaxCapacity > 0),
    DateTime DATETIME NOT NULL,
    Duration INT NOT NULL CHECK (Duration > 0), -- in minutes
    Status VARCHAR(20) CHECK (Status IN ('Scheduled', 'Completed', 'Cancelled', 'Full')) DEFAULT 'Scheduled'
);

CREATE TABLE Booking (
    BookingID INT IDENTITY(1,1) PRIMARY KEY,
    MemberID INT FOREIGN KEY REFERENCES Member(MemberID),
    SessionID INT FOREIGN KEY REFERENCES Session(SessionID),
    Status VARCHAR(20) CHECK (Status IN ('Confirmed', 'Cancelled', 'NoShow', 'Pending')) DEFAULT 'Pending',
    PaymentStatus VARCHAR(20) CHECK (PaymentStatus IN ('Paid', 'Pending', 'Refunded', 'Failed')),
    BookingDate DATETIME DEFAULT GETDATE()
);

CREATE TABLE UsageLog (
    UsageID INT IDENTITY(1,1) PRIMARY KEY,
    MemberID INT FOREIGN KEY REFERENCES Member(MemberID),
    EquipmentID INT FOREIGN KEY REFERENCES Equipment(EquipmentID),
    StartTime DATETIME NOT NULL,
    EndTime DATETIME,
    Status VARCHAR(20) CHECK (Status IN ('Completed', 'InProgress', 'Cancelled'))
);

CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    MemberID INT FOREIGN KEY REFERENCES Member(MemberID),
    PaymentDate DATETIME DEFAULT GETDATE(),
    Amount DECIMAL(10,2) NOT NULL,
    PaymentMethod VARCHAR(20) CHECK (PaymentMethod IN ('CreditCard', 'DebitCard', 'Cash', 'BankTransfer', 'Other')),
    Status VARCHAR(20) CHECK (Status IN ('Completed', 'Pending', 'Failed', 'Refunded'))
);

CREATE INDEX IX_Member_Email ON Member(Email);
CREATE INDEX IX_Session_TrainerID ON Session(TrainerID);
CREATE INDEX IX_Booking_SessionID ON Booking(SessionID);