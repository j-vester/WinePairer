USE wine_dine_pairing;

DROP TABLE pairings;
TRUNCATE TABLE wines;
TRUNCATE TABLE foods;
CREATE TABLE pairings (
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
    wine_specs VARCHAR(1000),
    food_specs VARCHAR(1000)
);