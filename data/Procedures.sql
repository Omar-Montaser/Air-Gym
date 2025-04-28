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
        @BranchID INT,
        @TrainerID INT
    AS
    BEGIN
            IF NOT EXISTS (SELECT 1 FROM MembershipType WHERE MembershipTypeID = @MembershipTypeID)
            BEGIN
                RAISERROR('Invalid MembershipTypeID provided', 16, 1);
                RETURN;
            END
            
            IF NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Invalid BranchID provided', 16, 1);
                RETURN;
            END
            
            IF @TrainerID IS NOT NULL AND NOT EXISTS (SELECT 1 FROM Trainer WHERE UserID = @TrainerID)
            BEGIN
                RAISERROR('Invalid TrainerID provided', 16, 1);
                RETURN;
            END
            IF @PaymentMethod NOT IN ('Cash', 'CreditCard', 'DebitCard', 'Online')
            BEGIN
                RAISERROR('Invalid PaymentMethod provided', 16, 1);
                RETURN;
            END

            INSERT INTO Users (Password, FirstName, LastName, PhoneNumber, Gender, DateOfBirth, Role)
            VALUES (@Password, @FirstName, @LastName, @PhoneNumber, @Gender, @DateOfBirth, 'Member');
            
            DECLARE @UserID INT = SCOPE_IDENTITY();
            DECLARE @SessionsAvailable INT;
            DECLARE @MonthlyPrice DECIMAL(10,2);
            
            SELECT 
                @SessionsAvailable = Sessions, 
                @MonthlyPrice = MonthlyPrice 
            FROM MembershipType 
            WHERE MembershipTypeID = @MembershipTypeID;

            INSERT INTO Member (UserID, MembershipTypeID, SubscriptionStartDate, SubscriptionEndDate, 
                            SessionsAvailable, BranchID, TrainerID, SubscriptionStatus)
            VALUES (
                @UserID, 
                @MembershipTypeID, 
                GETDATE(), 
                DATEADD(MONTH, @Duration, GETDATE()),
                @SessionsAvailable, 
                @BranchID, 
                @TrainerID, 
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
    END

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
        @SubscriptionStatus VARCHAR(20) = NULL, 
        @TrainerID INT = NULL,
        @BranchID INT = NULL
    )
    AS
    BEGIN
            IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
            BEGIN
                RAISERROR('Member ID does not exist', 16, 1);
                RETURN;
            END

            IF @MembershipTypeID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM MembershipType WHERE MembershipTypeID = @MembershipTypeID)
            BEGIN
                RAISERROR('Invalid MembershipTypeID provided', 16, 1);
                RETURN;
            END
            IF @TrainerID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM Trainer WHERE UserID = @TrainerID)
            BEGIN
                RAISERROR('Invalid TrainerID provided', 16, 1);
                RETURN;
            END
            IF @BranchID IS NOT NULL AND 
            NOT EXISTS (SELECT 1 FROM Branch WHERE BranchID = @BranchID)
            BEGIN
                RAISERROR('Invalid BranchID provided', 16, 1);
                RETURN;
            END
            IF @SubscriptionStatus IS NOT NULL AND
            @SubscriptionStatus NOT IN ('Active', 'Expired', 'Pending', 'Cancelled', 'OnHold')
            BEGIN
                RAISERROR('Invalid SubscriptionStatus provided', 16, 1);
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
            @TrainerID IS NOT NULL OR @BranchID IS NOT NULL
            BEGIN
                UPDATE Member
                SET MembershipTypeID = COALESCE(@MembershipTypeID, MembershipTypeID),
                    SubscriptionStartDate = COALESCE(@SubscriptionStartDate, SubscriptionStartDate),
                    SubscriptionEndDate = COALESCE(@SubscriptionEndDate, SubscriptionEndDate),
                    SubscriptionStatus = COALESCE(@SubscriptionStatus, SubscriptionStatus),
                    TrainerID = COALESCE(@TrainerID, TrainerID),
                    BranchID = COALESCE(@BranchID, BranchID)
                WHERE UserID = @UserID;
                
                IF @MembershipTypeID IS NOT NULL
                BEGIN
                    UPDATE Member
                    SET SessionsAvailable = (SELECT Sessions FROM MembershipType WHERE MembershipTypeID = @MembershipTypeID)
                    WHERE UserID = @UserID;
                END
            END
    END;
