package com.github.black.hole.basic.serialize;

import java.io.Serializable;

/**
 * @author hairen.long
 * @date 2021/8/14
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -3528193716903645620L;
    private Integer age;
    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
