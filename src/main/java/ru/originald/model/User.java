package ru.originald.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.originald.model.Enums.Role;
import ru.originald.model.to.UserGroupForUserUsing;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Danya on 15/04/2017.
 */

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users")
public class User {

    public static final int MIN_PASS_LENGTH = 6;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(targetEntity = Passport.class, cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Passport passport;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "users_to_users_group", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "group_id", nullable = false)
            })
    private List<UserGroup> userGroupList;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "users_to_users_group", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "group_id", nullable = false)
            })
    private List<UserGroupForUserUsing> userGroupForUserUsings;

    public List<UserGroupForUserUsing> getUserGroupForUserUsings() {
        return userGroupForUserUsings;
    }

    public void setUserGroupForUserUsings(List<UserGroupForUserUsing> userGroupForUserUsings) {
        this.userGroupForUserUsings = userGroupForUserUsings;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
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

//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", email='" + email + '\'' +
//                ", phone=" + phone +
//                ", status=" + status +
//                ", role=" + role +
//                ", company=" + company +
//                ", passport=" + passport +
//                ", userGroupList=" + userGroupForUserUsings +
//                '}';
//    }
//}
}
