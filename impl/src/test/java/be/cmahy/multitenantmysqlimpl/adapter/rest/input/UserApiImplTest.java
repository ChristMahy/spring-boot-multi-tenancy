package be.cmahy.multitenantmysqlimpl.adapter.rest.input;

import be.cmahy.multitenantmysqlapi.vo.output.UserOutputVo;
import be.cmahy.multitenantmysqlimpl.adapter.mapper.UserOutputVoMapper;
import be.cmahy.multitenantmysqlimpl.application.query.user.GetAllUserQuery;
import be.cmahy.multitenantmysqlimpl.application.vo.output.UserOutputAppVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static be.cmahy.multitenantmysqlimpl.helper.GeneratorRandomValue.generateString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserApiImplTest {

    @Mock
    private GetAllUserQuery allUserQuery;

    @Mock
    private UserOutputVoMapper userOutputVoMapper;

    @InjectMocks
    private UserApiImpl api;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllUser() {
        List<UserOutputAppVo> users = List.of(
            mock(UserOutputAppVo.class),
            mock(UserOutputAppVo.class)
        );

        when(allUserQuery.execute()).thenReturn(users);

        for (UserOutputAppVo user : users) {
            when(userOutputVoMapper.map(user)).thenReturn(mock(UserOutputVo.class));
        }

        ResponseEntity<List<UserOutputVo>> actual = api.getAllUser();

        assertThat(actual).isNotNull();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();

        assertThat(actual.getBody().size()).isEqualTo(users.size());

        verify(allUserQuery, times(1)).execute();
        verify(userOutputVoMapper, times(users.size())).map(any());
    }

    @Test
    void getAllUser_whenNoUser_thenReturnEmptyList() {
        List<UserOutputAppVo> users = Collections.emptyList();

        when(allUserQuery.execute()).thenReturn(users);

        ResponseEntity<List<UserOutputVo>> actual = api.getAllUser();

        assertThat(actual).isNotNull();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotNull();

        verify(allUserQuery, times(1)).execute();
        verifyNoInteractions(userOutputVoMapper);
    }

    @Test
    void getAllUser_onAnyException_thenReturn500() {
        try {
            IllegalStateException errorThrown = new IllegalStateException(generateString());

            when(allUserQuery.execute()).thenThrow(errorThrown);

            ResponseEntity<List<UserOutputVo>> actual = api.getAllUser();

            assertThat(actual).isNotNull();

            assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(actual.getBody()).isNull();

            verify(allUserQuery, times(1)).execute();
            verifyNoInteractions(userOutputVoMapper);
        } catch (Exception anyExce) {
            fail("Shouldn't pass here !!!!", anyExce);
        }
    }
}