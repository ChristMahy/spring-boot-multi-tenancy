package be.cmahy.multitenantmysqlimpl.application.query.user;

import be.cmahy.multitenantmysqlimpl.application.mapper.UserOutputAppVoMapper;
import be.cmahy.multitenantmysqlimpl.application.repository.UserRepository;
import be.cmahy.multitenantmysqlimpl.application.vo.output.UserOutputAppVo;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllUserQueryTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserOutputAppVoMapper mapper;

    @InjectMocks
    private GetAllUserQuery query;

    @BeforeEach
    void setUp() {
    }

    @Test
    void execute() {
        List<User> users = List.of(
            mock(User.class),
            mock(User.class),
            mock(User.class),
            mock(User.class)
        );

        when(repository.findAll()).thenReturn(users);
        users.forEach(user -> {
            UserOutputAppVo userAppVoMocked = mock(UserOutputAppVo.class);

            when(mapper.map(eq(user))).thenReturn(userAppVoMocked);
        });

        List<UserOutputAppVo> actual = query.execute();

        assertThat(actual).isNotNull();

        assertThat(actual.size()).isEqualTo(users.size());
    }

    @Test
    void execute_whenListFromRepositoryIsEmpty_thenEmptyList() {
        List<User> users = Collections.emptyList();

        when(repository.findAll()).thenReturn(users);

        List<UserOutputAppVo> actual = query.execute();

        assertThat(actual).isNotNull();
        assertThat(actual).isEmpty();

        verifyNoInteractions(mapper);
    }
}