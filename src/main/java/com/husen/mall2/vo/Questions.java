package com.husen.mall2.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 接收问题和答案的vo
 * @author husen
 */
public class Questions implements Serializable {
    private Integer q1;
    private Integer q2;
    private Integer q3;

    @NotEmpty(message = "1问题不能为空")
    @Length(max = 20, message = "1答案长度必须小于20")
    private String answer1;
    @NotEmpty(message = "2问题不能为空")
    @Length(max = 20, message = "2答案长度必须小于20")
    private String answer2;
    @NotEmpty(message = "3问题不能为空")
    @Length(max = 20, message = "3答案长度必须小于20")
    private String answer3;

    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public Integer getQ2() {
        return q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    public Integer getQ3() {
        return q3;
    }

    public void setQ3(Integer q3) {
        this.q3 = q3;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }
}
