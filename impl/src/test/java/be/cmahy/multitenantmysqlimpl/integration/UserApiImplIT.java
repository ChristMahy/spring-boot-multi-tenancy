package be.cmahy.multitenantmysqlimpl.integration;

import be.cmahy.multitenantmysqlimpl.adapter.repository.jpa.UserRepositoryImpl;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.generateString;
import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.randomLong;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserApiImplIT {
    @MockBean
    private UserRepositoryImpl userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllUsers() {
        try {
            List<User> users = List.of(
                new User(randomLong(), generateString()),
                new User(randomLong(), generateString()),
                new User(randomLong(), generateString()),
                new User(randomLong(), generateString()),
                new User(randomLong(), generateString()),
                new User(randomLong(), generateString()),
                new User(randomLong(), generateString())
            );

            when(userRepository.findAll()).thenReturn(users);

            mockMvc
                .perform(
                    MockMvcRequestBuilders
                        .get("/user")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("donald").password("pw"))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
        } catch (Exception exce) {
            fail("Shouldn't pass here !!!", exce);
        }
    }

    @Test
    void getAllUsers_whenUnauthorized_thenReturnUnauthorizedHttpCode() {
        try {
            mockMvc
                .perform(
                    MockMvcRequestBuilders
                        .get("/user")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .with(csrf())
                )
                .andExpect(status().isUnauthorized());
        } catch (Exception exce) {
            fail("Shouldn't pass here !!!", exce);
        }
    }
}
