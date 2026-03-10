
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agencies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    registration_number VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE agent_agency (
    id SERIAL PRIMARY KEY,
    agent_id INT REFERENCES users(id),
    agency_id INT REFERENCES agencies(id)
);

CREATE TABLE properties (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150),
    description TEXT,
    price DECIMAL(15,2),
    location VARCHAR(150),
    status VARCHAR(50),
    agent_id INT REFERENCES users(id)
);

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    property_id INT REFERENCES properties(id),
    user_id INT REFERENCES users(id),
    booking_date TIMESTAMP,
    status VARCHAR(50)
);

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
