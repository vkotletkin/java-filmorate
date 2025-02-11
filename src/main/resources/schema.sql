CREATE TABLE IF NOT EXISTS genres
(
    genre_id   INT          NOT NULL,
    genre_name varchar(255) NOT NULL,
    CONSTRAINT pk_genre_id PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS relation_status
(
    status_id INT          NOT NULL,
    status    varchar(255) NOT NULL,
    CONSTRAINT pk_status_id PRIMARY KEY (status_id)
);

CREATE TABLE IF NOT EXISTS association_rating
(
    association_id INT          NOT NULL,
    rating         varchar(255) NOT NULL,
    CONSTRAINT pk_association_id PRIMARY KEY (association_id)
);

CREATE TABLE IF NOT EXISTS films
(
    film_id            INT          NOT NULL,
    name               varchar(255) NOT NULL,
    description        varchar(255) NOT NULL,
    release_date       timestamp    NOT NULL,
    duration           INT          NOT NULL,
    association_rating INT          NOT NULL,
    CONSTRAINT pk_film_id PRIMARY KEY (film_id),
    FOREIGN KEY (association_rating) REFERENCES association_rating (association_id)
);

CREATE TABLE IF NOT EXISTS films_genres
(
    id       INT NOT NULL,
    film_id  INT NOT NULL,
    genre_id INT NOT NULL,
    CONSTRAINT pk_films_genres_id PRIMARY KEY (id),
    FOREIGN KEY (film_id) REFERENCES films (film_id),
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  INT          NOT NULL,
    email    varchar(255) NOT NULL,
    login    varchar(255) NOT NULL,
    name     varchar(255),
    birthday DATE         NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS film_likes
(
    like_id INT NOT NULL,
    film_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT pk_like_id PRIMARY KEY (like_id),
    FOREIGN KEY (film_id) REFERENCES films (film_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE IF NOT EXISTS friends_relations
(
    id              INT NOT NULL,
    user_id         INT NOT NULL,
    friend_id       INT NOT NULL,
    relation_status INT NOT NULL,
    CONSTRAINT pk_friends_relations_id PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (friend_id) REFERENCES users (user_id),
    FOREIGN KEY (relation_status) REFERENCES relation_status (status_id)
);

