-- ================================
-- SpaceFinder Database Schema
-- Run this in Railway SQL editor
-- ================================

-- Step 1: Drop existing tables (child tables first)
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS properties CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ================================
-- USERS TABLE
-- Matches User.java field order:
-- id, name, surname, email, phone,
-- password, role, status
-- ================================
CREATE TABLE users (
    id          BIGSERIAL           PRIMARY KEY,
    name        VARCHAR(255),
    surname     VARCHAR(255),
    email       VARCHAR(255)        UNIQUE NOT NULL,
    phone       VARCHAR(255),
    password    VARCHAR(255)        NOT NULL,
    role        VARCHAR(50),
    status      VARCHAR(50)
);

-- ================================
-- PROPERTIES TABLE
-- Matches Property.java field order:
-- id, title, description, price,
-- location, size, status, bedroom,
-- bathroom, parking, property_type,
-- listing_date, transfer_duty, pets,
-- photo, agent_id
-- ================================
CREATE TABLE properties (
    id              BIGSERIAL           PRIMARY KEY,
    title           VARCHAR(255),
    description     TEXT,
    price           DOUBLE PRECISION,
    location        VARCHAR(255),
    size            DOUBLE PRECISION,
    status          VARCHAR(50),
    bedroom         INTEGER,
    bathroom        INTEGER,
    parking         INTEGER,
    property_type   VARCHAR(100),
    listing_date    TIMESTAMP,
    transfer_duty   BOOLEAN,
    pets            BOOLEAN,
    photo           OID,
    agent_id        BIGINT              REFERENCES users(id)
);

-- ================================
-- BOOKINGS TABLE
-- Matches Booking.java field order:
-- id, user_id, agent_id, property_id,
-- viewing_date, status, notes, created_at
-- ================================
CREATE TABLE bookings (
    id              BIGSERIAL           PRIMARY KEY,
    user_id         BIGINT              NOT NULL REFERENCES users(id),
    agent_id        BIGINT              NOT NULL REFERENCES users(id),
    property_id     BIGINT              NOT NULL REFERENCES properties(id),
    viewing_date    TIMESTAMP,
    status          VARCHAR(50),
    notes           VARCHAR(255),
    created_at      TIMESTAMP
);

-- ================================
-- Re-seed default ADMIN user
-- Password will be set by DataSeeder on app restart
-- ================================
-- NOTE: Leave this commented out if using DataSeeder
-- DataSeeder will automatically create admin@spacefinder.com
-- INSERT INTO users (name, surname, email, phone, password, role, status)
-- VALUES ('System', 'Admin', 'admin@spacefinder.com', '0000000000', 'PLACEHOLDER', 'ADMIN', 'Active');

CREATE TABLE likes (
    id SERIAL PRIMARY KEY,
    property_id INT REFERENCES properties(id),
    user_id INT REFERENCES users(id)
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    property_id INT REFERENCES properties(id),
    user_id INT REFERENCES users(id),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


========================




ALTER TABLE properties
ADD COLUMN province VARCHAR(100);