package be.cmahy.multitenantmysqlimpl.adapter.mapper;

import be.cmahy.multitenantmysqlapi.vo.output.UserOutputVo;
import be.cmahy.multitenantmysqlimpl.application.vo.output.UserOutputAppVo;

import javax.inject.Named;

@Named
public class UserOutputVoMapper {
    public UserOutputVo map(UserOutputAppVo user) {
        if (user == null) {
            return null;
        }

        return new UserOutputVo()
            .setId(user.getId())
            .setName(user.getName());
    }
}
