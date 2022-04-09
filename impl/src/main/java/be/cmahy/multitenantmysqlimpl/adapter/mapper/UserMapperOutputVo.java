package be.cmahy.multitenantmysqlimpl.adapter.mapper;

import be.cmahy.multitenantmysqlapi.vo.output.UserOutputVo;
import be.cmahy.multitenantmysqlimpl.domain.entity.User;

import javax.inject.Named;

@Named
public class UserMapperOutputVo {
    public UserOutputVo map(User user) {
        return new UserOutputVo()
            .setId(user.getId())
            .setName(user.getName());
    }
}
