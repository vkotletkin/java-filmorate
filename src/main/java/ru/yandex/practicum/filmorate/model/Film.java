package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Film {
    Long id;

    @NotBlank
    String name;

    @NotBlank
    @Size(max = 200)
    String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate releaseDate;

    @Min(1)
    Long duration;
}
