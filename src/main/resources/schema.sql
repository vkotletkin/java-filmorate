CREATE TABLE IF NOT EXISTS genres
(
    genre_id   BIGINT       NOT NULL,
    genre_name varchar(255) NOT NULL,
    CONSTRAINT pk_genre_id PRIMARY KEY (genre_id)
);

CREATE TABLE IF NOT EXISTS relation_status
(
    status_id BIGINT       NOT NULL,
    status    varchar(255) NOT NULL,
    CONSTRAINT pk_status_id PRIMARY KEY (status_id)
);

CREATE TABLE IF NOT EXISTS MPA
(
    mpa_id BIGINT       NOT NULL,
    rating         varchar(255) NOT NULL,
    CONSTRAINT pk_mpa_id PRIMARY KEY (mpa_id)
);

CREATE TABLE IF NOT EXISTS films
(
    film_id            BIGINT AUTO_INCREMENT NOT NULL,
    name               varchar(255)          NOT NULL,
    description        varchar(200)          NOT NULL,
    release_date       timestamp             NOT NULL,
    duration           BIGINT                NOT NULL,
    mpa BIGINT,
    CONSTRAINT pk_film_id PRIMARY KEY (film_id),
    FOREIGN KEY (mpa) REFERENCES mpa (mpa_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS films_genres
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    film_id  BIGINT                NOT NULL,
    genre_id BIGINT                NOT NULL,
    CONSTRAINT pk_films_genres_id PRIMARY KEY (id),
    FOREIGN KEY (film_id) REFERENCES films (film_id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres (genre_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS users
(
    user_id  BIGINT AUTO_INCREMENT NOT NULL,
    email    varchar(255)          NOT NULL,
    login    varchar(255) UNIQUE   NOT NULL,
    name     varchar(255),
    birthday DATE                  NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS film_likes
(
    like_id BIGINT AUTO_INCREMENT NOT NULL,
    film_id BIGINT                NOT NULL,
    user_id BIGINT                NOT NULL,
    CONSTRAINT pk_like_id PRIMARY KEY (like_id),
    FOREIGN KEY (film_id) REFERENCES films (film_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friends_relations
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    user_id         BIGINT                NOT NULL,
    friend_id       BIGINT                NOT NULL,
    relation_status BIGINT                NOT NULL,
    CONSTRAINT pk_friends_relations_id PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (relation_status) REFERENCES relation_status (status_id) ON DELETE SET NULL
);

