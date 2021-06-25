package com.manish0890.contacts.service;

import com.manish0890.contacts.TestConfig;
import com.manish0890.contacts.TestDataCleanup;
import com.manish0890.contacts.entity.Phone;
import com.manish0890.contacts.model.ContactDto;
import com.manish0890.contacts.repository.ContactRepository;
import com.manish0890.contacts.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class ContactServiceIntegrationTest extends TestDataCleanup {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ContactService service;

    private ContactDto dto;

    @BeforeEach
    void setup() {
        dto = getSampleContactDto();
    }

    @Test
    void createAndRead() {
        // Exercise endpoint
        ContactDto created = service.create(dto);

        // Assertions
        assertNotNull(created.getId());
        assertNotNull(created.getCreatedDate());
        assertNull(created.getUpdatedDate());

        // Verify Contact was saved in database correctly
        assertEquals(created, service.getById(dto.getId()));
    }

    @Test
    void searchByName() {
        service.create(dto);

        // Exercise Endpoint
        List<ContactDto> contactSearchedByPartialFName = service.searchByName(dto.getFirstName().substring(0, 5));
        List<ContactDto> contactSearchedByPartialLName = service.searchByName(dto.getLastName().substring(0, 5));

        // Assertions
        assertEquals(1, contactSearchedByPartialFName.size());
        assertEquals(1, contactSearchedByPartialLName.size());
        assertEquals(contactSearchedByPartialFName, contactSearchedByPartialLName);
    }

    @Test
    void update() {

        ContactDto created = service.create(dto);

        ContactDto toBeUpdatedWithDto = getSampleContactDto();
        toBeUpdatedWithDto.setId(created.getId());

        // Exercise Endpoint
        service.update(toBeUpdatedWithDto);

        // Assertions
        assertEquals(toBeUpdatedWithDto, service.getById(created.getId()));
    }

    @Test
    void delete() {
        service.create(dto);

        // Exercise endpoint
        service.delete(dto.getId());

        // Assertions
        assertFalse(contactRepository.existsById(dto.getId()));
        assertFalse(phoneRepository.existsByContactId(dto.getId()));
    }

    private ContactDto getSampleContactDto() {
        ContactDto dto = new ContactDto();
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
            phone.setNumber(1234567890L + i);
            phoneList.add(phone);
        }

        dto.setPhones(phoneList);

        return dto;
    }
}
