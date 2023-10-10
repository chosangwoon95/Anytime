DROP TABLE BOARD_AUTH CASCADE CONSTRAINTS PURGE;

CREATE TABLE BOARD_AUTH (
    BOARD_AUTH_ID NUMBER NOT NULL PRIMARY KEY,
    USER_ID NUMBER NOT NULL,
    BOARD_ID NUMBER NOT NULL, 
    FOREIGN KEY (BOARD_ID) REFERENCES BOARD(BOARD_ID),
    FOREIGN KEY (USER_ID) REFERENCES MEMBER(USER_ID) 
);

DROP SEQUENCE BOARD_AUTH_SEQ;

CREATE SEQUENCE BOARD_AUTH_SEQ;

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 1, 1);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 1, 2);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 4, 3);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 4, 4);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 3, 5);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 3, 6);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 1, 25);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 1, 26);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 1, 27);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 41, 42);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 41, 43);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 41, 44);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 41, 45);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 41, 46);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 41, 47);

INSERT INTO BOARD_AUTH (BOARD_AUTH_ID, USER_ID, BOARD_ID)
VALUES (BOARD_AUTH_SEQ.NEXTVAL, 41, 48);