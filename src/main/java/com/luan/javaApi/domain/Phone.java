package com.luan.javaApi.domain;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE,
            generator = "phone_id_seq")
    @SequenceGenerator(
            name = "phone_id_seq",
            sequenceName = "phone_id_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(name = "number")
    private int number;

    @Column(name = "ddd")
    private int ddd;

    @Column(name = "user_id")
    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
