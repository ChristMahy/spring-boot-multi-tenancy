package be.cmahy.multitenantmysqlimpl.adapter.mapper;

import be.cmahy.multitenantmysqlapi.vo.output.UserOutputVo;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.generateString;
import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperOutputVoTest {

    @InjectMocks
    private UserMapperOutputVo mapper;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
            randomLong(),
            generateString()
        );
    }

    @Test
    void map() {
        UserOutputVo actual = mapper.map(user);

        assertThat(actual).isNotNull();

        assertThat(actual.getId()).isEqualTo(user.getId());
        assertThat(actual.getName()).isEqualTo(user.getName());
    }
}