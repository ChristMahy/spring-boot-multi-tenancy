package be.cmahy.multitenantmysqlimpl.application.query.user;

import be.cmahy.multitenantmysqlimpl.application.repository.UserRepository;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;

@Named
public class GetAllUserQuery {
    private final static Logger log = LoggerFactory.getLogger(GetAllUserQuery.class);

    private final UserRepository userRepository;

    public GetAllUserQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() {
        log.debug("Querying all users");

        return userRepository.findAll();
    }
}
