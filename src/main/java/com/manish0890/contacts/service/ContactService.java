package com.manish0890.contacts.service;

import com.manish0890.contacts.entity.Contact;
import com.manish0890.contacts.model.ContactDto;
import com.manish0890.contacts.model.NotFoundException;
import com.manish0890.contacts.repository.ContactRepository;
import com.manish0890.contacts.repository.PhoneRepository;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ContactService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactService.class);
    private static final String DOES_NOT_EXIST_MSG = "Contact does not exist for Id: ";
    private static final String NOT_NULL_MSG = "Contact cannot be null";

    private final ContactRepository contactRepository;
    private final PhoneRepository phoneRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, PhoneRepository phoneRepository) {
        this.contactRepository = contactRepository;
        this.phoneRepository = phoneRepository;
    }

    public ContactDto create(ContactDto contactDto) {
        Assert.notNull(contactDto, NOT_NULL_MSG);
        Assert.isNull(contactDto.getId(), "Id must be null");
        commonValidations(contactDto);

        // save contact in database
        saveContact(contactDto);
        LOGGER.info("Created Contact: {}", contactDto.getId());
        return contactDto;
    }

    public ContactDto getById(long id) {
        // Verify if the contact exists in database
        if(!contactRepository.existsById(id)) {
            throw new NotFoundException(DOES_NOT_EXIST_MSG + id);
        }

        Contact contact = contactRepository.findById(id);

        // Get contact from contacts table
        ContactDto dto = getContactDto(contact);

        // Get phone numbers associated with contact ids
        dto.setPhones(phoneRepository.findByContactId(id));

        LOGGER.info("Retrieved Contact by Id: {}", contact.getId());
        return dto;
    }

    public List<ContactDto> searchByName(String name) {
        Assert.notNull(name, "Name cannot be null");

        List<Contact> contacts = ListUtils.emptyIfNull(
                contactRepository.findByFirstNameContainingOrLastNameContainingOrderByCreatedDate(name, name));

        // Get contact from contacts table
        List<ContactDto> dtoList = new ArrayList<>();

        contacts.forEach(contact -> {
            ContactDto dto = getContactDto(contact);
            // Get phone numbers associated with contact ids
            dto.setPhones(phoneRepository.findByContactId(contact.getId()));
            dtoList.add(dto);
        });

        LOGGER.info("Retrieved {} Contacts by Name search: {}", contacts.size(), name);
        return dtoList;
    }

    public ContactDto update(ContactDto contactDto) {
        Assert.notNull(contactDto, NOT_NULL_MSG);
        Assert.notNull(contactDto.getId(), "Id cannot be null");
        commonValidations(contactDto);

        // Verify if the contact exists in database
        if(!contactRepository.existsById(contactDto.getId())) {
            throw new NotFoundException(DOES_NOT_EXIST_MSG + contactDto.getId());
        }

        // Delete all previously linked phone numbers
        while (BooleanUtils.toBooleanDefaultIfNull(phoneRepository.existsByContactId(contactDto.getId()), false)){
            phoneRepository.deleteByContactId(contactDto.getId());
        }

        // Save contact in database
        saveContact(contactDto);
        LOGGER.info("Updated Contact: {}", contactDto.getId());
        return contactDto;
    }

    private void commonValidations(ContactDto contactDto) {
        Assert.notNull(contactDto.getFirstName(), "First name cannot be null");
        Assert.notNull(contactDto.getLastName(), "Last name cannot be null");
        Assert.notNull(contactDto.getPhones(), "Phone numbers name cannot be null");
        Assert.isTrue(!contactDto.getPhones().isEmpty(), "Phone numbers name cannot be empty");
    }

    private void saveContact(ContactDto contactDto) {
        if (contactDto.getId() == null) {
            // If ID == null then this is a new record
            contactDto.setCreatedDate(new Date());
            contactDto.setUpdatedDate(null);
        } else {
            // If ID != null then this is an old record
            contactDto.setCreatedDate(contactRepository.findById(contactDto.getId()).orElseThrow().getCreatedDate());
            contactDto.setUpdatedDate(new Date());
        }

        Contact contact = getContact(contactDto);

        // Save contact in table
        contactRepository.save(contact);

        // Save phone numbers in table
        contactDto.getPhones().forEach(phone -> {
            phone.setContact(contact);
            phoneRepository.save(phone);
        });

        contactDto.setId(contact.getId());
    }

    public void delete(long id) {
        // Verify if the contact exists in database
        if(!contactRepository.existsById(id)) {
            throw new NotFoundException(DOES_NOT_EXIST_MSG + id);
        }
        contactRepository.deleteById(id);
    }

    public static Contact getContact(ContactDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Contact.class);
    }

    public static ContactDto getContactDto(Contact contact) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(contact, ContactDto.class);
    }
}
