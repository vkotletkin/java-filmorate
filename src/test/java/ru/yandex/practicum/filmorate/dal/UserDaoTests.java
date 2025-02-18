package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.impl.H2DatabaseUserDaoImpl;
import ru.yandex.practicum.filmorate.entity.User;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({H2DatabaseUserDaoImpl.class})
public class UserDaoTests {
    private final H2DatabaseUserDaoImpl dao;

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = dao.findUserById(1L);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    @Test
    public void testFindAllUsers() {
        Collection<User> user = dao.findAll();

        assertThat(user.size()).isEqualTo(1);
    }

    @Test
    public void testDeleteUser() {
        dao.deleteUserById(1L);

        assertThat(dao.findAll().size()).isEqualTo(0);
    }
}