CREATE OR ALTER PROCEDURE DeleteUser
        @UserID INT
    AS
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Users WHERE UserID = @UserID)
        BEGIN
            RAISERROR('User does not exist.', 16, 1);
            RETURN;
        END
        DELETE FROM Users WHERE UserID = @UserID;
    END;


CREATE OR ALTER PROCEDURE ExtendSubscription
        @UserID INT,
        @Duration INT, 
        @PaymentMethod VARCHAR(20) 
    AS
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
        BEGIN
            RAISERROR('Member does not exist.', 16, 1);
            RETURN;
        END
        IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus NOT IN ('Active'))
        BEGIN
            RAISERROR('This subscription is not active, cannot extend.', 16, 1);
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
        SET SubscriptionEndDate = DATEADD(MONTH, @Duration, SubscriptionEndDate)
        WHERE UserID = @UserID;

    INSERT INTO Payment (Category, MemberID, PaymentMethod, PaymentDate , Amount, Status)
        VALUES ('Membership', @UserID, @PaymentMethod,GETDATE(),(@MonthlyPrice * @Duration), 'Completed');
    END;

CREATE OR ALTER PROCEDURE RenewSubscription
        @UserID INT,
        @Duration INT,
        @MembershipTypeID INT,
        @PaymentMethod VARCHAR(20)
    AS
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
        BEGIN
            RAISERROR('Member does not exist.', 16, 1);
            RETURN;
        END
        IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus IN ('Active'))
        BEGIN
            RAISERROR('Subscription is already active', 16, 1);
            RETURN;
        END
        
        DECLARE @MonthlyPrice DECIMAL(10,2);
        DECLARE @SessionsAvailable INT;
        SELECT @MonthlyPrice = MonthlyPrice, @SessionsAvailable = Sessions
        FROM MembershipType
        INNER JOIN Member ON MembershipType.MembershipTypeID = Member.MembershipTypeID
        WHERE Member.UserID = @UserID;
        DECLARE @NewEndDate DATE = DATEADD(MONTH, @Duration, GETDATE());
        DECLARE @NEWSTARTDATE DATE= GETDATE();
        EXEC UpdateMember 
            @UserID = @UserID,
            @MembershipTypeID = @MembershipTypeID, 
            @SubscriptionStartDate=@NewStartDate,
            @SubscriptionEndDate = @NewEndDate,  
            @SubscriptionStatus = 'Active'; 

            INSERT INTO Payment (Category, MemberID, PaymentMethod, Amount, Status)
        VALUES (
            'Membership', 
            @UserID, 
            @PaymentMethod, 
            (@MonthlyPrice * @Duration),
            'Completed'
        );
    END;

CREATE OR ALTER PROCEDURE CancelSubscription
        @UserID INT
    AS
    BEGIN

        IF NOT EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID)
        BEGIN
            RAISERROR('Member does not exist.', 16, 1);
            RETURN;
        END

        IF EXISTS (SELECT 1 FROM Member WHERE UserID = @UserID AND SubscriptionStatus IN ('Cancelled', 'Expired'))
        BEGIN
            RAISERROR('This subscription is already cancelled or expired.', 16, 1);
            RETURN;
        END

        UPDATE Member
        SET SubscriptionStatus = 'Cancelled',SubscriptionStartDate=GetDate()
        WHERE UserID = @UserID;

        UPDATE Booking
        SET Status = 'Cancelled'
        WHERE UserID = @UserID;
    END