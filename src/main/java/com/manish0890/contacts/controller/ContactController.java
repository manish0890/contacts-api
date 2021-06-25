package com.manish0890.contacts.controller;

import com.manish0890.contacts.model.ContactDto;
import com.manish0890.contacts.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

    private final ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDto create(@RequestBody ContactDto contactDto) {
        Assert.notNull(contactDto, "Body cannot be null");
        LOGGER.info("Received a request for creating a new contact: {}", contactDto.getFirstName());
        return service.create(contactDto);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDto get(@PathVariable long id) {
        LOGGER.info("Received a request for getting a contact by Id: {}", id);
        return service.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContactDto> searchByName(@RequestParam(name = "keyword") String keyword) {
        LOGGER.info("Received a request for searching contacts by Name: {}", keyword);
        return service.searchByName(keyword);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ContactDto update(@RequestBody ContactDto contactDto) {
        Assert.notNull(contactDto, "Body cannot be null");
        LOGGER.info("Received a request for Updating a contact by Id: {}", contactDto.getId());
        return service.update(contactDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        LOGGER.info("Received a request for deleting a contact by Id: {}", id);
        service.delete(id);
    }
}
