USE AirGym;
GO
CREATE OR ALTER PROCEDURE AddNewMember
        @PaymentMethod VARCHAR(20),
        @Duration INT,
        @Password VARCHAR(12),
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
            DECLARE @MonthlyPrice DECIMAL(10,2);
            DECLARE @PrivateTrainer BIT;
            DECLARE @TrainerID INT = NULL;
            
            SELECT 
                @SessionsAvailable = Sessions, 
                @FreezesAvailable = FreezeDuration,
                @MonthlyPrice = MonthlyPrice,
                @PrivateTrainer = PrivateTrainer
            FROM MembershipType 
            WHERE MembershipTypeID = @MembershipTypeID;
            
            -- Assign a random trainer if the membership type includes a private trainer
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
                            SessionsAvailable, BranchID, TrainerID, FreezesAvailable, SubscriptionStatus)
            VALUES (
                @UserID, 
                @MembershipTypeID, 
                GETDATE(), 
                DATEADD(MONTH, @Duration, GETDATE()),
                @SessionsAvailable, 
                @BranchID, 
                @TrainerID, 
                @FreezesAvailable,
                'Active'
            );

            INSERT INTO Payment (Category, MemberID, PaymentMethod, Amount, Status)
            VALUES (
                'Membership', 
                @UserID, 
                @PaymentMethod, 
                (@MonthlyPrice * @Duration),
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
            DELETE FROM Users WHERE UserID = @UserID;
            
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
        @PaymentMethod VARCHAR(20) 
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
            DECLARE @MonthlyPrice DECIMAL(10,2);
            DECLARE @SessionsAvailable INT;
            SELECT 
                @MonthlyPrice = MonthlyPrice, 
                @SessionsAvailable = Sessions
            FROM MembershipType
            INNER JOIN Member ON MembershipType.MembershipTypeID = Member.MembershipTypeID
            WHERE Member.UserID = @UserID;
            UPDATE Member
            SET 
            SubscriptionEndDate = DATEADD(MONTH, @Duration, SubscriptionEndDate),
            SessionsAvailable=SessionsAvailable+@SessionsAvailable
            WHERE UserID = @UserID;

            INSERT INTO Payment (Category, MemberID, PaymentMethod, PaymentDate, Amount, Status)
            VALUES ('Membership', @UserID, @PaymentMethod, GETDATE(), (@MonthlyPrice * @Duration), 'Completed');
            
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
        @PaymentMethod VARCHAR(20)
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
            
            DECLARE @MonthlyPrice DECIMAL(10,2);
            DECLARE @Session INT;
            SELECT @MonthlyPrice = MonthlyPrice, @Session = Sessions
            FROM MembershipType
            INNER JOIN Member ON MembershipType.MembershipTypeID = Member.MembershipTypeID
            WHERE Member.UserID = @UserID;
            DECLARE @NewEndDate DATE = DATEADD(MONTH, @Duration, GETDATE());
            DECLARE @NEWSTARTDATE DATE= GETDATE();
            UPDATE Member
            SET 
            SubscriptionStartDate =@NEWSTARTDATE,
            SubscriptionEndDate = @NewEndDate,
            SessionsAvailable=SessionsAvailable+@Session,
            SubscriptionStatus='Active',
            MembershipTypeID=@MembershipTypeID
            WHERE UserID = @UserID;

            INSERT INTO Payment (Category, MemberID, PaymentMethod, Amount, Status)
            VALUES (
                'Membership', 
                @UserID, 
                @PaymentMethod, 
                (@MonthlyPrice * @Duration),
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
            SET SubscriptionStatus = 'Cancelled',SubscriptionStartDate=GetDate()
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
    END
GO
CREATE OR ALTER PROCEDURE UpdateMemberStatus    
    AS
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;
            
            UPDATE Member
            SET SubscriptionStatus = 'Expired'
            WHERE SubscriptionEndDate < GETDATE() AND SubscriptionStatus = 'Active';
            Update Member
            SET SubscriptionStatus = 'Active'
            WHERE FreezeEndDate < GETDATE() AND SubscriptionStatus = 'Frozen';
            UPDATE Session
            SET Status = 'Completed'
            WHERE DateTime < GETDATE() AND Status = 'Scheduled';

            UPDATE Session
            SET Status = 'Full'
            WHERE SessionID IN (
                SELECT SessionID
                FROM Booking
                GROUP BY SessionID
                HAVING COUNT(*) >= (SELECT MaxCapacity FROM Session WHERE SessionID = Session.SessionID)
            );

            UPDATE Booking
            SET Status = 'Cancelled'
            WHERE SessionID IN (SELECT SessionID FROM Session WHERE Status = 'Cancelled') 
            AND Status = 'Confirmed';
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
                FreezeEndDate = DATEADD(MONTH, @Duration, GETDATE())
            WHERE UserID = @UserID;
            UPDATE Member
            SET SubscriptionEndDate= DATEADD(MONTH, @Duration, GETDATE())
            WHERE UserID = @UserID;
            UPDATE Member
            SET SubscriptionStatus = 'Frozen', SubscriptionEndDate = DATEADD(MONTH, @Duration, GETDATE())
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
-- GO
-- CREATE OR ALTER PROCEDURE UnfreezeSubscription
--     @UserID INT
--     AS
--     BEGIN
--         BEGIN TRY
--             BEGIN TRANSACTION;
            
--             IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
--             BEGIN
--                 RAISERROR('Member does not exist.', 16, 1);
--                 ROLLBACK TRANSACTION;
--                 RETURN;
--             END
--             IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus IN ('Frozen'))
--             BEGIN
--                 RAISERROR('This subscription is not frozen.', 16, 1);
--                 ROLLBACK TRANSACTION;
--                 RETURN;
--             END

--             UPDATE Member
--             SET SubscriptionStatus = 'Active',
--             WHERE UserID = @UserID;
            
--             COMMIT TRANSACTION;
--         END TRY
--         BEGIN CATCH
--             ROLLBACK TRANSACTION;
--             THROW;
--         END CATCH
--     END;