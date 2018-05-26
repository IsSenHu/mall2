package ecjtu.husen.pojo.DTO;

import java.io.Serializable;

public enum Gender implements Serializable {
    unknown(0, "未知性别"),
    male(11, "男"),
    female(12, "女");

    public final static String Doc = "0:unknown;11:male;12:female";

    private final Integer value;
    private final String description;
    Gender(Integer value, String description){
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
        return "Gender{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
