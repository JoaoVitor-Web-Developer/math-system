-- V1__Create_initial_tables.sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- User Table
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       value_per_square_meter DECIMAL(19, 2)
);

-- Role Table
CREATE TABLE roles (
                       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                       name VARCHAR(50) UNIQUE NOT NULL
);

-- Join table for N-N relationship between Users and Roles
CREATE TABLE users_roles (
                             user_id UUID NOT NULL,
                             role_id UUID NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                             FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Budgets Table
CREATE TABLE budgets (
                         id UUID PRIMARY KEY,
                         client_name VARCHAR(255) NOT NULL,
                         description TEXT,
                         total_area DECIMAL(10, 2) NOT NULL,
                         price_per_meter_at_creation DECIMAL(19, 2) NOT NULL,
                         total_price DECIMAL(19, 2) NOT NULL,
                         created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                         user_id UUID NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert initial roles
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_USER');