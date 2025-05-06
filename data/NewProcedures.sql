USE AirGym2;
--------------------------------Members--------------------------------
GO
CREATE OR ALTER PROCEDURE AddNewMember
        @PaymentMethod VARCHAR(20),
        @PaymentAmount DECIMAL(10,2),
        @Duration INT, 
        @Password VARCHAR(255),
        @FirstName VARCHAR(50),
        @LastName VARCHAR(50),
        @PhoneNumber VARCHAR(20),
        @Gender VARCHAR(10),
        @DateOfBirth DATE,
        @MembershipTypeID INT,
        @BranchID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM MembershipType WHERE MembershipTypeID = @MembershipTypeID)
            BEGIN
                RAISERROR('Invalid MembershipTypeID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Invalid BranchID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @PaymentMethod NOT IN ('Cash', 'CreditCard', 'DebitCard', 'Online')
            BEGIN
                RAISERROR('Invalid PaymentMethod provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF EXISTS (SELECT 1 FROM Users WHERE PhoneNumber = @PhoneNumber)
            BEGIN
                RAISERROR('Phone number already exists.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            INSERT INTO Users (Password, FirstName, LastName, PhoneNumber, Gender, DateOfBirth, Role)
            VALUES (@Password, @FirstName, @LastName, @PhoneNumber, @Gender, @DateOfBirth, 'Member');
            
            DECLARE @UserID INT = SCOPE_IDENTITY();
            DECLARE @SessionsAvailable INT;
            DECLARE @FreezesAvailable INT;
            DECLARE @PrivateTrainer BIT;
            DECLARE @TrainerID INT = NULL;
            
            SELECT 
                @SessionsAvailable = Sessions, 
                @FreezesAvailable = FreezeDuration * @Duration,
                @PrivateTrainer = PrivateTrainer
            FROM MembershipType 
            WHERE MembershipTypeID = @MembershipTypeID;
            IF @PrivateTrainer = 1
            BEGIN
                SELECT TOP 1 @TrainerID = UserID
                FROM Trainer
                WHERE BranchID = @BranchID AND Status = 'Active'
                ORDER BY NEWID();
                
                IF @TrainerID IS NULL
                BEGIN
                    RAISERROR('No active trainers available at the selected branch', 16, 1);
                    ROLLBACK TRANSACTION;
                    RETURN;
                END
            END

            INSERT INTO Member (UserID, MembershipTypeID, SubscriptionStartDate, SubscriptionEndDate, 
                            SessionsAvailable, BranchID, TrainerID, FreezesAvailable, SubscriptionStatus, FreezeEndDate)
            VALUES (
                @UserID, 
                @MembershipTypeID, 
                GETDATE(), 
                DATEADD(MONTH, @Duration, GETDATE()),
                @SessionsAvailable, 
                @BranchID, 
                @TrainerID, 
                @FreezesAvailable,
                'Active',
                DATEADD(DAY, -1, GETDATE())
            );

            INSERT INTO Payment (Category, MemberID, PaymentMethod, Amount, Status)
            VALUES (
                'Membership', 
                @UserID, 
                @PaymentMethod, 
                @PaymentAmount,
                'Completed'
            );
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;

GO
CREATE OR ALTER PROCEDURE UpdateMember (
        @UserID INT,
        @FirstName VARCHAR(50) = NULL,  
        @LastName VARCHAR(50) = NULL,   
        @PhoneNumber VARCHAR(15) = NULL,
        @Gender VARCHAR(10) = NULL,     
        @DateOfBirth DATE = NULL,       
        @MembershipTypeID INT = NULL,   
        @SubscriptionStartDate DATE = NULL, 
        @SubscriptionEndDate DATE = NULL,   
        @SessionsAvailable INT = NULL,
        @SubscriptionStatus VARCHAR(20) = NULL, 
        @TrainerID INT = NULL,
        @BranchID INT = NULL,
        @FreezesAvailable INT = NULL,
        @FreezeEndDate DATE = NULL
    )
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Member ID does not exist', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF @MembershipTypeID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM MembershipType WHERE MembershipTypeID = @MembershipTypeID)
            BEGIN
                RAISERROR('Invalid MembershipTypeID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF @TrainerID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM Trainer WHERE UserID = @TrainerID)
            BEGIN
                RAISERROR('Invalid TrainerID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF @BranchID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Invalid BranchID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF @SubscriptionStatus IS NOT NULL AND
            @SubscriptionStatus NOT IN ('Active', 'Expired', 'Cancelled')
            BEGIN
                RAISERROR('Invalid SubscriptionStatus provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF @FirstName IS NOT NULL OR @LastName IS NOT NULL OR 
            @PhoneNumber IS NOT NULL OR @Gender IS NOT NULL OR 
            @DateOfBirth IS NOT NULL
            BEGIN
                UPDATE Users
                SET FirstName = COALESCE(@FirstName, FirstName),
                    LastName = COALESCE(@LastName, LastName),
                    PhoneNumber = COALESCE(@PhoneNumber, PhoneNumber),
                    Gender = COALESCE(@Gender, Gender),
                    DateOfBirth = COALESCE(@DateOfBirth, DateOfBirth)
                WHERE UserID = @UserID;
            END

            IF @MembershipTypeID IS NOT NULL OR @SubscriptionStartDate IS NOT NULL OR 
            @SubscriptionEndDate IS NOT NULL OR @SubscriptionStatus IS NOT NULL OR
            @TrainerID IS NOT NULL OR @BranchID IS NOT NULL OR @SessionsAvailable is not null OR
            @FreezesAvailable IS NOT NULL OR @FreezeEndDate IS NOT NULL
            BEGIN
                UPDATE Member
                SET MembershipTypeID = COALESCE(@MembershipTypeID, MembershipTypeID),
                    SubscriptionStartDate = COALESCE(@SubscriptionStartDate, SubscriptionStartDate),
                    SubscriptionEndDate = COALESCE(@SubscriptionEndDate, SubscriptionEndDate),
                    SubscriptionStatus = COALESCE(@SubscriptionStatus, SubscriptionStatus),
                    TrainerID = COALESCE(@TrainerID, TrainerID),
                    BranchID = COALESCE(@BranchID, BranchID),
                    SessionsAvailable= COALESCE(@SessionsAvailable, SessionsAvailable),
                    FreezesAvailable= COALESCE(@FreezesAvailable, FreezesAvailable) ,
                    FreezeEndDate= COALESCE(@FreezeEndDate, FreezeEndDate)
                WHERE UserID = @UserID;
            END
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
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
        Trainer t ON m.TrainerID = t.UserID
    LEFT JOIN 
        Users tu ON t.UserID = tu.UserID  -- Join to get trainer's name
    INNER JOIN
        MembershipType mt ON m.MembershipTypeID = mt.MembershipTypeID
);
--------------------------------Trainer--------------------------------
GO
CREATE OR ALTER PROCEDURE AddNewTrainer
        @Password VARCHAR(255),
        @FirstName VARCHAR(50),
        @LastName VARCHAR(50),
        @PhoneNumber VARCHAR(20),
        @Gender VARCHAR(10),
        @DateOfBirth DATE,
        @Specialization VARCHAR(100),
        @ExperienceYears INT,
        @Salary DECIMAL(10,2),
        @BranchID INT,
        @Status VARCHAR(20) = 'Active'
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Invalid BranchID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Status NOT IN ('Active', 'OnLeave', 'Terminated')
            BEGIN
                RAISERROR('Invalid Status provided. Must be Active, OnLeave, or Terminated', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @ExperienceYears < 0
            BEGIN
                RAISERROR('Experience years cannot be negative', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Salary <= 0
            BEGIN
                RAISERROR('Salary must be greater than zero', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF EXISTS (SELECT 1 FROM Users WHERE PhoneNumber = @PhoneNumber)
            BEGIN
                RAISERROR('Phone number already exists.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            INSERT INTO Users (Password, FirstName, LastName, PhoneNumber, Gender, DateOfBirth, Role)
            VALUES (@Password, @FirstName, @LastName, @PhoneNumber, @Gender, @DateOfBirth, 'Trainer');
            
            DECLARE @UserID INT = SCOPE_IDENTITY();
            
            INSERT INTO Trainer (UserID, Specialization, ExperienceYears, Salary, BranchID, Status)
            VALUES (
                @UserID, 
                @Specialization, 
                @ExperienceYears, 
                @Salary, 
                @BranchID, 
                @Status
            );
            
            COMMIT TRANSACTION;
            SELECT @UserID AS NewTrainerID;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE UpdateTrainer
        @UserID INT,
        @PhoneNumber VARCHAR(15) = NULL,
        @ExperienceYears INT = NULL,
        @Salary DECIMAL(10,2) = NULL,
        @BranchID INT = NULL,
        @Status VARCHAR(20) = NULL,
        @Specialization VARCHAR(100) =NULL
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Trainer WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Trainer ID does not exist', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF @BranchID IS NOT NULL AND 
               NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Invalid BranchID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Status IS NOT NULL AND
               @Status NOT IN ('Active', 'OnLeave', 'Terminated')
            BEGIN
                RAISERROR('Invalid Status provided. Must be Active, OnLeave, or Terminated', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @ExperienceYears IS NOT NULL AND @ExperienceYears < 0
            BEGIN
                RAISERROR('Experience years cannot be negative', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Salary IS NOT NULL AND @Salary <= 0
            BEGIN
                RAISERROR('Salary must be greater than zero', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @PhoneNumber IS NOT NULL AND EXISTS (
                SELECT 1 FROM Users 
                WHERE PhoneNumber = @PhoneNumber AND UserID != @UserID
            )
            BEGIN
                RAISERROR('Phone number already exists.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF @PhoneNumber IS NOT NULL
            BEGIN
                UPDATE Users
                SET PhoneNumber = @PhoneNumber
                WHERE UserID = @UserID;
            END

            IF @ExperienceYears IS NOT NULL OR @Salary IS NOT NULL OR 
               @BranchID IS NOT NULL OR @Status IS NOT NULL OR @Specialization IS NOT NULL 
            BEGIN
                UPDATE Trainer
                SET ExperienceYears = COALESCE(@ExperienceYears, ExperienceYears),
                    Salary = COALESCE(@Salary, Salary),
                    BranchID = COALESCE(@BranchID, BranchID),
                    Status = COALESCE(@Status, Status),
                    Specialization =COALESCE(@Specialization,Specialization)
                WHERE UserID = @UserID;
            END
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE DeleteUser
    @UserID INT
AS
BEGIN
    BEGIN TRY
        BEGIN TRANSACTION;

        IF NOT EXISTS (SELECT 1 FROM Users WHERE UserID = @UserID)
        BEGIN
            RAISERROR('User does not exist.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END
        
        UPDATE Member
        SET TrainerID = NULL
        WHERE TrainerID = @UserID;
        
        DELETE FROM Booking WHERE UserID = @UserID;
        DELETE FROM Payment WHERE MemberID = @UserID;
        DELETE FROM Member WHERE UserID = @UserID;
        
        DELETE FROM Trainer WHERE UserID = @UserID;

        DELETE FROM Users WHERE UserID = @UserID;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
--------------------------------Branches--------------------------------
CREATE OR ALTER PROCEDURE AddNewBranch
        @Name VARCHAR(100),
        @Location VARCHAR(255),
        @Status VARCHAR(20) = 'Active',
        @AdminID INT = NULL
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF EXISTS (SELECT 1 FROM Branch WHERE Name = @Name)
            BEGIN
                RAISERROR('Branch name already exists.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Status NOT IN ('Active', 'Maintenance', 'Closed')
            BEGIN
                RAISERROR('Invalid Status provided. Must be Active, Maintenance, or Closed', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @AdminID IS NOT NULL
            BEGIN
                IF NOT EXISTS (SELECT 1 FROM Users WHERE UserID = @AdminID AND Role = 'Admin')
                BEGIN
                    RAISERROR('Invalid AdminID provided. User does not exist or is not an admin.', 16, 1);
                    ROLLBACK TRANSACTION;
                    RETURN;
                END
                
                IF EXISTS (SELECT 1 FROM Branch WHERE AdminID = @AdminID)
                BEGIN
                    RAISERROR('Admin is already assigned to another branch.', 16, 1);
                    ROLLBACK TRANSACTION;
                    RETURN;
                END
            END
            
            INSERT INTO Branch (Name, Location, OpeningDate, Status, AdminID)
            VALUES (@Name, @Location,GETDATE(), @Status, @AdminID);
            
            DECLARE @BranchID INT = SCOPE_IDENTITY();
            
            COMMIT TRANSACTION;
            SELECT @BranchID AS NewBranchID;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE UpdateBranch
        @BranchID INT,
        @Name VARCHAR(100) = NULL,
        @Location VARCHAR(255) = NULL,
        @Status VARCHAR(20) = NULL,
        @AdminID INT =NULL
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Branch ID does not exist', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF @Name IS NOT NULL AND EXISTS (
                SELECT 1 FROM Branch 
                WHERE Name = @Name AND BranchID != @BranchID
            )
            BEGIN
                RAISERROR('Branch name already exists.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF NOT EXISTS (SELECT 1 FROM USERS WHERE USERID =@AdminID)
                        BEGIN
                RAISERROR('Admin ID does not exist', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF @Status IS NOT NULL AND
               @Status NOT IN ('Active', 'Maintenance', 'Closed')
            BEGIN
                RAISERROR('Invalid Status provided. Must be Active, Maintenance, or Closed', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            UPDATE Branch
            SET Name = COALESCE(@Name, Name),
                Location = COALESCE(@Location, Location),
                Status = COALESCE(@Status, Status),
                AdminID= COALESCE(@AdminID,AdminID)
            WHERE BranchID = @BranchID;
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;

--------------------------------Equipment--------------------------------
GO
CREATE OR ALTER PROCEDURE AddNewEquipment
        @Name VARCHAR(50),
        @PurchaseDate DATE,
        @MaintenanceDate DATE = NULL,
        @Status VARCHAR(20) = 'Available',
        @BranchID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Invalid BranchID provided', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Status NOT IN ('Available', 'Maintenance', 'Retired')
            BEGIN
                RAISERROR('Invalid Status provided. Must be Available, Maintenance, or Retired', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @PurchaseDate > GETDATE()
            BEGIN
                RAISERROR('Purchase date cannot be in the future', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @MaintenanceDate IS NOT NULL AND @MaintenanceDate < @PurchaseDate
            BEGIN
                RAISERROR('Maintenance date cannot be before purchase date', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            INSERT INTO Equipment (Name, PurchaseDate, MaintenanceDate, Status, BranchID)
            VALUES (@Name, @PurchaseDate, @MaintenanceDate, @Status, @BranchID);
            
            DECLARE @EquipmentID INT = SCOPE_IDENTITY();
            
            COMMIT TRANSACTION;
            SELECT @EquipmentID AS NewEquipmentID;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE UpdateEquipment
        @EquipmentID INT,
        @Status VARCHAR(20)
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Equipment WHERE EquipmentID = @EquipmentID)
            BEGIN
                RAISERROR('Equipment ID does not exist', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Status NOT IN ('Available', 'Maintenance', 'Retired')
            BEGIN
                RAISERROR('Invalid Status provided. Must be Available, Maintenance, or Retired', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            UPDATE Equipment
            SET Status = @Status
            WHERE EquipmentID = @EquipmentID;
            
            IF @Status = 'Maintenance'
            BEGIN
                UPDATE Equipment
                SET MaintenanceDate = GETDATE()
                WHERE EquipmentID = @EquipmentID;
            END
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE ExtendSubscription
        @UserID INT,
        @Duration INT, 
        @PaymentMethod VARCHAR(20),
        @PaymentAmount DECIMAL(10,2)
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Member does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus NOT IN ('Active'))
            BEGIN
                RAISERROR('This subscription is not active, cannot extend.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            DECLARE @SessionsAvailable INT;
            DECLARE @FreezeDuration INT;
            SELECT 
                @SessionsAvailable = Sessions,
                @FreezeDuration = FreezeDuration
            FROM MembershipType
            INNER JOIN Member ON MembershipType.MembershipTypeID = Member.MembershipTypeID
            WHERE Member.UserID = @UserID;
            UPDATE Member
            SET 
            SubscriptionEndDate = DATEADD(MONTH, @Duration, SubscriptionEndDate),
            SessionsAvailable=SessionsAvailable+@SessionsAvailable,
            FreezesAvailable=FreezesAvailable+(@FreezeDuration * @Duration)
            WHERE UserID = @UserID;

            INSERT INTO Payment (Category, MemberID, PaymentMethod, PaymentDate, Amount, Status)
            VALUES ('Membership', @UserID, @PaymentMethod, GETDATE(), @PaymentAmount, 'Completed');
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
    
GO
CREATE OR ALTER PROCEDURE RenewSubscription
        @UserID INT,
        @Duration INT,
        @MembershipTypeID INT,
        @PaymentMethod VARCHAR(20),
        @PaymentAmount DECIMAL(10,2)
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Member does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus IN ('Active'))
            BEGIN
                RAISERROR('Subscription is already active', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            DECLARE @Session INT;
            DECLARE @FreezeDuration INT;
            
            SELECT 
                @Session = Sessions,
                @FreezeDuration = FreezeDuration
            FROM MembershipType
            WHERE MembershipTypeID = @MembershipTypeID;
            DECLARE @NewEndDate DATE = DATEADD(MONTH, @Duration, GETDATE());
            DECLARE @NEWSTARTDATE DATE= GETDATE();
            UPDATE Member
            SET 
            SubscriptionStartDate =@NEWSTARTDATE,
            SubscriptionEndDate = @NewEndDate,
            SessionsAvailable=SessionsAvailable+@Session,
            SubscriptionStatus='Active',
            MembershipTypeID=@MembershipTypeID,
            FreezesAvailable=@FreezeDuration * @Duration,
            FreezeEndDate=DATEADD(DAY, -1, GETDATE())
            WHERE UserID = @UserID;

            INSERT INTO Payment (Category, MemberID, PaymentMethod, Amount, Status)
            VALUES (
                'Membership', 
                @UserID, 
                @PaymentMethod, 
                @PaymentAmount,
                'Completed'
            );
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE CancelSubscription
        @UserID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Member does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus IN ('Cancelled', 'Expired'))
            BEGIN
                RAISERROR('This subscription is already cancelled or expired.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            UPDATE Member
            SET SubscriptionStatus = 'Cancelled',SubscriptionEndDate=GetDate()
            WHERE UserID = @UserID;

            UPDATE Booking
            SET Status = 'Cancelled'
            WHERE UserID = @UserID;
            
            UPDATE Payment
            SET Status = 'Cancelled'
            WHERE MemberID = @UserID 
              AND Category = 'Membership'
              AND DATEDIFF(DAY, PaymentDate, GETDATE()) <= 7; 
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END
GO
CREATE OR ALTER PROCEDURE UpdateMemberStatus    
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            -- Update expired subscriptions
            UPDATE Member
            SET SubscriptionStatus = 'Expired'
            WHERE SubscriptionEndDate < GETDATE() AND SubscriptionStatus = 'Active';
            
            -- Unfreeze subscriptions that have reached their freeze end date
            UPDATE Member
            SET SubscriptionStatus = 'Active'
            WHERE FreezeEndDate < GETDATE() AND SubscriptionStatus = 'Frozen';
            
            -- Mark completed sessions and deduct from available sessions
            UPDATE Session
            SET Status = 'Completed'
            WHERE Status = 'Scheduled'
            AND DATEADD(MINUTE, Duration, DateTime) < GETDATE();

            -- Deduct completed sessions from member's available sessions
            UPDATE m
            SET m.SessionsAvailable = m.SessionsAvailable - completed_sessions.count
            FROM Member m
            JOIN (
                SELECT b.UserID, COUNT(*) as count
                FROM Booking b
                JOIN Session s ON b.SessionID = s.SessionID
                WHERE s.Status = 'Completed'
                AND b.Status = 'Confirmed'
                GROUP BY b.UserID
            ) completed_sessions ON m.UserID = completed_sessions.UserID;

            -- Update booking status for completed sessions
            UPDATE b
            SET b.Status = 'Completed'
            FROM Booking b
            JOIN Session s ON b.SessionID = s.SessionID
            WHERE s.Status = 'Completed'
            AND b.Status = 'Confirmed';

            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE FreezeSubscription
    @UserID INT,
    @Duration INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Member does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus IN ('Cancelled', 'Expired'))
            BEGIN
                RAISERROR('This subscription is already cancelled or expired.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            DECLARE @FreezesAvailable INT;
            SELECT @FreezesAvailable = FreezesAvailable
            FROM Member
            WHERE UserID = @UserID;
            
            IF @FreezesAvailable < @Duration
            BEGIN
                RAISERROR('Not enough freezes available for the requested duration.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            UPDATE Member
            SET FreezesAvailable = FreezesAvailable - @Duration,
                FreezeEndDate = DATEADD(DAY, @Duration, GETDATE()),
                SubscriptionEndDate = DATEADD(DAY, @Duration, SubscriptionEndDate),
                SubscriptionStatus = 'Frozen'
            WHERE UserID = @UserID;

            UPDATE Booking
            SET Status = 'Cancelled'
            WHERE UserID = @UserID;
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE UnfreezeSubscription
    @UserID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Member does not exist.', 16, 1);
                ROLLBACK TRANSACTION;   
                RETURN;
            END
            IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus NOT IN ('Frozen'))
            BEGIN
                RAISERROR('This subscription is not frozen, cannot unfreeze.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            DECLARE @RemainingDays INT;
            SELECT @RemainingDays = DATEDIFF(DAY, GETDATE(), FreezeEndDate)
            FROM Member
            WHERE UserID = @UserID;
            
            IF @RemainingDays < 0
                SET @RemainingDays = 0;
                
            UPDATE Member
            SET FreezesAvailable = FreezesAvailable + @RemainingDays,
                FreezeEndDate = DATEADD(DAY, -1, GETDATE()),
                SubscriptionStatus = 'Active',
                SubscriptionEndDate = DATEADD(DAY, -(@RemainingDays), SubscriptionEndDate)
            WHERE UserID = @UserID;
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE AddPayment
        @MemberID INT,
        @Category VARCHAR(50),
        @PaymentMethod VARCHAR(20),
        @Amount DECIMAL(10,2)
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;

            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @MemberID)
            BEGIN
                RAISERROR('Member does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF @Category NOT IN ('Membership','Expense','Other') OR
            @PaymentMethod NOT IN ('CreditCard', 'DebitCard', 'Cash', 'BankTransfer', 'Other')
            BEGIN
                RAISERROR('Invalid category or payment method.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            INSERT INTO Payment (Category, MemberID, PaymentMethod, PaymentDate, Amount, Status)
            VALUES (@Category, @MemberID, @PaymentMethod, GETDATE(), @Amount, 'Completed');

            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
END;
GO
CREATE OR ALTER PROCEDURE CancelPayment
        @PaymentID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;

            IF NOT EXISTS (SELECT 1 FROM Payment WHERE PaymentID = @PaymentID)
            BEGIN
                RAISERROR('Payment not found.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF EXISTS (
                SELECT 1 FROM Payment 
                WHERE PaymentID = @PaymentID 
                AND DATEDIFF(DAY, PaymentDate, GETDATE()) > 7
            )
            BEGIN
                RAISERROR('Only payments made within 7 days can be cancelled.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            UPDATE Payment SET Status = 'Cancelled' WHERE PaymentID = @PaymentID;

            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
END;
Go 
CREATE OR ALTER PROCEDURE UpdateSessionStatus
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            --check which session is full
            UPDATE Session
            SET Status = 'Full'
            WHERE SessionID IN (
                SELECT s.SessionID
                FROM Session s
                JOIN (
                    SELECT SessionID, COUNT(*) AS BookingCount
                    FROM Booking
                    WHERE Status = 'Confirmed'
                    GROUP BY SessionID
                ) b ON s.SessionID = b.SessionID
                WHERE b.BookingCount >= s.MaxCapacity
                AND s.Status = 'Scheduled'
            );
            --check which session went past deadline
            UPDATE Session
            SET Status = 'Completed'
            WHERE Status = 'Scheduled'
            AND DATEADD(MINUTE, Duration, DateTime) < GETDATE();
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE AddSession
        @TrainerID INT,
        @BranchID INT,
        @SessionType VARCHAR(20),
        @MaxCapacity INT,
        @DateTime DATETIME,
        @Duration INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            -- Validate trainer exists
            IF NOT EXISTS (SELECT 1 FROM Trainer WHERE UserID = @TrainerID)
            BEGIN
                RAISERROR('Trainer does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            -- Validate branch exists
            IF NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Branch does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            -- Validate session type
            IF @SessionType NOT IN ('Yoga', 'HIIT', 'Cycling', 'Pilates', 'Boxing', 'Zumba')
            BEGIN
                RAISERROR('Invalid session type.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            -- Validate capacity
            IF @MaxCapacity <= 0
            BEGIN
                RAISERROR('Maximum capacity must be greater than zero.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            -- Validate duration
            IF @Duration <= 0
            BEGIN
                RAISERROR('Duration must be greater than zero.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            -- Validate date time
            IF @DateTime < GETDATE()
            BEGIN
                RAISERROR('Session date and time must be in the future.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            -- Check if trainer is already scheduled for another session at the same time
            IF EXISTS (
                SELECT 1 
                FROM Session 
                WHERE TrainerID = @TrainerID 
                AND Status IN ('Scheduled', 'Full')
                AND (
                    (@DateTime BETWEEN DateTime AND DATEADD(MINUTE, Duration, DateTime))
                    OR
                    (DATEADD(MINUTE, @Duration, @DateTime) BETWEEN DateTime AND DATEADD(MINUTE, Duration, DateTime))
                )
            )
            BEGIN
                RAISERROR('Trainer is already scheduled for another session at this time.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            INSERT INTO Session (TrainerID, BranchID, SessionType, MaxCapacity, DateTime, Duration, Status)
            VALUES (@TrainerID, @BranchID, @SessionType, @MaxCapacity, @DateTime, @Duration, 'Scheduled');

            SELECT SCOPE_IDENTITY() AS SessionID;

            EXEC UpdateSessionStatus;

            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
END;
GO
CREATE OR ALTER PROCEDURE UpdateSession
        @SessionID INT,
        @TrainerID INT = NULL,
        @BranchID INT = NULL,
        @SessionType VARCHAR(20) = NULL,
        @MaxCapacity INT = NULL,
        @DateTime DATETIME = NULL,
        @Duration INT = NULL,
        @Status VARCHAR(20) = NULL
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Session WHERE SessionID = @SessionID)
            BEGIN
                RAISERROR('Session does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @TrainerID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM Trainer WHERE UserID = @TrainerID)
            BEGIN
                RAISERROR('Trainer does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
        
            IF @BranchID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Branch does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @SessionType IS NOT NULL AND 
            @SessionType NOT IN ('Yoga', 'HIIT', 'Cycling', 'Pilates', 'Boxing', 'Zumba')
            BEGIN
                RAISERROR('Invalid session type.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @MaxCapacity IS NOT NULL AND @MaxCapacity <= 0
            BEGIN
                RAISERROR('Maximum capacity must be greater than zero.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Duration IS NOT NULL AND @Duration <= 0
            BEGIN
                RAISERROR('Duration must be greater than zero.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            IF @Status IS NOT NULL AND 
            @Status NOT IN ('Scheduled', 'Completed', 'Cancelled', 'Full')
            BEGIN
                RAISERROR('Invalid session status.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            UPDATE Session
            SET TrainerID = COALESCE(@TrainerID, TrainerID),
                BranchID = COALESCE(@BranchID, BranchID),
                SessionType = COALESCE(@SessionType, SessionType),
                MaxCapacity = COALESCE(@MaxCapacity, MaxCapacity),
                DateTime = COALESCE(@DateTime, DateTime),
                Duration = COALESCE(@Duration, Duration),
                Status = COALESCE(@Status, Status)
            WHERE SessionID = @SessionID;
            
            EXEC UpdateSessionStatus;
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE CancelSession
    @SessionID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Session WHERE SessionID = @SessionID)
            BEGIN
                RAISERROR('Session does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF EXISTS (SELECT 1 FROM Booking WHERE SessionID = @SessionID)
            BEGIN
                UPDATE Session
                SET Status = 'Cancelled'
                WHERE SessionID = @SessionID;
                
                UPDATE Booking
                SET Status = 'Cancelled'
                WHERE SessionID = @SessionID;
            END
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
END;
GO
CREATE OR ALTER PROCEDURE DeleteSession
    @SessionID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;

            IF NOT EXISTS (SELECT 1 FROM Session WHERE SessionID = @SessionID)
            BEGIN
                RAISERROR('Session does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            IF EXISTS (SELECT 1 FROM Booking WHERE SessionID = @SessionID)
            BEGIN
                RAISERROR('Session has bookings, cannot delete.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            DELETE FROM Session WHERE SessionID = @SessionID;
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
END;
GO
CREATE OR ALTER PROCEDURE UpdateBookingStatus
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            UPDATE Booking
            SET Status = 'Cancelled'
            FROM Booking b
            JOIN Session s ON b.SessionID = s.SessionID
            WHERE s.Status = 'Cancelled'
            AND b.Status = 'Confirmed';

            -- Delete bookings for completed sessions
            DELETE b
            FROM Booking b
            JOIN Session s ON b.SessionID = s.SessionID
            WHERE s.Status = 'Completed'
            AND b.Status = 'Confirmed';

            -- Update sessions that are marked as Full but have available capacity
            UPDATE s
            SET s.Status = 'Scheduled'
            FROM Session s
            WHERE s.Status = 'Full'
            AND (SELECT COUNT(*) FROM Booking b WHERE b.SessionID = s.SessionID AND b.Status = 'Confirmed') < s.MaxCapacity;
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE CreateBooking
        @UserID INT,
        @SessionID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            -- Validate member exists and has active subscription
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus = 'Active')
            BEGIN
                RAISERROR('Member does not exist or subscription is not active.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            -- Validate session exists
            IF NOT EXISTS (SELECT 1 FROM Session WHERE SessionID = @SessionID)
            BEGIN
                RAISERROR('Session does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            -- Check if session is available
            IF EXISTS (SELECT 1 FROM Session WHERE SessionID = @SessionID AND Status IN ('Cancelled', 'Full', 'Completed'))
            BEGIN
                RAISERROR('Session is not available for booking.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF EXISTS (SELECT 1 FROM Booking WHERE UserID = @UserID AND SessionID = @SessionID AND Status = 'Confirmed')
            BEGIN
                RAISERROR('User already has a booking for this session.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SessionsAvailable > 0)
            BEGIN
                RAISERROR('No available sessions remaining.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            -- Insert the booking
            INSERT INTO Booking (UserID, SessionID, Status, BookingDate)
            VALUES (@UserID, @SessionID, 'Confirmed', GETDATE());
            
            -- Check if session is now full
            DECLARE @CurrentBookings INT;
            DECLARE @MaxCapacity INT;
            
            SELECT @MaxCapacity = MaxCapacity 
            FROM Session 
            WHERE SessionID = @SessionID;
            
            SELECT @CurrentBookings = COUNT(*) 
            FROM Booking 
            WHERE SessionID = @SessionID AND Status = 'Confirmed';
            
            IF @CurrentBookings >= @MaxCapacity
            BEGIN
                UPDATE Session
                SET Status = 'Full'
                WHERE SessionID = @SessionID;
            END
            
            -- Return the new booking ID
            SELECT SCOPE_IDENTITY() AS BookingID;
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;
GO
CREATE OR ALTER PROCEDURE CancelBooking
    @BookingID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;  

            IF NOT EXISTS (SELECT 1 FROM Booking WHERE BookingID = @BookingID)
            BEGIN
                RAISERROR('Booking does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END

            UPDATE Booking
            SET Status = 'Cancelled'
            WHERE BookingID = @BookingID;
            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;


GO
CREATE OR ALTER PROCEDURE DeleteBooking
        @BookingID INT
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            IF NOT EXISTS (SELECT 1 FROM Booking WHERE BookingID = @BookingID)
            BEGIN
                RAISERROR('Booking does not exist.', 16, 1);
                ROLLBACK TRANSACTION;
                RETURN;
            END
            
            DECLARE @SessionID INT;
            SELECT TOP 1 @SessionID = SessionID FROM Booking WHERE BookingID = @BookingID;
            
            DELETE FROM Booking WHERE BookingID = @BookingID;
            
            IF EXISTS (SELECT 1 FROM Session WHERE SessionID = @SessionID AND Status = 'Full')
            BEGIN
                UPDATE Session
                SET Status = 'Scheduled'
                WHERE SessionID = @SessionID;
            END

            IF EXISTS (
                SELECT 1 FROM Session s
                WHERE s.SessionID = @SessionID AND s.Status = 'Full'
                AND (SELECT COUNT(*) FROM Booking b WHERE b.SessionID = @SessionID AND b.Status = 'Confirmed') < s.MaxCapacity
            )
            BEGIN
                UPDATE Session
                SET Status = 'Scheduled'
                WHERE SessionID = @SessionID;
            END

            
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION;
            THROW;
        END CATCH
    END;