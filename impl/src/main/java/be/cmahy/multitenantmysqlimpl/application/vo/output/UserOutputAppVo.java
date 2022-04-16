package be.cmahy.multitenantmysqlimpl.application.vo.output;

public class UserOutputAppVo {
    private Long id;
    private String name;

    public UserOutputAppVo() {
    }

    public Long getId() {
        return id;
    }

    public UserOutputAppVo setId(Long id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public UserOutputAppVo setName(String name) {
        this.name = name;

        return this;
    }
}
