package ru.originald.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * Created by redin on 5/5/17.
 */
@Entity
@Table(name = "company")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    private String info;

    @Column(name = "address")
    private String address;

    private String email;

    private long phone;

    @OneToMany(mappedBy = "company",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

//    @Override
//    public String toString() {
//        return "Company{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", info='" + info + '\'' +
//                ", address='" + address + '\'' +
//                ", email='" + email + '\'' +
//                ", phone=" + phone +
//                ", users=" + users +
//                '}';
//    }
}
