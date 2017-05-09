package ru.originld.model;

import javax.persistence.*;

/**
 * Created by Danya on 09/05/2017.
 */
@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nubmer")
    private long number;

    @Column(name = "address")
    private String address;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
