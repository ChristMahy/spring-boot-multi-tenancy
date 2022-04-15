package be.cmahy.multitenantmysqlimpl.integration;

import be.cmahy.multitenantmysqlimpl.adapter.repository.jpa.UserRepositoryImpl;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.generateString;
import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

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

    @WithMockUser(username = "donald", password = "pw")
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

            MvcResult result = mockMvc
                .perform(
                    MockMvcRequestBuilders
                        .get("/user")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .with(csrf())
                )
                .andReturn();

            assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(result.getResponse().getContentType()).isEqualTo("application/json;charset=UTF-8");
        } catch (Exception exce) {
            fail("Shouldn't pass here !!!", exce);
        }
    }

    @Test
    void getAllUsers_whenUnauthorized_thenReturnUnauthorizedHttpCode() {
        try {
            MvcResult result = mockMvc
                .perform(
                    MockMvcRequestBuilders
                        .get("/user")
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .with(csrf())
                )
                .andReturn();

            assertThat(result.getResponse().getStatus())
                .isEqualTo(HttpStatus.UNAUTHORIZED.value());
        } catch (Exception exce) {
            fail("Shouldn't pass here !!!", exce);
        }
    }
}
