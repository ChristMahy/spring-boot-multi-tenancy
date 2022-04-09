package be.cmahy.multitenantmysqlimpl.application.repository;

import be.cmahy.multitenantmysqlimpl.domain.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}
