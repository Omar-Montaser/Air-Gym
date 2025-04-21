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
    Description TEXT,
    Price DECIMAL(10,2) NOT NULL,
    Duration INT NOT NULL,
    noOfSessions INT,
    noOfPrivateSessions INT,
    FreezeDuration INT,
    InBody BIT DEFAULT 0,
    AccessLevel VARCHAR(100)
);
CREATE TABLE User (
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    -- For login
    -- Username VARCHAR(50) NOT NULL UNIQUE, 
    -- PasswordSalt CHAR(29) NOT NULL,
    -- PasswordHash VARCHAR(255) NOT NULL,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    PhoneNumber VARCHAR(15),
    Gender VARCHAR(10) CHECK (Gender IN ('Male', 'Female')),
    DateOfBirth DATE,
    Role VARCHAR(20) CHECK (Role IN ('Admin', 'Member', 'Trainer')) NOT NULL
);

CREATE TABLE Trainer (
    UserID INT PRIMARY KEY FOREIGN KEY REFERENCES [User](UserID),

    Specialization VARCHAR(100),
    ExperienceYears INT CHECK (ExperienceYears >= 0),
    Salary DECIMAL(10,2),
    -- CVPath VARCHAR(255), -- Optional: link to uploaded CV
    BranchID INT FOREIGN KEY REFERENCES Branch(BranchID),
    Status VARCHAR(20) CHECK (Status IN ('Active', 'OnLeave', 'Terminated')) DEFAULT 'Active'
);

CREATE TABLE Member (
    UserID INT PRIMARY KEY FOREIGN KEY REFERENCES [User](UserID),

    JoinDate DATE DEFAULT GETDATE(),
    MembershipID INT FOREIGN KEY REFERENCES MembershipType(MembershipID),
    BranchID INT FOREIGN KEY REFERENCES Branch(BranchID),
    TrainerID INT NULL FOREIGN KEY REFERENCES Trainer(UserID), -- PT
    PaymentMethod VARCHAR(20) CHECK (PaymentMethod IN ('CreditCard', 'DebitCard', 'Cash', 'BankTransfer', 'Other')),
    SubscriptionStatus VARCHAR(20) CHECK (SubscriptionStatus IN ('Active', 'Expired', 'Pending', 'Cancelled', 'OnHold')) DEFAULT 'Active'
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
    TrainerID INT FOREIGN KEY REFERENCES Trainer(UserID),
    BranchID INT FOREIGN KEY REFERENCES Branch(BranchID),
    SessionType VARCHAR(20) CHECK (SessionType IN ('Yoga', 'HIIT', 'Cycling', 'Pilates', 'Boxing', 'Zumba')),
    MaxCapacity INT NOT NULL CHECK (MaxCapacity > 0),
    DateTime DATETIME NOT NULL,
    Duration INT NOT NULL CHECK (Duration > 0), -- in minutes
    Status VARCHAR(20) CHECK (Status IN ('Scheduled', 'Completed', 'Cancelled', 'Full')) DEFAULT 'Scheduled'
);

CREATE TABLE Booked_Sessions (
    BookingID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT FOREIGN KEY REFERENCES Member(UserID),
    SessionID INT FOREIGN KEY REFERENCES Session(SessionID),
    Status VARCHAR(20) CHECK (Status IN ('Confirmed', 'Cancelled', 'NoShow', 'Pending')) DEFAULT 'Pending',
    PaymentStatus VARCHAR(20) CHECK (PaymentStatus IN ('Paid', 'Pending', 'Refunded', 'Failed')),
    BookingDate DATETIME DEFAULT GETDATE()
);

CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT FOREIGN KEY REFERENCES User(UserID),
    PaymentDate DATETIME DEFAULT GETDATE(),
    Amount DECIMAL(10,2) NOT NULL,
    PaymentMethod VARCHAR(20) CHECK (PaymentMethod IN ('CreditCard', 'DebitCard', 'Cash', 'BankTransfer', 'Other')),
    Status VARCHAR(20) CHECK (Status IN ('Completed', 'Pending', 'Failed', 'Refunded'))
);