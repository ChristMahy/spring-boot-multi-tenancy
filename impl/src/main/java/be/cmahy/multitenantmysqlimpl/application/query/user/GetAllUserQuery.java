package be.cmahy.multitenantmysqlimpl.application.query.user;

import be.cmahy.multitenantmysqlimpl.application.mapper.UserOutputAppVoMapper;
import be.cmahy.multitenantmysqlimpl.application.repository.UserRepository;
import be.cmahy.multitenantmysqlimpl.application.vo.output.UserOutputAppVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class GetAllUserQuery {
    private final static Logger log = LoggerFactory.getLogger(GetAllUserQuery.class);

    private final UserRepository userRepository;
    private final UserOutputAppVoMapper userOutputAppVoMapper;

    public GetAllUserQuery(
        UserRepository userRepository,
        UserOutputAppVoMapper userOutputAppVoMapper
    ) {
        this.userRepository = userRepository;
        this.userOutputAppVoMapper = userOutputAppVoMapper;
    }

    public List<UserOutputAppVo> execute() {
        log.debug("Querying all users");

        return userRepository.findAll().stream()
            .map(userOutputAppVoMapper::map)
            .collect(Collectors.toList());
    }
}
