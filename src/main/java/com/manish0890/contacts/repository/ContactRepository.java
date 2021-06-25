package com.manish0890.contacts.repository;

import com.manish0890.contacts.entity.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    // Spring data query for finding contact with matching Id
    Contact findById(long id);

    // Spring data query for searching contact with matching partial or full firstName or lastName
    List<Contact> findByFirstNameContainingOrLastNameContainingOrderByCreatedDate(String firstName, String lastName);
}
