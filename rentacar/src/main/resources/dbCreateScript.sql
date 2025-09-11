
--DataBase Creation
CREATE DATABASE rent_a_car
WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.UTF-8'
    LC_CTYPE = 'en_US.UTF-8'
    TEMPLATE = template0;

-- Users table create
CREATE TABLE Users(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE, 
    passwd bytea
    user_role TINYINT NOT NULL DEFAULT 1;    
    
);

-- Customers table create
CREATE TABLE Customers(
    customer_id SERIAL PRIMARY KEY,
    user_id INT not NULL,
    customer_name VARCHAR(20) not null, 
    customer_surname VARCHAR(30) not null, 
    customer_age SMALLINT not null,     
    company_name VARCHAR(20) not null, 
    -- Foreign Key constraints
    CONSTRAINT fk_customer_user FOREIGN KEY(user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- VehicleTypes table create
CREATE TABLE VehicleTypes(
    type_id SERIAL PRIMARY KEY,    
    vehicle_type VARCHAR(20) not null,
    hourly_pricing DECIMAL(6,2) not null,
    daily_pricing DECIMAL(6,2) not null, 
    weakly_pricing DECIMAL(6,2) not null, 
    monthly_pricing DECIMAL(6,2) not null  
);

CREATE TABLE Vehicles (
    vehicle_id SERIAL PRIMARY KEY,
    type_id INT NOT NULL,
    plate VARCHAR(10) NOT NULL UNIQUE,
    model_year SMALLINT NOT NULL,
    model_name VARCHAR(20) not null, 
    brand_name VARCHAR(20) not null, 
    km NUMERIC(12,2) NOT NULL,
    fuel NUMERIC(5,2) NOT NULL,
    vehicle_value INT NOT NULL,    
    vehicle_status VARCHAR(10) NOT NULL,
    -- Foreign Key constraints
    CONSTRAINT fk_vehicle_type FOREIGN KEY(type_id) REFERENCES VehicleTypes(type_id) ON DELETE RESTRICT
);

-- Rental table create

CREATE TABLE Rentals (
    rental_id SERIAL PRIMARY KEY,
    vehicle_id INT NOT NULL,
    customer_id INT NOT NULL,    
    rent_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    planned_dropoff_date TIMESTAMP DEFAULT NULL,
    actual_dropoff_date TIMESTAMP DEFAULT NULL,
    planned_price DECIMAL(10,2),
    late_fee DECIMAL(10,2),
    repair_fee DECIMAL(10,2),
    checkout DECIMAL(10,2),
    depozit DECIMAL(10,2),
    rental_status VARCHAR(10) NOT NULL,
    -- Foreign Key constraints
    CONSTRAINT fk_rental_vehicle FOREIGN KEY(vehicle_id) REFERENCES Vehicles(vehicle_id) ON DELETE CASCADE,
    CONSTRAINT fk_rental_customer FOREIGN KEY(customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
);