Use AirGym;
---------------------------------------------Member Functions-------------------------------------------------
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
GO
CREATE OR ALTER FUNCTION CountAllActiveMembers()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) 
        FROM Member 
        WHERE SubscriptionStatus = 'Active';
        RETURN @Count;
    END;
    GO

    CREATE OR ALTER FUNCTION CountAllActiveTrainers()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) 
        FROM Trainer
        WHERE Status = 'Active';
        RETURN @Count;
    END;
USE AirGym;
GO
CREATE OR ALTER FUNCTION GetMemberDetails()
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            u.UserID, 
            CAST(u.FirstName + ' ' + u.LastName AS NVARCHAR(100)) AS FullName,
            u.PhoneNumber, 
            DATEDIFF(YEAR, u.DateOfBirth, GETDATE()) AS Age,
            u.Gender,
            b.Name AS BranchName,
            ISNULL(CAST(tu.FirstName + ' ' + tu.LastName AS NVARCHAR(100)), 'No Trainer Assigned') AS Trainer,
            mt.Name AS Membership,
            m.SubscriptionStatus,
            m.SubscriptionEndDate,
            m.SessionsAvailable,
            m.FreezesAvailable,
            m.FreezeEndDate
        FROM 
            Member m
        INNER JOIN 
            Users u ON m.UserID = u.UserID
        LEFT JOIN 
            Branch b ON m.BranchID = b.BranchID
        LEFT JOIN 
            Users tu ON m.TrainerID = tu.UserID
        LEFT JOIN
            MembershipType mt ON m.MembershipTypeID = mt.MembershipTypeID
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
CREATE OR ALTER FUNCTION GetAllMemberBookings(@UserID INT)
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            b.BookingID,
            b.UserID,
            b.SessionID,
            b.Status AS BookingStatus,
            b.BookingDate,
            s.SessionType,
            s.DateTime,
            s.Duration,
            s.Status AS SessionStatus,
            tUser.FirstName + ' ' + tUser.LastName AS TrainerName,
            br.Name AS BranchName
        FROM Booking b
        JOIN Session s ON s.SessionID = b.SessionID
        JOIN Trainer t ON t.UserID = s.TrainerID
        JOIN Users tUser ON tUser.UserID = t.UserID
        JOIN Branch br ON br.BranchID = s.BranchID
        WHERE b.UserID = @UserID AND b.Status = 'Confirmed'
    );
    GO

    CREATE OR ALTER FUNCTION CountAllMembers()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) FROM Member;
        RETURN @Count;
    END;
GO
---------------------------------------------Trainer Functions-------------------------------------------------
CREATE OR ALTER FUNCTION GetTrainerDetails()
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            u.UserID, 
            u.FirstName + ' ' + u.LastName AS TrainerName, 
            u.PhoneNumber, 
            u.Gender,
            t.Specialization,
            t.ExperienceYears,
            t.Salary,
            b.Name AS BranchName,
            t.Status
        FROM 
            Trainer t
        INNER JOIN 
            Users u ON t.UserID = u.UserID
        LEFT JOIN 
            Branch b ON t.BranchID = b.BranchID
    );
GO
CREATE OR ALTER FUNCTION CountAllTrainers()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) FROM Trainer;
        RETURN @Count;
    END;

GO
---------------------------------------------Admin & BranchFunctions-------------------------------------------------
CREATE OR ALTER FUNCTION CountAllAdmins()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) 
        FROM Users
        WHERE Role = 'Admin';
        RETURN @Count;
    END;
GO
CREATE OR ALTER FUNCTION GetMostDenseBranch()
    RETURNS INT
    AS
    BEGIN
        DECLARE @BranchID INT;
        
        SELECT TOP 1 @BranchID = b.BranchID
        FROM 
            Branch b
        LEFT JOIN 
            Member m ON b.BranchID = m.BranchID
        WHERE
            b.Status = 'Active'
        GROUP BY 
            b.BranchID
        ORDER BY 
            COUNT(m.UserID) DESC;
            
        RETURN @BranchID;
    END;
GO
CREATE OR ALTER FUNCTION GetBestIncomeBranch()
    RETURNS INT
    AS
    BEGIN
        DECLARE @BestBranchID INT;
        
        SELECT TOP 1 @BestBranchID = b.BranchID
        FROM 
            Branch b
        JOIN 
            Member m ON b.BranchID = m.BranchID
        JOIN 
            Payment p ON m.UserID = p.MemberID
        WHERE 
            p.Status = 'Completed'
            AND p.Category = 'Membership'
            AND b.Status = 'Active'
        GROUP BY 
            b.BranchID
        ORDER BY 
            SUM(p.Amount) DESC;
            
        RETURN @BestBranchID;
    END;
