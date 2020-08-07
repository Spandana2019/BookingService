package com.example.bookingapplication.service;

import java.util.List;

import com.example.bookingapplication.model.Contact;

public interface ContactService {

	List<Contact> getAllContacts();

	Contact addContact(Contact contact);

	Contact updateContact(Contact contact);

	void deleteContact(Contact contact);

}
