package com.manish0890.contacts.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.manish0890.contacts.entity.Phone;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Phone> phones;
    private String company;
    private String street;
    private String city;
    private String state;
    private Long zipcode;
    private String notes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date updatedDate;


}
