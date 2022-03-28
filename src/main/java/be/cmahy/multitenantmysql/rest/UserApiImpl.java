package be.cmahy.multitenantmysql.rest;

import be.cmahy.multitenantmysql.entity.User;
import be.cmahy.multitenantmysql.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApiImpl {
    private final static Logger log = LoggerFactory.getLogger(UserApiImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserApiImpl(UserRepository userRepository) {
        log.info("UserApiImpl initialized");

        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        log.info("Get all users");

        List<User> users = (List<User>) userRepository.findAll();

        log.info("Users {}", users.size());

        return ResponseEntity.ok(users);
    }
}
