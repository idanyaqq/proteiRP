package ru.originld.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by redin on 5/5/17.
 */
@Entity
@Table(name = "company")
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

    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    // for what is that ?
    private List<User> users;

//    private Company(Builder builder) {
//        id = builder.id;
//        name = builder.name;
//        info = builder.info;
//        email = builder.email;
//        phone = builder.phone;
//        address = builder.address;
//
//    }
//
//
//
//    public static class Builder{
//        private long id;
//        private String name;
//        private String info;
//        private String address;
//        private String email;
//        private long phone;
//
//        public Builder() {
//        }
//
//        public Builder id(int val){
//            id = val;
//            return this;
//        }
//
//        public Builder name(String val){
//            name = val;
//            return this;
//        }
//
//        public Builder info(String val){
//            info = val;
//            return this;
//        }
//
//        public Builder address(String val){
//            address = val;
//            return this;
//        }
//
//        public Builder email(String val){
//            email = val;
//            return this;
//        }
//
//        public Builder phone(long val){
//            phone = val;
//            return this;
//        }
//
//        public Company build(){
//            Company company = new Company(this);
//            clean();
//            return company;
//        }
//        public void clean() {
//            id = 0;
//            name = null;
//            info = null;
//            address = null;
//            email = null;
//            phone = 0;
//        }
//    }

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
}
