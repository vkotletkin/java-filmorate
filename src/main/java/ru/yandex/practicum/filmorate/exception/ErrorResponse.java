package ru.yandex.practicum.filmorate.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ErrorResponse {

    final String error;
    final String description;
}
