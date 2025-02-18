MERGE INTO GENRES
    KEY (GENRE_ID)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик');

MERGE INTO MPA
    KEY (MPA_ID)
    VALUES (1, 'G'),
           (2, 'PG'),
           (3, 'PG-13'),
           (4, 'R'),
           (5, 'NC-17');

MERGE INTO RELATION_STATUS
    KEY (STATUS_ID)
    VALUES (1, 'Request Sender By First User'),
           (2, 'Request Sender By Second User'),
           (3, 'Confirmed');

MERGE INTO USERS (USER_ID, EMAIL, LOGIN, BIRTHDAY, NAME)
    KEY (USER_ID)
    VALUES (1, 'vkotletkin@google.com', 'vkotletkin', '2001-01-02', 'Vladislav Kotletkin');