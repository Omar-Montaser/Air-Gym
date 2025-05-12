# 📊 Air-Gym Database Schema

This document outlines the database structure used in the **Air-Gym** desktop application. The database is implemented in **SQL Server** and supports core operations for gym members, admins, trainers, sessions, and payments.

---

## 📌 Table Overview

### 1. `Users`
Stores all users: members, admins, and trainers.

- `UserID` (PK)
- `Password`, `FirstName`, `LastName`
- `PhoneNumber` (unique, 11-digit Egyptian format)
- `Gender` (`Male`, `Female`)
- `DateOfBirth`
- `Role` (`Admin`, `Member`, `Trainer`)

### 2. `Branch`
Gym locations.

- `BranchID` (PK)
- `Name`, `Location`, `OpeningDate`
- `Status` (`Active`, `Maintenance`, `Closed`)
- `AdminID` → `Users(UserID)`

### 3. `MembershipType`
Membership packages.

- `MembershipTypeID` (PK)
- `Name`, `Description`
- `AccessLevel`, `MonthlyPrice`, `Sessions`
- `PrivateTrainer`, `FreezeDuration`, `InBody`
- `ColorHex` (`#RRGGBB`)

### 4. `Trainer`
Trainer info, extending `Users`.

- `UserID` (PK, FK → `Users`)
- `Specialization`, `ExperienceYears`, `Salary`
- `BranchID` → `Branch`
- `Status` (`Active`, `OnLeave`, `Terminated`)

### 5. `Member`
Member info, extending `Users`.

- `UserID` (PK, FK → `Users`)
- `MembershipTypeID` → `MembershipType`
- `SubscriptionStartDate`, `SubscriptionEndDate`
- `FreezeEndDate`, `SessionsAvailable`, `FreezesAvailable`
- `BranchID`, `TrainerID`
- `SubscriptionStatus` (`Active`, `Expired`, `Cancelled`, `Frozen`)

### 6. `Session`
Workout sessions.

- `SessionID` (PK)
- `TrainerID` → `Trainer`
- `BranchID` → `Branch`
- `SessionType` (`Yoga`, `HIIT`, etc.)
- `MaxCapacity`, `DateTime`, `Duration`
- `Status` (`Scheduled`, `Completed`, `Cancelled`, `Full`)

### 7. `Booking`
Tracks member session bookings.

- `BookingID` (PK)
- `UserID` → `Member`
- `SessionID` → `Session`
- `Status` (`Confirmed`, `Cancelled`)
- `BookingDate`

### 8. `Equipment`
Branch equipment inventory.

- `EquipmentID` (PK)
- `Name`, `PurchaseDate`, `MaintenanceDate`
- `Status` (`Available`, `Maintenance`, `Retired`)
- `BranchID` → `Branch`

### 9. `Payment`
Tracks financial transactions.

- `PaymentID` (PK)
- `Category` (`Membership`, `Expense`, `Other`)
- `MemberID` → `Member`
- `PaymentMethod` (`CreditCard`, `Cash`, etc.)
- `PaymentDate`, `Amount`, `Status`

---

## ✅ Constraints and Validations

- Egyptian phone number validation via a `CHECK` constraint.
- Passwords must be ≥ 8 characters.
- Role-specific values constrained via `CHECK`.
- Subscription dates are validated for logical duration (≤ 1 year).
- Foreign keys are configured with `CASCADE` or `SET NULL` behavior.

---

## 📂 Scripts

All schema creation scripts, stored procedures, and functions are located in the `/data` directory of the repository.

---

