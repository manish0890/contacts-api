package com.manish0890.contacts.service;

import com.manish0890.contacts.entity.Phone;
import com.manish0890.contacts.model.ContactDto;
import com.manish0890.contacts.model.NotFoundException;
import com.manish0890.contacts.repository.ContactRepository;
import com.manish0890.contacts.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceUnitTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private ContactService service;

    private ContactDto dto;

    @BeforeEach
    void setup() {
        dto = new ContactDto();
        dto.setFirstName(randomAlphabetic(10));
        dto.setLastName(randomAlphabetic(10));
        dto.setStreet(randomAlphabetic(10));
        dto.setCity(randomAlphabetic(10));
        dto.setState(randomAlphabetic(10));
        dto.setZipcode(12345L);
        dto.setCompany(randomAlphabetic(10));
        dto.setNotes(randomAlphabetic(100));

        List<Phone> phoneList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Phone phone = new Phone();
            phone.setNumber(1234567890L);
            phoneList.add(phone);
        }

        dto.setPhones(phoneList);
    }

    @Test
    void createNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.create(null));
        assertEquals("Contact cannot be null", e.getMessage());
    }

    @Test
    void createNotNullId() {
        dto.setId(123L);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        assertEquals("Id must be null", e.getMessage());
    }

    @Test
    void createNullFirstName() {
        dto.setFirstName(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        assertEquals("First name cannot be null", e.getMessage());
    }

    @Test
    void createNullLastName() {
        dto.setLastName(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        assertEquals("Last name cannot be null", e.getMessage());
    }

    @Test
    void createNullPhoneNumber() {
        dto.setPhones(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.create(dto));
        assertEquals("Phone numbers name cannot be null", e.getMessage());
    }

    @Test
    void getByNotExistentId() {
        long id = 123;

        // Stub the responses
        when(contactRepository.existsById(id)).thenReturn(false);

        // Exercise
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.getById(id));
        assertEquals("Contact does not exist for Id: " + id, e.getMessage());
    }

    @Test
    void searchByNameNull() {
        // Exercise
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.searchByName(null));
        assertEquals("Name cannot be null", e.getMessage());
    }

    @Test
    void updateNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.update(null));
        assertEquals("Contact cannot be null", e.getMessage());
    }

    @Test
    void updateNullId() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.update(dto));
        assertEquals("Id cannot be null", e.getMessage());
    }

    @Test
    void updateNotExistentId() {
        dto.setId(123L);

        // Stub the responses
        when(contactRepository.existsById(dto.getId())).thenReturn(false);

        // Exercise
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(dto));
        assertEquals("Contact does not exist for Id: " + dto.getId(), e.getMessage());
    }

    @Test
    void updateNullFirstName() {
        dto.setId(123L);
        dto.setFirstName(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.update(dto));
        assertEquals("First name cannot be null", e.getMessage());
    }

    @Test
    void updateNullLastName() {
        dto.setId(123L);
        dto.setLastName(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.update(dto));
        assertEquals("Last name cannot be null", e.getMessage());
    }

    @Test
    void updateNullPhoneNumber() {
        dto.setId(123L);
        dto.setPhones(null);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.update(dto));
        assertEquals("Phone numbers name cannot be null", e.getMessage());

        dto.setPhones(Collections.emptyList());
        e = assertThrows(IllegalArgumentException.class, () -> service.update(dto));
        assertEquals("Phone numbers name cannot be empty", e.getMessage());
    }
}
