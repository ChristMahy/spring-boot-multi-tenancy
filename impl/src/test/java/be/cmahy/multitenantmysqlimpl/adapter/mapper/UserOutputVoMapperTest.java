package be.cmahy.multitenantmysqlimpl.adapter.mapper;

import be.cmahy.multitenantmysqlapi.vo.output.UserOutputVo;
import be.cmahy.multitenantmysqlimpl.application.vo.output.UserOutputAppVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.generateString;
import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.randomLong;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserOutputVoMapperTest {

    @InjectMocks
    private UserOutputVoMapper mapper;

    @Test
    void map() {
        UserOutputAppVo user = new UserOutputAppVo()
            .setId(randomLong())
            .setName(generateString());

        UserOutputVo actual = mapper.map(user);

        assertThat(actual).isNotNull();

        assertThat(actual.getId()).isEqualTo(user.getId());
        assertThat(actual.getName()).isEqualTo(user.getName());
    }

    @Test
    void map_whenUserIsNull_thenReturnNull() {
        UserOutputVo actual = mapper.map(null);

        assertThat(actual).isNull();
    }
}