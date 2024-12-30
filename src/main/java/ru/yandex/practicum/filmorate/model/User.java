package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;

import java.time.LocalDate;

@Data
public class User {
    private Long id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
