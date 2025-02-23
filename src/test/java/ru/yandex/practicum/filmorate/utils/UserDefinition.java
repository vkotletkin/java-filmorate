package ru.yandex.practicum.filmorate.utils;

import ru.yandex.practicum.filmorate.entity.User;

import java.time.LocalDate;

public class UserDefinition {
    public static User userLucBesson = User.builder()
            .id(1L)
            .email("lucbesson@gmail.com")
            .login("lucbesson")
            .name("Luc Besson")
            .birthday(LocalDate.parse("1959-03-18"))
            .build();
}
