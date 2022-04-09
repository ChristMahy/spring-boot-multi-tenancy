package be.cmahy.multitenantmysqlapi;

import be.cmahy.multitenantmysqlapi.vo.output.UserOutputVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public interface UserApi {
    @GetMapping
    ResponseEntity<List<UserOutputVo>> getAllUser();
}
