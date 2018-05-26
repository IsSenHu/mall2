package ecjtu.husen.pojo.DTO;

import java.io.Serializable;

/**
 * 入库单状态
 */
public enum InStatu implements Serializable {
    notReviewde(0, "未审核"),
    auditedFailed(1, "审核未通过"),
    auditedSuccessed(2, "审核通过");

    public final static String Doc = "0:未审核;1:审核未通过;2:审核通过";

    private final Integer value;
    private final String description;

    InStatu(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDoc() {
        return Doc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "InStatu{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }
}
