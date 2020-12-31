CREATE TABLE PERSON
(
    `ID`         int(11)      NOT NULL AUTO_INCREMENT,
    `NAME`       varchar(100) NOT NULL,
    `PASSWORD`   varchar(100) NOT NULL,
    `DELETED`    tinyint(1)   NOT NULL DEFAULT '0',
    PRIMARY KEY (`ID`)
);