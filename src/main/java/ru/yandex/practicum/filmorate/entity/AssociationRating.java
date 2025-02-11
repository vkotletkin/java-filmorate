package ru.yandex.practicum.filmorate.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssociationRating {
    Long id;
    String rating;
}
