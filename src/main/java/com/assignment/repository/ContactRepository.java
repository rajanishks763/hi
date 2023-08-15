package com.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.antity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
	
 @Query(value="select * from CONTACTS", nativeQuery = true)
 List<Contact> allDetaills();
}
