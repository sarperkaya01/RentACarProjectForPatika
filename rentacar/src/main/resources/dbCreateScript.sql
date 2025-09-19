CREATE DATABASE rent_a_car
WITH
    OWNER = postgres,
    ENCODING = 'UTF8',
    LC_COLLATE = 'en_US.UTF-8',
    LC_CTYPE = 'en_US.UTF-8',
    TEMPLATE = template0;

-- Users table create
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(254) NOT NULL UNIQUE,
    passwd BYTEA NOT NULL,
    user_role VARCHAR(10) NOT NULL DEFAULT 'ADMIN'
);

-- Customers table create
CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    user_id INT not NULL,
    customer_name VARCHAR(20) not null,
    customer_surname VARCHAR(30) not null,
    customer_age SMALLINT not null,
    company_name VARCHAR(20) not null UNIQUE,
    -- Foreign Key constraints
    CONSTRAINT fk_customer_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

-- VehicleTypes table create
CREATE TABLE vehicle_properties (
    prop_id SERIAL PRIMARY KEY,
    vehicle_type VARCHAR(20) not null,
    hourly_pricing DECIMAL(6, 2) not null,
    daily_pricing DECIMAL(6, 2) not null,
    weekly_pricing DECIMAL(6, 2) not null,
    monthly_pricing DECIMAL(6, 2) not null
);

CREATE TABLE vehicles (
    vehicle_id SERIAL PRIMARY KEY,
    prop_id INT NOT NULL,
    plate VARCHAR(10) NOT NULL UNIQUE,
    model_year SMALLINT NOT NULL,
    model_name VARCHAR(20) not null,
    brand_name VARCHAR(20) not null,
    km NUMERIC(12, 2) NOT NULL,
    fuel NUMERIC(5, 2) NOT NULL,
    vehicle_value INT NOT NULL,
    vehicle_status VARCHAR(10) NOT NULL,
    -- Foreign Key constraints
    CONSTRAINT fk_vehicle_type FOREIGN KEY (prop_id) REFERENCES vehicle_properties (prop_id) ON DELETE RESTRICT
);

-- Rental table create

CREATE TABLE rentals (
    rental_id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    rent_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rental_status VARCHAR(10) NOT NULL,
    -- Foreign Key constraints
    CONSTRAINT fk_rental_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (vehicle_id) ON DELETE RESTRICT,
    CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE RESTRICT
);

CREATE TABLE checkOut (
    checkout_id SERIAL PRIMARY KEY,
    rental_id INT NOT NULL,
    planned_dropoff_date TIMESTAMP DEFAULT NULL,
    actual_dropoff_date TIMESTAMP DEFAULT NULL,
    planned_price DECIMAL(10, 2),
    depozit DECIMAL(10, 2),
    late_fee DECIMAL(10, 2),
    repair_fee DECIMAL(10, 2) DEFAULT NULL,
    checkout_amount DECIMAL(10, 2),
    checkout_status VARCHAR(10) NOT NULL,
    -- Foreign Key constraints
    CONSTRAINT fk_checkout FOREIGN KEY (rental_id) REFERENCES rentals (rental_id) ON DELETE CASCADE
);