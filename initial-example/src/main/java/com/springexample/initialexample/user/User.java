package com.springexample.initialexample.user;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class User {
    private Integer id;
    @Size(min = 2, max = 50, message = "Number of characters in name should be minimum 2 and maximum 50")
    private String name;
    @Past(message = "Birthday should be in past.")
    private Date birthDate;

    public User() {
    }

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
