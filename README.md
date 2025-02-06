# java-filmorate
![Untitled](https://github.com/user-attachments/assets/c4922a26-8bd2-479c-9683-72990094efe0)

Добавление фильма:

```
INSERT INTO films (film_id, name, description, release_date, duration, association_rating) 
VALUES (1, 'Test', 'Test Description', '1985-07-23', 500, 1);
```

Добавление пользователя:
```
INSERT INTO users (user_id, email, login, name, birthday) 
VALUES (25, 'test@test.ru', 'testlogin', 'Test', '1985-07-23');
```

Удаление пользователя:
```
DELETE FROM USERS
WHERE user_id = 25;
```

Поиск среднего значения длительности всех фильмов:
```
SELECT AVG(duration) AS average_film_duration
FROM films;
```
