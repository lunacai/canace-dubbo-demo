package com.example.mybatisdemo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable {
    /**
     * 数据库表中的唯一ID
     */
    private Long id;

    /**
     * name
     */
    private String username;

    /**
     * description
     */
    private String description;
    private String content;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}