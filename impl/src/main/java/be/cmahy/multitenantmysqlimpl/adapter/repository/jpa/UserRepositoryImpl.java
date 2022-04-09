package be.cmahy.multitenantmysqlimpl.adapter.repository.jpa;

import be.cmahy.multitenantmysqlimpl.application.repository.UserRepository;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryImpl extends UserRepository, CrudRepository<User, Long> {
}
