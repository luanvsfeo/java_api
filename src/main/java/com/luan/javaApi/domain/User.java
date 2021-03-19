package com.luan.javaApi.domain;

import com.luan.javaApi.utils.StringUtils;
import javax.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq")
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "created")
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "token")
    private UUID token;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Phone> phones;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public boolean isValidForCreation(){
        return this.email != null && this.name != null && this.password != null;
    }

    public void generateUser(){
        Date date = new Date();
        this.created = date;
        this.lastLogin = date;
        this.token = UUID.randomUUID();
        this.generateCriptoredPassword();
    }

    public void updateLastLogin(){
        this.lastLogin = new Date();
    }

    public boolean isSessionValid(){
        return ChronoUnit.MINUTES.between(this.getLastLogin().toInstant(), new Date().toInstant()) < 30;
    }

    public void generateCriptoredPassword() {
       this.password = StringUtils.generateCriptoredPassword(this.password);
    }
}
