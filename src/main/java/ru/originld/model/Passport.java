package ru.originld.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by redin on 5/10/17.
 */
@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @Column(name = "user_id",updatable = false,insertable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "number")
    private long number;

    @Column(name = "info")
    private String info;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

//    @Override
//    public String toString() {
//        return "Passport{" +
//                "userId=" + userId +
//                ", number=" + number +
//                ", info='" + info + '\'' +
//                ", user=" + user +
//                '}';
//    }
}
