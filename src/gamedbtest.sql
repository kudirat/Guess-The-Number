DROP DATABASE IF EXISTS guessNumbertest;
CREATE DATABASE guessNumbertest;

USE guessNumbertest;

CREATE TABLE rounds(
roundid INT AUTO_INCREMENT,
gameid INT NOT NULL, 
guess INT NOT NULL,
guesstime TIMESTAMP NOT NULL,
guessresult VARCHAR(25) NOT NULL,
finished BOOLEAN DEFAULT false,
CONSTRAINT pk_roundid 
        PRIMARY KEY (roundid)
);

CREATE TABLE games(
gameid INT AUTO_INCREMENT,
answer INT NOT NULL,
gamestatus VARCHAR(25) NOT NULL,
CONSTRAINT pk_gameid 
        PRIMARY KEY (gameid)
);

ALTER TABLE rounds 
ADD CONSTRAINT currentgame_fk
FOREIGN KEY (gameid) 
REFERENCES games (gameid)
ON DELETE CASCADE;
