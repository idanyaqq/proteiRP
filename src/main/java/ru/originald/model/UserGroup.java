package ru.originald.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by redin on 5/10/17.
 */
@Entity
@Table(name = "users_group")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "info")
    private String info;

    @ManyToMany(fetch =FetchType.LAZY)
    @JoinTable(name = "users_to_users_group",joinColumns = {
            @JoinColumn(name = "group_id",nullable = false)},
            inverseJoinColumns = {
            @JoinColumn(name = "user_id",nullable = false)
    })
    private List<User> userList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

//    @Override
//    public String toString() {
//        return "UserGroup{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", info='" + info + '\'' +
//                ", userList=" + userList +
//                '}';
//    }
}
