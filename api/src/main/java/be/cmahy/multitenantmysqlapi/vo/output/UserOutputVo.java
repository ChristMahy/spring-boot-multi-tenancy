package be.cmahy.multitenantmysqlapi.vo.output;

public class UserOutputVo {
    private Long id;
    private String name;

    public UserOutputVo() {
    }

    public Long getId() {
        return id;
    }

    public UserOutputVo setId(Long id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public UserOutputVo setName(String name) {
        this.name = name;

        return this;
    }
}
