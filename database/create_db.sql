CREATE DATABASE IF NOT EXISTS wine_dine_pairing;
USE wine_dine_pairing;

CREATE TABLE IF NOT EXISTS wines (
    wine_id INT AUTO_INCREMENT PRIMARY KEY,
    colour ENUM('wit', 'rood', 'rosé', 'onbekend') NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS foods (
    food_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    gamechanger BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS pairings (
    pairing_id INT AUTO_INCREMENT PRIMARY KEY,
    wine_id INT NOT NULL,
    CONSTRAINT fk_wine
        FOREIGN KEY (wine_id)
        REFERENCES wines(wine_id)
        ON UPDATE CASCADE 
        ON DELETE CASCADE,
    food_id INT NOT NULL,
    CONSTRAINT fk_food
        FOREIGN KEY (food_id)
        REFERENCES foods(food_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    wine_specs VARCHAR(255),
    food_specs VARCHAR(255)
);