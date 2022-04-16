package be.cmahy.multitenantmysqlimpl.application.mapper;

import be.cmahy.multitenantmysqlimpl.application.vo.output.UserOutputAppVo;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.generateString;
import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.randomLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserOutputAppVoMapperTest {

    @InjectMocks
    private UserOutputAppVoMapper mapper;

    @Test
    void map() {
        User user = new User(randomLong(), generateString());

        UserOutputAppVo actual = mapper.map(user);

        assertThat(actual).isNotNull();

        assertThat(actual.getId()).isEqualTo(user.getId());
        assertThat(actual.getName()).isEqualTo(user.getName());
    }

    @Test
    void map_whenUserIsNull_thenReturnNull() {
        UserOutputAppVo actual = mapper.map(null);

        assertThat(actual).isNull();
    }
}