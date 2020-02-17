INSERT INTO APP_ROLE VALUES(1, 'ADMIN');

CREATE TABLE Persistent_Logins (
 
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used Datetime not null,
    PRIMARY KEY (series)
     
);