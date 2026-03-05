
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agencies (
    agency_id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    registration_number VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agent_agency (
    id SERIAL PRIMARY KEY,
    agent_id INT REFERENCES users(user_id),
    agency_id INT REFERENCES agencies(agency_id)
);

CREATE TABLE properties (
    property_id SERIAL PRIMARY KEY,
    title VARCHAR(150),
    description TEXT,
    price DECIMAL(15,2),
    location VARCHAR(150),
    status VARCHAR(50),
    agent_id INT REFERENCES users(user_id)
);

CREATE TABLE bookings (
    booking_id SERIAL PRIMARY KEY,
    property_id INT REFERENCES properties(property_id),
    user_id INT REFERENCES users(user_id),
    booking_date TIMESTAMP,
    status VARCHAR(50)
);

CREATE TABLE likes (
    like_id SERIAL PRIMARY KEY,
    property_id INT REFERENCES properties(property_id),
    user_id INT REFERENCES users(user_id)
);

CREATE TABLE comments (
    comment_id SERIAL PRIMARY KEY,
    property_id INT REFERENCES properties(property_id),
    user_id INT REFERENCES users(user_id),
    message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
