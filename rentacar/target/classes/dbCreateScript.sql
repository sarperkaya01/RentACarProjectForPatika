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
    user_id INT not NULL UNIQUE,
    customer_name VARCHAR(20) not null,
    customer_surname VARCHAR(30) not null,
    customer_age SMALLINT not null,
    company_name VARCHAR(20) not null UNIQUE,
    -- Foreign Key constraints
    CONSTRAINT fk_customer_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE vehicle_properties (
    prop_id SERIAL PRIMARY KEY,
    daily_pricing DECIMAL(6, 2) not null,
    weekly_pricing DECIMAL(6, 2) not null,
    monthly_pricing DECIMAL(6, 2) not null
);

CREATE TABLE vehicles (
    vehicle_id SERIAL PRIMARY KEY,
    vehicle_type VARCHAR(31) NOT NULL,
    prop_id INT NOT NULL,
    brand_name VARCHAR(30) NOT NULL,
    model_name VARCHAR(30) NOT NULL,
    model_year INT NOT NULL,
    plate_or_tailnumber VARCHAR(15) NOT NULL UNIQUE,
    current_fuel NUMERIC(6, 2) NOT NULL,
    max_fuel_capacity NUMERIC(6, 2) NOT NULL,
    vehicle_value INT NOT NULL,
    vehicle_status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_vehicle_properties FOREIGN KEY (prop_id) REFERENCES vehicle_properties (prop_id)
);

CREATE TABLE checkout (
    checkout_id SERIAL PRIMARY KEY,
    planned_dropoff_date TIMESTAMP DEFAULT NULL,
    actual_dropoff_date TIMESTAMP DEFAULT NULL,
    planned_price DECIMAL(10, 2),
    depozit DECIMAL(10, 2),
    late_fee DECIMAL(10, 2),
    repair_fee DECIMAL(10, 2) DEFAULT NULL,
    checkout_amount DECIMAL(10, 2),
    checkout_status VARCHAR(10) NOT NULL
    -- Foreign Key constraints
);

-- Rental table create

CREATE TABLE rentals (
    rental_id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    checkout_id INT NOT NULL,
    rent_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    rental_status VARCHAR(10) NOT NULL,
    -- Foreign Key constraints
    CONSTRAINT fk_rental_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (vehicle_id) ON DELETE RESTRICT,
    CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE RESTRICT,
    CONSTRAINT fk_rental_checkout FOREIGN KEY (checkout_id) REFERENCES checkout (checkout_id) ON DELETE CASCADE
);

CREATE TABLE automobiles (
    auto_id INT PRIMARY KEY,
    km NUMERIC(12, 2) NOT NULL,
    wheel_drive_type VARCHAR(20) NOT NULL,
    CONSTRAINT fk_auto_vehicle FOREIGN KEY (auto_id) REFERENCES vehicles (vehicle_id) ON DELETE CASCADE
);

CREATE TABLE motorcycles (
    motor_id INT PRIMARY KEY,    
    km NUMERIC(12, 2) NOT NULL,
    engine_cc INT NOT NULL,
    mobility_type VARCHAR(20) NOT NULL,
    CONSTRAINT fk_motor_vehicle FOREIGN KEY (motor_id) REFERENCES vehicles (vehicle_id) ON DELETE CASCADE
);

CREATE TABLE helicopters (
    heli_id INT PRIMARY KEY,   
    flight_hours NUMERIC(12, 2) NOT NULL,
    speciality VARCHAR(20) NOT NULL,
    CONSTRAINT fk_heli_vehicle FOREIGN KEY (heli_id) REFERENCES vehicles (vehicle_id) ON DELETE CASCADE
);