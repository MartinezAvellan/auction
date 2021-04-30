DROP TABLE IF EXISTS auction;

CREATE TABLE auction (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  auction_value NUMERIC
);

INSERT INTO auction (name, auction_value) VALUES ('HUGO 1.99', 1.99);
INSERT INTO auction (name, auction_value) VALUES ('HUGO 0.99', 0.99);
INSERT INTO auction (name, auction_value) VALUES ('HUGO 0.01*2', 0.01);
INSERT INTO auction (name, auction_value) VALUES ('HUGO 1.3', 1.3);
INSERT INTO auction (name, auction_value) VALUES ('HUGO WIN', 0.3);
INSERT INTO auction (name, auction_value) VALUES ('HUGO 0.01', 0.01);