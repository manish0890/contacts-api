package com.manish0890.contacts.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Contact implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String firstName;

    @Column(nullable = false, length = 25)
    private String lastName;

    @Column(length = 100)
    private String company;

    @Column(length = 100)
    private String street;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(length = 5)
    private String zipcode;

    @Column
    private String notes;

    @Column(nullable = false)
    private Date createdDate;

    @Column
    private Date updatedDate;
}
