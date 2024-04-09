USE WishlistDB;
INSERT INTO Users (name, password) VALUES ('test', 'test');

INSERT INTO Wishlists (name, description, user_id) VALUES ('Liste','Beskrivelse af en liste', 1);

INSERT INTO Wish (name, description, price, url, wishlist_id) VALUES ('wish', 'Beskrivelse af et wish', 'https://google.com', 1);