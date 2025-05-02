CREATE DATABASE AirGym;
USE AirGym;
GO
CREATE TABLE Users(
    UserID INT IDENTITY(1,1) PRIMARY KEY,
    Password VARCHAR(255) NOT NULL CHECK (LEN(Password) >= 8),
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL UNIQUE,
    Gender VARCHAR(10) NOT NULL CHECK (Gender IN ('Male', 'Female')),
    DateOfBirth DATE NOT CHECK (DateOfBirth <= DATEADD(YEAR, -16, GETDATE())),
    Role VARCHAR(20) NOT NULL CHECK (Role IN ('Admin', 'Member', 'Trainer'))
);
ALTER TABLE Users
ADD CONSTRAINT CK_PhoneNumber_Egyptian
CHECK (
    LEN(REPLACE(REPLACE(REPLACE(REPLACE(PhoneNumber, '(', ''), ')', ''), '-', ''), ' ', '')) = 11
    AND PhoneNumber LIKE '01%'
    AND SUBSTRING(PhoneNumber, 3, 1) IN ('0', '1', '2', '5')
    AND PhoneNumber NOT LIKE '%[A-Za-z]%'
);

CREATE TABLE Branch(
    BranchID INT IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL UNIQUE,
    Location VARCHAR(255) NOT NULL,
    OpeningDate DATE NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'Active' CHECK (Status IN ('Active', 'Maintenance', 'Closed')),
    AdminID INT NULL,
    CONSTRAINT FK_Branch_Admin FOREIGN KEY (AdminID) REFERENCES Users(UserID) ON DELETE SET NULL
);

CREATE TABLE MembershipType (
    MembershipTypeID INT IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    Description TEXT,
    AccessLevel INT NOT NULL,
    MonthlyPrice DECIMAL(10,2) NOT NULL,
    Sessions INT NOT NULL DEFAULT 0,
    PrivateTrainer BIT NOT NULL DEFAULT 0,
    FreezeDuration INT NOT NULL DEFAULT 0,
    InBody INT NOT NULL,
    ColorHex VARCHAR(7) CHECK (ColorHex LIKE '#______')
);

CREATE TABLE Trainer (
    UserID INT PRIMARY KEY,
    Specialization VARCHAR(100) NOT NULL,
    ExperienceYears INT CHECK (ExperienceYears >= 0),
    Salary DECIMAL(10,2) NOT NULL,
    BranchID INT NULL, 
    Status VARCHAR(20) NOT NULL DEFAULT 'Active' CHECK (Status IN ('Active', 'OnLeave', 'Terminated')),
    
    CONSTRAINT FK_Trainer_User FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    CONSTRAINT FK_Trainer_Branch FOREIGN KEY (BranchID) REFERENCES Branch(BranchID) ON DELETE SET NULL
);

CREATE TABLE Member (
    UserID INT PRIMARY KEY,
    MembershipTypeID INT NULL,
    SubscriptionStartDate DATE NOT NULL,
    SubscriptionEndDate DATE NOT NULL,
    FreezeEndDate DATE NULL,
    SessionsAvailable INT NOT NULL DEFAULT 0,
    FreezesAvailable INT NOT NULL DEFAULT 0,
    BranchID INT NULL, 
    TrainerID INT NULL,
    SubscriptionStatus VARCHAR(20) NOT NULL DEFAULT 'Active' CHECK (SubscriptionStatus IN ('Active', 'Expired', 'Cancelled', 'Frozen')),
    CONSTRAINT FK_Member_User FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    CONSTRAINT FK_Member_MembershipType FOREIGN KEY (MembershipTypeID) REFERENCES MembershipType(MembershipTypeID) ON DELETE SET NULL,
    CONSTRAINT FK_Member_Branch FOREIGN KEY (BranchID) REFERENCES Branch(BranchID) ON DELETE SET NULL,
    CONSTRAINT CK_Subscription_Duration CHECK (DATEDIFF(day, SubscriptionStartDate, SubscriptionEndDate) <= 365),
    CONSTRAINT CK_Subscription_Dates CHECK (SubscriptionStartDate <= SubscriptionEndDate)
);

CREATE TABLE Equipment (
    EquipmentID INT IDENTITY(1,1) PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    PurchaseDate DATE NOT NULL,
    MaintenanceDate DATE,
    Status VARCHAR(20) NOT NULL DEFAULT 'Available' CHECK (Status IN ('Available', 'Maintenance', 'Retired')),
    BranchID INT NOT NULL,
    CONSTRAINT FK_Equipment_Branch FOREIGN KEY (BranchID) REFERENCES Branch(BranchID) ON DELETE CASCADE
);

CREATE TABLE Session (
    SessionID INT IDENTITY(1,1) PRIMARY KEY,
    TrainerID INT NOT NULL,
    BranchID INT NOT NULL,
    SessionType VARCHAR(20) NOT NULL CHECK (SessionType IN ('Yoga', 'HIIT', 'Cycling', 'Pilates', 'Boxing', 'Zumba')),
    MaxCapacity INT NOT NULL CHECK (MaxCapacity > 0),
    DateTime DATETIME NOT NULL,
    Duration INT NOT NULL CHECK (Duration > 0),
    Status VARCHAR(20) NOT NULL DEFAULT 'Scheduled' CHECK (Status IN ('Scheduled', 'Completed', 'Cancelled', 'Full')),
    CONSTRAINT FK_Session_Trainer FOREIGN KEY (TrainerID) REFERENCES Trainer(UserID) ON DELETE CASCADE,
    CONSTRAINT FK_Session_Branch FOREIGN KEY (BranchID) REFERENCES Branch(BranchID) ON DELETE CASCADE
);

CREATE TABLE Payment (
    PaymentID INT IDENTITY(1,1) PRIMARY KEY,
    Category VARCHAR(50) NOT NULL CHECK (Category IN ('Membership', 'Expense', 'Other')),
    MemberID INT NOT NULL, 
    PaymentMethod VARCHAR(20) CHECK (PaymentMethod IN ('CreditCard', 'DebitCard', 'Cash', 'BankTransfer', 'Other')),
    PaymentDate DATETIME DEFAULT GETDATE(),
    Amount DECIMAL(10,2) NOT NULL,
    Status VARCHAR(20) DEFAULT 'Completed' NOT NULL CHECK (Status IN ('Completed', 'Cancelled')),
    CONSTRAINT FK_Payment_Member FOREIGN KEY (MemberID) REFERENCES Member(UserID) ON DELETE CASCADE
);
CREATE TABLE Booking (
    BookingID INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT NOT NULL, 
    SessionID INT NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT 'Confirmed' CHECK (Status IN ('Confirmed', 'Cancelled')),
    BookingDate DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Booking_Member FOREIGN KEY (UserID) REFERENCES Member(UserID) ON DELETE CASCADE,
    CONSTRAINT FK_Booking_Session FOREIGN KEY (SessionID) REFERENCES Session(SessionID) ON DELETE CASCADE
);