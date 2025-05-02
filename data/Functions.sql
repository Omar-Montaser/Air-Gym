Use AirGym;
go
CREATE OR ALTER FUNCTION GetMemberByPhoneNumber(@PhoneNumber VARCHAR(15))
RETURNS TABLE
AS
RETURN
(
SELECT u.UserID, u.FirstName, u.LastName, u.Password, u.PhoneNumber, u.Gender, u.DateOfBirth, 
       m.MembershipTypeID, m.BranchID, m.TrainerID, m.SubscriptionStartDate, m.SubscriptionEndDate, 
       m.SessionsAvailable, m.SubscriptionStatus, m.FreezesAvailable, m.FreezeEndDate 
    FROM Users u JOIN Member m ON u.UserID = m.UserID 
    WHERE u.PhoneNumber = @PhoneNumber
);
GO
CREATE OR ALTER FUNCTION GetMemberDetails(@UserID INT)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        u.UserID, 
        u.FirstName+' '+u.LastName AS MemberName, 
        u.PhoneNumber, 
        b.Name,
        t.FirstName+' '+t.LastName AS TrainerName,
        mt.Name AS MembershipName,
        m.SubscriptionStartDate,
        m.SubscriptionEndDate,
        m.SessionsAvailable
    FROM 
        Member m
    INNER JOIN 
        Users u ON m.UserID = u.UserID
    LEFT JOIN 
        Branch b ON m.BranchID = b.BranchID
    LEFT JOIN 
        Users t ON m.TrainerID = t.UserID
    INNER JOIN
        MembershipType mt ON m.MembershipTypeID = mt.MembershipTypeID
    WHERE 
        m.UserID = @UserID
);
go
CREATE OR ALTER FUNCTION CheckMemberActive(@UserID INT)
RETURNS BIT
AS
BEGIN
    DECLARE @IsActive BIT = 0;
    IF EXISTS (
        SELECT 1 FROM Member
        WHERE UserID = @UserID
        AND SubscriptionStatus = 'Active'
    )
    SET @IsActive = 1;
    RETURN @IsActive;
END;
go
CREATE OR ALTER FUNCTION GetActiveMembers()
RETURNS TABLE
AS
RETURN
(
    SELECT 
        u.UserID, 
        u.FirstName+' '+u.LastName AS MemberName, 
        u.PhoneNumber, 
        b.Name,
        t.FirstName+' '+t.LastName AS TrainerName,
        mt.Name AS MembershipName,
        m.SubscriptionStartDate,
        m.SubscriptionEndDate,
        m.SessionsAvailable
    FROM 
        Member m
    INNER JOIN 
        Users u ON m.UserID = u.UserID
    LEFT JOIN 
        Branch b ON m.BranchID = b.BranchID
    LEFT JOIN 
        Users t ON m.TrainerID = t.UserID
    INNER JOIN
        MembershipType mt ON m.MembershipTypeID = mt.MembershipTypeID
    WHERE 
        m.UserID = u.UserID and dbo.CheckMemberActive(m.UserID)=1
);
go
CREATE OR ALTER FUNCTION GetMembershipHistory(@UserID INT)
RETURNS TABLE
AS
RETURN
(
    SELECT 
        p.PaymentID,
        p.PaymentDate,
        p.Amount,
        p.PaymentMethod,
        p.Status
    FROM 
        Payment p
    WHERE 
        p.MemberID = @UserID
        AND p.Category = 'Membership'

);
go
CREATE OR ALTER FUNCTION GetFrozenMembers()
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            u.UserID, u.FirstName, u.LastName, u.PhoneNumber, m.SubscriptionEndDate
        FROM 
            Member m
        INNER JOIN 
            Users u ON m.UserID = u.UserID
        WHERE 
            m.SubscriptionStatus = 'Frozen'
    );
go
CREATE OR ALTER FUNCTION GetMembershipTypePrice(@MembershipTypeID INT)
    RETURNS DECIMAL(10,2)
    AS
    BEGIN
        DECLARE @Price DECIMAL(10,2);
        SELECT @Price = MonthlyPrice
        FROM MembershipType
        WHERE MembershipTypeID = @MembershipTypeID;
        RETURN @Price;
    END
go
CREATE OR ALTER FUNCTION GetMembershipTypeByName (@Name VARCHAR(100))
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT MembershipTypeID, Name, MonthlyPrice, Sessions
        FROM MembershipType
        WHERE Name = @Name
    );
go
CREATE OR ALTER FUNCTION IsGuest (@UserID INT)
    RETURNS BIT
    AS
    BEGIN
        DECLARE @Result BIT;

        IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            SET @Result = 0; 
        ELSE
            SET @Result = 1;

        RETURN @Result;
    END;
go
CREATE OR ALTER FUNCTION GetExpiredMembers()
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            u.UserID, u.FirstName, u.LastName, u.PhoneNumber, m.SubscriptionEndDate
        FROM 
            Member m
        INNER JOIN 
            Users u ON m.UserID = u.UserID
        WHERE 
            m.SubscriptionEndDate < GETDATE()
            AND m.SubscriptionStatus = 'Expired'
    );
go
CREATE OR ALTER FUNCTION GetMembersByBranch(@BranchID INT)
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            u.UserID, u.FirstName, u.LastName, u.PhoneNumber, 
            m.SubscriptionStartDate, m.SubscriptionEndDate, m.SubscriptionStatus,
            mt.Name AS MembershipType
        FROM 
            Member m
        INNER JOIN 
            Users u ON m.UserID = u.UserID
        INNER JOIN
            MembershipType mt ON m.MembershipTypeID = mt.MembershipTypeID
        WHERE 
            m.BranchID = @BranchID
    );
    go
    CREATE OR ALTER FUNCTION GetSessionBookingDetails(@UserID INT)
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            sb.BookingID, 
            s.SessionType,
            s.DateTime,
            s.Duration,
            t.FirstName + ' ' + t.LastName AS TrainerName,
            b.Name
        FROM 
            Booking sb
        INNER JOIN 
            Session s ON sb.SessionID = s.SessionID
        INNER JOIN
            Users t ON s.TrainerID = t.UserID
        INNER JOIN
            Branch b ON s.BranchID = b.BranchID
        WHERE 
            sb.UserID = @UserID
    );

go
CREATE OR ALTER FUNCTION GetEarliestSessionByType(@SessionType VARCHAR(20))
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT TOP 1
            s.SessionID,
            s.TrainerID,
            s.BranchID,
            s.SessionType,
            s.MaxCapacity,
            s.DateTime,
            s.Duration,
            s.Status,
            u.FirstName + ' ' + u.LastName AS TrainerName,
            b.Name AS BranchName
        FROM 
            Session s
        INNER JOIN
            Users u ON s.TrainerID = u.UserID
        INNER JOIN
            Branch b ON s.BranchID = b.BranchID
        WHERE 
            s.SessionType = @SessionType
            AND s.Status NOT IN ('Full', 'Completed', 'Cancelled')
            AND s.DateTime > GETDATE()
        ORDER BY
            s.DateTime ASC
    );
Go 
CREATE OR ALTER FUNCTION GetAllMemberBookings(@UserID INT)
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT * FROM Booking WHERE UserID = @UserID and Status = 'Confirmed'
    );
