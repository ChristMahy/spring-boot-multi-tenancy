package be.cmahy.multitenantmysqlimpl.application.mapper;

import be.cmahy.multitenantmysqlimpl.application.vo.output.UserOutputAppVo;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;

import javax.inject.Named;

@Named
public class UserOutputAppVoMapper {
    public UserOutputAppVo map(User user) {
        if (user == null) {
            return null;
        }

        return new UserOutputAppVo()
            .setId(user.getId())
            .setName(user.getName());
    }
}
