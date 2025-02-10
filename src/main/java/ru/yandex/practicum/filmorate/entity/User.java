package ru.yandex.practicum.filmorate.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    Long id;

    @Email
    String email;

    @NotBlank
    String login;

    String name;

    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthday;

    Set<Long> friendsIds;
}
