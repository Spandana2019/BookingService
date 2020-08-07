package com.example.bookingapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookingapplication.model.Contact;
import com.example.bookingapplication.repository.ContactRepository;

@Service
public class ContactServiceImp implements ContactService {

	private ContactRepository contactRepository;

	public ContactServiceImp(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}

	@Override
	public Contact updateContact(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	public void deleteContact(Contact contact) {
		contactRepository.delete(contact);
	}

	@Override
	public Contact addContact(Contact contact) {
		return contactRepository.save(contact);
	}

}
