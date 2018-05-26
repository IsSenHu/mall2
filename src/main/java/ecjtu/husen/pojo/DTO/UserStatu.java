package ecjtu.husen.pojo.DTO;

import java.io.Serializable;

public enum UserStatu implements Serializable {
    enable(1, "启用"),
    disable(2, "禁用"),
    deleted(3, "被删除");

    public final static String Doc = "1:enable;2:disable";

    private final Integer value;
    private final String description;

    UserStatu(Integer value, String description){
        this.description = description;
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }
    public String getDescription(){
        return this.description;
    }

    @Override
    public String toString() {
        return "UserStatu{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
