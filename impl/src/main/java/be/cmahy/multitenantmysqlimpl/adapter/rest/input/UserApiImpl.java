package be.cmahy.multitenantmysqlimpl.adapter.rest.input;

import be.cmahy.multitenantmysqlapi.UserApi;
import be.cmahy.multitenantmysqlapi.vo.output.UserOutputVo;
import be.cmahy.multitenantmysqlimpl.adapter.mapper.UserMapperOutputVo;
import be.cmahy.multitenantmysqlimpl.application.query.user.GetAllUserQuery;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class UserApiImpl implements UserApi {
    private final static Logger log = LoggerFactory.getLogger(UserApiImpl.class);

    private final GetAllUserQuery allUserQuery;
    private final UserMapperOutputVo userMapper;

    public UserApiImpl(
        GetAllUserQuery allUserQuery,
        UserMapperOutputVo userMapper
    ) {
        this.allUserQuery = allUserQuery;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<List<UserOutputVo>> getAllUser() {
        log.info("Get all users");

        List<User> users = allUserQuery.execute();

        log.info("Users {}", users.size());

        return ResponseEntity.ok(
            users.stream()
                .map(userMapper::map)
                .collect(Collectors.toList())
        );
    }
}
