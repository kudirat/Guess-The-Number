DROP DATABASE IF EXISTS guessNumber;
CREATE DATABASE guessNumber;

USE guessNumber;

CREATE TABLE rounds(
roundid INT AUTO_INCREMENT,
gameid INT NOT NULL, 
guess INT NOT NULL,
finished BOOLEAN DEFAULT false,
CONSTRAINT pk_roundid 
        PRIMARY KEY (roundid)
);

CREATE TABLE games(
gameid INT AUTO_INCREMENT,
answer INT NOT NULL,
gamestatus VARCHAR(25) NOT NULL,
gametime VARCHAR(25) NOT NULL,
CONSTRAINT pk_gameid 
        PRIMARY KEY (gameid)
);

ALTER TABLE rounds 
ADD CONSTRAINT currentgame_fk
FOREIGN KEY (gameid) 
REFERENCES games (gameid);
