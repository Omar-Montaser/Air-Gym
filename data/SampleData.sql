USE AirGym
INSERT INTO Users (Password, FirstName, LastName, PhoneNumber, Gender, DateOfBirth, Role) VALUES
('adminpwd1', 'Adam', 'Mansour', '01011235888', 'Male', '1985-02-25', 'Admin'),    
('adminpwd2', 'Mona', 'Salah', '01033445566', 'Female', '1987-06-12', 'Admin'),    
('adminpwd3', 'Tamer', 'Kamal', '01077889900', 'Male', '1982-09-10', 'Admin'),     
('adminpwd4', 'Nour', 'Fathy', '01099887766', 'Female', '1990-11-05', 'Admin'),    
('adminpwd5', 'Omar', 'Montaser', '01066554422', 'Male', '1988-03-17', 'Admin'),   

('trainerpwd1', 'Ahmed', 'Hassan', '01012345678', 'Male', '1990-05-20', 'Trainer'),  
('trainerpwd2', 'Sara', 'Ali', '01087654321', 'Female', '1992-08-15', 'Trainer'),   
('trainerpwd3', 'Youssef', 'Douban', '01099887755', 'Male', '1993-10-30', 'Trainer'), 
('trainerpwd4', 'Mohamed', 'Fotoh', '01066558877', 'Male', '1991-07-07', 'Trainer'), 

('memberpwd1', 'Mohamed', 'Youssef', '01011223344', 'Male', '1995-12-10', 'Member'),
('memberpwd2', 'Laila', 'Kamal', '01044332211', 'Female', '1998-03-22', 'Member'),  
('memberpwd3', 'Ahmed', 'Naguib', '01055667788', 'Male', '1997-01-15', 'Member');

INSERT INTO Branch (Name, Location, PhoneNumber, OpeningDate, Status, AdminID) VALUES
('AirGym Haram', '44 Haram St., Below National Bank of Egypt, Giza', '01151928888', '2018-01-01', 'Active', 1),
('AirGym 6th of October', 'Central Spine, Magda Sq., Inside La Cite Mall, 6th of October, Giza', '01101188884', '2019-03-15', 'Active', 2),
('AirGym Maadi', '1 Block 61, Badr Towers, 10th Sector, Near To Marasy Seafood, Maadi, Cairo', '01100000537', '2020-06-10', 'Active', 3),
('AirGym Nasr City', '5 Al Nasr Street, Nasr City, Cairo', '01111148811', '2021-09-20', 'Active', 4),
('AirGym Heliopolis', '104 Othman Ibn Affan St., Next to QNB Bank & Faragalla Market, Heliopolis, Cairo', '01122224376', '2022-11-05', 'Active', 5);

 
INSERT INTO Trainer (UserID, Specialization, ExperienceYears, Salary, BranchID, Status) VALUES
(6, 'Strength Training', 5, 8000.00, 1, 'Active'),
(7, 'Yoga & Pilates', 3, 7000.00, 4, 'Active'),
(8, 'CrossFit', 4, 7500.00, 3, 'Active'),
(9, 'Boxing & MMA', 6, 8500.00, 2, 'Active');
INSERT INTO MembershipType (Name, Description, AccessLevel, MonthlyPrice, Sessions, PrivateTrainer, FreezeDuration, InBody, ColorHex) VALUES
('Basic', 'Access to gym equipment during staffed hours.', 1, 500.00, 0, 0, 0, 0, '#FF5733'),
('Standard', 'Includes group classes and extended hours access.', 2, 750.00, 5, 0, 1, 1, '#33C1FF'),
('Premium', 'All-access pass with personal trainer sessions.', 3, 1200.00, 10, 1, 2, 1, '#8D33FF'),
('VIP', 'Exclusive access to all facilities and events with unlimited personal trainer sessions.', 4, 2000.00, 20, 1, 3, 1, '#FFD700');

INSERT INTO Member (UserID, MembershipTypeID, SubscriptionStartDate, SubscriptionEndDate, SessionsAvailable, BranchID, TrainerID, SubscriptionStatus) VALUES
(10, 1, '2025-01-01', '2025-03-31', 0, 1, 6, 'Active'),
(11, 3, '2025-02-15', '2025-05-15', 10, 3, 7, 'Active'),
(12, 2, '2025-03-01', '2025-06-01', 5, 2, 8, 'Active');

INSERT INTO Equipment (Name, PurchaseDate, MaintenanceDate, Status, BranchID) VALUES
('Treadmill', '2020-01-15', '2025-01-01', 'Available', 1),
('Bench Press', '2019-05-10', '2025-01-07', 'Available', 1),
('Elliptical Machine', '2021-07-22', '2024-07-22', 'Maintenance', 2),
('Dumbbell Set', '2018-03-05', '2025-01-04', 'Available', 3),
('Rowing Machine', '2022-04-18', '2025-01-02', 'Available', 4),
('Squat Rack', '2021-12-30', '2025-01-02', 'Available', 5);

INSERT INTO Session (TrainerID, BranchID, SessionType, MaxCapacity, DateTime, Duration, Status) VALUES
(6, 1, 'HIIT', 20, '2025-05-10 18:00:00', 60, 'Scheduled'),
(7, 3, 'Yoga', 15, '2025-05-11 10:00:00', 45, 'Scheduled'),
(8, 2, 'Cycling', 25, '2025-05-12 17:00:00', 50, 'Scheduled'),
(9, 4, 'Boxing', 10, '2025-05-13 19:30:00', 90, 'Scheduled'),
(6, 5, 'Zumba', 30, '2025-05-14 16:00:00', 60, 'Scheduled');

INSERT INTO Payment (Category, MemberID, PaymentMethod, PaymentDate, Amount, Status) VALUES
('Membership', 10, 'CreditCard', '2025-01-01 12:00:00', 500.00, 'Completed'),
('Membership', 11, 'DebitCard', '2025-02-15 15:00:00', 750.00, 'Completed'),
('Membership', 12, 'Cash', '2025-03-01 11:30:00', 1200.00, 'Completed'),
('Session', 10, 'CreditCard', '2025-05-01 17:00:00', 100.00, 'Pending'),
('Session', 11, 'Cash', '2025-05-02 17:30:00', 100.00, 'Completed');

INSERT INTO Booking (UserID, SessionID, PaymentID, Status, BookingDate) VALUES
(10, 1, 4, 'Confirmed', '2025-05-01 12:00:00'),
(11, 2, 5, 'Confirmed', '2025-05-02 13:00:00'),
(12, 3, NULL, 'Pending', '2025-05-03 14:00:00'),
(10, 5, NULL, 'Pending', '2025-05-04 15:00:00');


