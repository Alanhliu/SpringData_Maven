package com.siasun.springdata;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hliu on 2016/11/1.
 */

@Table(name = "jpa_persons")
@Entity
public class Person {

    @GeneratedValue
    @Id
    private Integer id;
    private String lastName;
    private String email;
    private Date birth;

    @Column(name="add_id")
    private Integer addressId;

    @JoinColumn(name = "address_id")
    @ManyToOne
    private Address address;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
}
