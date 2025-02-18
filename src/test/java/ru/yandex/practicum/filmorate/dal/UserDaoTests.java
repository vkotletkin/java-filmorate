package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.dal.impl.H2DatabaseUserDaoImpl;
import ru.yandex.practicum.filmorate.entity.User;
import ru.yandex.practicum.filmorate.utils.UserDefinition;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import({H2DatabaseUserDaoImpl.class})
public class UserDaoTests {
    private final H2DatabaseUserDaoImpl dao;

    @Test
    public void testUser() {
        Collection<User> users = dao.findAll();
        assertThat(users.size()).isEqualTo(0);

        dao.createUser(UserDefinition.userLucBesson);
        users = dao.findAll();
        assertThat(users.size()).isEqualTo(1);

        Optional<User> userOptional = dao.findUserById(1L);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L)
                );

        dao.deleteUserById(1L);
        users = dao.findAll();
        assertThat(users.size()).isEqualTo(0);
    }
}
