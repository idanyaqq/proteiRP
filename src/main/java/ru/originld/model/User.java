package ru.originld.model;

import com.fasterxml.jackson.annotation.*;
import ru.originld.model.Enums.Role;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Danya on 15/04/2017.
 */

@Entity
@Table(name = "users_test")
@Proxy(lazy = false)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "status")
    private boolean status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    @JsonManagedReference(value = "company")
    private Company company;

    @OneToOne(targetEntity = Passport.class,cascade = CascadeType.PERSIST,mappedBy = "user",fetch = FetchType.EAGER)
    private Passport passport;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class , property = "@id")
    @JoinTable(name = "users_to_users_group",joinColumns = {
            @JoinColumn(name = "user_id",nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "group_id",nullable = false)
            })
    private List<UserGroup> userGroupList;


    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", status=" + status +
                ", role=" + role +
                ", company=" + company +
                ", passport=" + passport +
                ", userGroupList=" + userGroupList +
                '}';
    }
}
