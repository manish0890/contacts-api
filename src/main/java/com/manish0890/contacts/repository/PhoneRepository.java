package com.manish0890.contacts.repository;

import com.manish0890.contacts.entity.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    // Spring data query for finding all phone numbers associated with contactId
    List<Phone> findByContactId(long contactId);

    // Spring data query for deleting all phone numbers associated with contactId
    void deleteByContactId(long contactId);

    // Spring data query for checking if we have any phone numbers in table associated with contactId
    boolean existsByContactId(long contactId);
}
