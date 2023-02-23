package com.example.login2.users.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;

    private String password;

    private LocalDateTime jwtCreatedOn;

    private boolean isActive;

    @Column(length = 36)
    private String activationCode;

    @Column(length = 36)
    private String pwdResetCode;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;



    public User() {
    }

    public User(long id, String name, String surname, String email, String password, LocalDateTime jwtCreatedOn,boolean isActive,String activationCode,String pwdResetCode,Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.jwtCreatedOn = jwtCreatedOn;
        this.isActive = isActive;
        this.activationCode = activationCode;
        this.pwdResetCode = pwdResetCode;
        this.roles = roles;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getJwtCreatedOn() {
        return jwtCreatedOn;
    }

    public void setJwtCreatedOn(LocalDateTime jwtCreatedOn) {
        this.jwtCreatedOn = jwtCreatedOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getPwdResetCode() {
        return pwdResetCode;
    }

    public void setPwdResetCode(String pwdResetCode) {
        this.pwdResetCode = pwdResetCode;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