GO
CREATE OR ALTER FUNCTION GetWorstIncomeBranch()
    RETURNS INT
    AS
    BEGIN
        DECLARE @WorstBranchID INT;
        
        SELECT TOP 1 @WorstBranchID = b.BranchID
        FROM 
            Branch b
        LEFT JOIN 
            Member m ON b.BranchID = m.BranchID
        LEFT JOIN 
            Payment p ON m.UserID = p.MemberID AND p.Status = 'Completed' AND p.Category = 'Membership'
        WHERE 
            b.Status = 'Active'
        GROUP BY 
            b.BranchID
        ORDER BY 
            ISNULL(SUM(p.Amount), 0) ASC;
            
        RETURN @WorstBranchID;
    END;
GO
CREATE OR ALTER FUNCTION GetBranchesInMaintenance()
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT *
        FROM 
            Branch
        WHERE 
            Status = 'Maintenance'
    );
GO
CREATE OR ALTER FUNCTION CountAllBranches()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) FROM Branch;
        RETURN @Count;
    END;

---------------------------------------------EquipmentFunctions-------------------------------------------------
GO
CREATE OR ALTER FUNCTION GetAllAvailableEquipment()
    RETURNS TABLE
    AS
    RETURN
    (
        SELECT 
            e.*
        FROM 
            Equipment e
        JOIN 
            Branch b ON e.BranchID = b.BranchID
        WHERE 
            e.Status = 'Available'
    );
GO
CREATE OR ALTER FUNCTION CountAllEquipment()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) FROM Equipment;
        RETURN @Count;
    END;
GO
CREATE OR ALTER FUNCTION CountAllAvailableEquipment()
    RETURNS INT
    AS
    BEGIN
        DECLARE @Count INT;
        SELECT @Count = COUNT(*) 
        FROM Equipment 
        WHERE Status = 'Available';
        RETURN @Count;
    END;
---------------------------------------------Membership Functions-------------------------------------------------
GO
CREATE OR ALTER FUNCTION GetBestSellingMembership()
    RETURNS INT
    AS
    BEGIN
        DECLARE @BestSellingMembershipID INT;
        
        SELECT TOP 1 @BestSellingMembershipID = mt.MembershipTypeID
        FROM 
            MembershipType mt
        JOIN 
            Member m ON mt.MembershipTypeID = m.MembershipTypeID
        JOIN 
            Payment p ON m.UserID = p.MemberID
        WHERE 
            p.Status = 'Completed'
            AND p.Category = 'Membership'
        GROUP BY 
            mt.MembershipTypeID, mt.Name, mt.MonthlyPrice
        ORDER BY 
            COUNT(m.UserID) DESC;
            
        RETURN @BestSellingMembershipID;
    END;
---------------------------------------------Business Functions-------------------------------------------------
GO
CREATE OR ALTER FUNCTION GetTotalExpenses()
    RETURNS DECIMAL(10,2)
    AS
    BEGIN
        DECLARE @TotalExpenses DECIMAL(10,2);
        SELECT @TotalExpenses = ISNULL(SUM(Amount), 0)
        FROM Payment
        WHERE Category = 'Expense' AND Status = 'Completed';
        RETURN @TotalExpenses;
    END;
GO
CREATE OR ALTER FUNCTION GetTotalRevenue()
    RETURNS DECIMAL(10,2)
    AS
    BEGIN
        DECLARE @TotalRevenue DECIMAL(10,2);
        SELECT @TotalRevenue = ISNULL(SUM(Amount), 0)
        FROM Payment
        WHERE Category = 'Membership' AND Status = 'Completed';
        RETURN @TotalRevenue;
    END;
GO
CREATE OR ALTER FUNCTION GetNetProfit()
    RETURNS DECIMAL(10,2)
    AS
    BEGIN
        DECLARE @Revenue DECIMAL(10,2);
        DECLARE @Expenses DECIMAL(10,2);
        
        SELECT @Revenue = ISNULL(SUM(Amount), 0)
        FROM Payment
        WHERE Category = 'Membership' AND Status = 'Completed';
        
        SELECT @Expenses = ISNULL(SUM(Amount), 0)
        FROM Payment
        WHERE Category = 'Expense' AND Status = 'Completed';
        
        RETURN @Revenue - @Expenses;
    END;
GO
---------------------------------------------SessionFunctions-------------------------------------------------
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