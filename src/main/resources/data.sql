DELETE FROM GENRES;
INSERT INTO GENRES (genre_id, genre_name)
VALUES (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');

DELETE FROM MPA;
INSERT INTO MPA (MPA_ID, name)
VALUES (1, 'G');

INSERT INTO MPA (MPA_ID, name)
VALUES (2, 'PG');

INSERT INTO MPA (MPA_ID, name)
VALUES (3, 'PG-13');

INSERT INTO MPA (MPA_ID, name)
VALUES (4, 'R');

INSERT INTO MPA (MPA_ID, name)
VALUES (5, 'NC-17');

DELETE FROM RELATION_STATUS;
INSERT INTO RELATION_STATUS (STATUS_ID, STATUS)
VALUES (1, 'Request Sender By First User');

INSERT INTO RELATION_STATUS (STATUS_ID, STATUS)
VALUES (2, 'Request Sender By Second User');

INSERT INTO RELATION_STATUS (STATUS_ID, STATUS)
VALUES (3, 'Confirmed');