package com.assignment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.antity.Contact;
import com.assignment.dto.CustomerDTO;
import com.assignment.repository.ContactRepository;
import com.assignment.repository.UserRepository;

@RestController
@RequestMapping("/sunbase/portal/api")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;
    //Operation for creating a new Customer
    @PostMapping("/assignment.jsp")
    public ResponseEntity<String> createContact(
           @RequestParam("cmd") String cmd,
            @RequestBody Contact contact,
            @RequestHeader("Authorization") String authorizationHeader
            ) 
    {
    	
       //  Validate authorization token
        if (!validateAuthorization(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
//
        if ("create".equals(cmd)) {
            if (contact.getFirstName() == null || contact.getLastName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First Name or Last Name is missing");
            }

            contactRepository.save(contact);
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully Created");
        } else {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid command");
       }
    }

    private boolean validateAuthorization(String authorizationHeader) {
        // Implement your logic to validate the authorization token
        // Return true if the token is valid, otherwise false
        return true;
    }
    
    //Operation for fetching customers
    @GetMapping("/assignment.jsp")
    public ResponseEntity<List<CustomerDTO>> getCustomerList(
            @RequestParam("cmd") String cmd,
            @RequestHeader("Authorization") String authorizationHeader
    		) {

        // Validate authorization token
        if (!validateAuthorization(authorizationHeader)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (("get_customer_list").equals(cmd)) {
            List<Contact> contacts = contactRepository.allDetaills();
            List<CustomerDTO> customerList = new ArrayList<>();

            for (Contact contact : contacts) {
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setFirstName(contact.getFirstName());
                customerDTO.setLastName(contact.getLastName());
                customerDTO.setStreet(contact.getStreet());
                customerDTO.setAddress(contact.getAddress());
                customerDTO.setCity(contact.getCity());
                customerDTO.setState(contact.getState());
                customerDTO.setEmail(contact.getEmail());
                customerDTO.setPhone(contact.getPhone());
                customerList.add(customerDTO);
            }

            return ResponseEntity.ok(customerList);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //Operation for deleting a customer
        @PostMapping("/assignment.jsp/{uuid}")
        public ResponseEntity<String> handleAssignmentRequest(
                @RequestParam("cmd") String cmd,
                @PathVariable("uuid") String uuid,
                @RequestHeader("Authorization") String authorizationHeader) {

            // Validate authorization token
            if (!validateAuthorization(authorizationHeader)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            if ("delete".equals(cmd)) {
                try {
                    Long contactId = Long.parseLong(uuid);
                    Optional<Contact> optionalContact = contactRepository.findById(contactId);

                    if (optionalContact.isPresent()) {
                        contactRepository.deleteById(contactId);
                        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUID not found");
                    }
                } catch (NumberFormatException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format");
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Not deleted");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        //Operation for updating existing customer
    @PutMapping("/assignment.jsp/{uuid}")
    public ResponseEntity<String> updateById(@RequestParam("cmd") String cmd,
            @RequestBody(required = false) Contact updatedContact,
            @PathVariable("uuid")String uuid,
            @RequestHeader("Authorization") String authorizationHeader
            ) {

    	if ("update".equals(cmd)) {
            if (uuid == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UUID parameter is missing");
            }
            
            if (updatedContact == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body is empty");
            }

            try {
                // Find and update the contact by UUID
                Optional<Contact> optionalContact = contactRepository.findById(Long.parseLong(uuid));
                if (optionalContact.isPresent()) {
                    Contact existingContact = optionalContact.get();
                    existingContact.setFirstName(updatedContact.getFirstName());
                    existingContact.setLastName(updatedContact.getLastName());
                    existingContact.setStreet(updatedContact.getStreet());
                    existingContact.setAddress(updatedContact.getAddress());
                    existingContact.setCity(updatedContact.getCity());
                    existingContact.setState(updatedContact.getState());
                    existingContact.setEmail(updatedContact.getEmail());
                    existingContact.setPhone(updatedContact.getPhone());
                    contactRepository.save(existingContact);
                    return ResponseEntity.ok("Successfully Updated");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UUID not found");
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid UUID format");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	
    }
}}

